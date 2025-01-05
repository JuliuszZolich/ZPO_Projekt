package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    public TextArea LoginTextArea;
    @FXML
    public Button LoginButton;
    @FXML
    public Label WrongParamettersError;
    @FXML
    public javafx.scene.control.PasswordField PasswordField;


    public void login(MouseEvent mouseEvent) throws IOException {
        if (!LoginTextArea.getText().equals("123") || !PasswordField.getText().equals("123")) {
            PasswordField.setText("");
            LoginTextArea.setText("");
            WrongParamettersError.setVisible(true);
        }
        else {
            Stage loginStage = (Stage) LoginButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/MainScene.fxml"));
            Scene mainScene = new Scene(fxmlLoader.load());
            MainController mainController = fxmlLoader.getController();

            for (int i = 0; i < 10; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/StudentItem.fxml"));
                AnchorPane pane = loader.load();
                mainController.GroupList.getChildren().add(pane);
            }
            Stage mainStage = new Stage();
            mainStage.setTitle("Lista Obecności");
            mainStage.setScene(mainScene);
            loginStage.close();
            mainStage.show();
        }
    }
}
