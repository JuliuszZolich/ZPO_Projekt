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
        stage.getIcons().add(image);
        stage.setTitle("Lista Obecności - Panel logowania");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}