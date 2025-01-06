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

import java.io.IOException;
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


    public <User> void login(MouseEvent mouseEvent) throws IOException {
        Stage loginStage = (Stage) LoginButton.getScene().getWindow();
        String username = LoginTextArea.getText();
        String password = PasswordField.getText();
        Prowadzacy prowadzacy = null;
        if (LoginTextArea.getText().isBlank() || PasswordField.getText().isBlank()) {
            PasswordField.setText("");
            LoginTextArea.setText("");
            WrongParamettersError.setVisible(true);
            WrongParamettersError.setText("Nie podano Loginu lub hasła!");
        }
        else {
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("http://172.30.83.83:8080/api/login"))
                        .POST(HttpRequest.BodyPublishers.ofString(""))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                ObjectMapper mapper = new ObjectMapper();
                prowadzacy = mapper.readValue(response.body(), Prowadzacy.class);

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
}
