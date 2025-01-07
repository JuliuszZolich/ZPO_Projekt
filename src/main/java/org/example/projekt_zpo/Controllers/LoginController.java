package org.example.projekt_zpo.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.projekt_zpo.Grupa;
import org.example.projekt_zpo.Prowadzacy;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoginController {
    Boolean isLoggedIn = false;

    @FXML
    public TextArea LoginTextArea;
    @FXML
    public Button LoginButton;
    @FXML
    public Label WrongParamettersError;
    @FXML
    public PasswordField PasswordField;

    public void login(MouseEvent mouseEvent) throws IOException {
        Prowadzacy prowadzacy = null;
        Stage loginStage = (Stage) LoginButton.getScene().getWindow();
        String username = LoginTextArea.getText();
        String password = PasswordField.getText();
        if (LoginTextArea.getText().isBlank() || PasswordField.getText().isBlank()) {
            PasswordField.setText("");
            LoginTextArea.setText("");
            WrongParamettersError.setVisible(true);
            WrongParamettersError.setText("Nie podano Loginu lub hasła!");
        }
        else {
            try {
                JSONObject jsonProwadzacy = createJSONObjectProwadzacy(username, password);
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest requestLogin = HttpRequest.newBuilder()
                        .uri(new URI("http://172.30.83.83:8080/api/login"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonProwadzacy.toString()))
                        .build();

                HttpResponse<String> responseProwadzacy = client.send(requestLogin, HttpResponse.BodyHandlers.ofString());
                try{
                    ObjectMapper mapper = new ObjectMapper();
                    prowadzacy = mapper.readValue(responseProwadzacy.body(), Prowadzacy.class);
                    isLoggedIn = true;
                }
                catch (Exception e){
                    PasswordField.setText("");
                    LoginTextArea.setText("");
                    WrongParamettersError.setVisible(true);
                    WrongParamettersError.setText("Błędne Dane Logowania!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (isLoggedIn) {
                ArrayList<Grupa> grupy = null;
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest requestGrupy = HttpRequest.newBuilder()
                            .uri(new URI("http://172.30.83.83:8080/api/grupy"))
                            .GET()
                            .build();
                    HttpResponse<String> responseGrupy = client.send(requestGrupy, HttpResponse.BodyHandlers.ofString());
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        grupy = (ArrayList<Grupa>) mapper.readValue(responseGrupy.body(), new TypeReference<List<Grupa>>() {
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/MainScene.fxml"));
                Scene mainScene = new Scene(fxmlLoader.load());
                Stage mainStage = new Stage();
                setTitleAndIcon("Lista Obecności", mainStage);
                mainStage.setScene(mainScene);
                loginStage.close();
                mainStage.show();
                MainController mainController = fxmlLoader.getController();
                mainController.setUserName(prowadzacy.getImie(), prowadzacy.getNazwisko());
                mainController.showGroupList(grupy);
            }
        }
    }

    public void setTitleAndIcon(String title, Stage stage) {
        stage.setTitle(title);
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/app_icon.png")).toExternalForm());
        stage.getIcons().add(image);
    }

    public JSONObject createJSONObjectProwadzacy(String login, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("password", password);
        jsonObject.put("login", login);
        return jsonObject;
    }
}