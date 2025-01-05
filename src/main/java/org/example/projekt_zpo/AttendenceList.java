package org.example.projekt_zpo;

import com.almasb.fxgl.core.collection.Array;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AttendenceList extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AttendenceList.class.getResource("MainScene.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/StyleSheets/MainScene.css").toExternalForm());
        Image image = new Image(getClass().getResource("/images/app_icon.png").toExternalForm());
        VBox VBox = (javafx.scene.layout.VBox) scene.lookup("#GroupsContainer");
        for (int i = 0; i < 5; i++) {
            FXMLLoader fxmlLoader2 = new FXMLLoader(AttendenceList.class.getResource("GroupItem.fxml"));
            AnchorPane temp = fxmlLoader2.load();
            VBox.getChildren().add(temp);
        }
        stage.getIcons().add(image);
        stage.setTitle("Lista Obecności - Panel logowania");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}