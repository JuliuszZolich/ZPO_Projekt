package org.example.projekt_zpo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AttendenceList extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AttendenceList.class.getResource("LoginScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/StyleSheets/MainScene.css")).toExternalForm());
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/app_icon.png")).toExternalForm());
        /*VBox VBox = (javafx.scene.layout.VBox) scene.lookup("#GroupsContainer");
        for (int i = 0; i < 5; i++) {
            FXMLLoader fxmlLoader2 = new FXMLLoader(AttendenceList.class.getResource("GroupItem.fxml"));
            AnchorPane temp = fxmlLoader2.load();
            VBox.getChildren().add(temp);
        }*/
        stage.getIcons().add(image);
        stage.setTitle("Lista Obecności - Panel logowania");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}