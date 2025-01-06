package org.example.projekt_zpo.Controllers;

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
import org.example.projekt_zpo.Prowadzacy;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class LoginController {
    @FXML
    public TextArea LoginTextArea;
    @FXML
    public Button LoginButton;
    @FXML
    public Label WrongParamettersError;
    @FXML
    public PasswordField PasswordField;

    public void login(MouseEvent mouseEvent){
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

                JSONObject json = createJSONObject(username, password);
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("http://172.30.83.83:8080/api/login"))
                        .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                ObjectMapper mapper = new ObjectMapper();
                Prowadzacy prowadzacy = mapper.readValue(response.body(), Prowadzacy.class);
                System.out.println(prowadzacy.getHaslo() + " " + prowadzacy.getImie());
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/MainScene.fxml"));
                Scene mainScene = new Scene(fxmlLoader.load());
                Stage mainStage = new Stage();
                setTitleAndIcon("Lista Obecności", mainStage);
                mainStage.setScene(mainScene);
                loginStage.close();
                mainStage.show();
                MainController mainController = fxmlLoader.getController();
                mainController.setUserName(prowadzacy.getImie(),prowadzacy.getNazwisko());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setTitleAndIcon(String title, Stage stage) {
        stage.setTitle(title);
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/app_icon.png")).toExternalForm());
        stage.getIcons().add(image);
    }

    public JSONObject createJSONObject(String login, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("password", password);
        jsonObject.put("login", login);
        System.out.println(jsonObject.toString());
        return jsonObject;
    }
}