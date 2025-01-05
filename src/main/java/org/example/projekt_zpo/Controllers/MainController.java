package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    Button buttonAddGroup;

    @FXML
    Button buttonAddStudent;

    public void openAddGroupWindow(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddGroup.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dodaj Grupę");
        Stage ownerStage = (Stage) buttonAddGroup.getScene().getWindow();
        stage.initOwner(ownerStage);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void openAddStudentWindow(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddStudent.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dodaj Studenta");
        Stage ownerStage = (Stage) buttonAddStudent.getScene().getWindow();
        stage.initOwner(ownerStage);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
