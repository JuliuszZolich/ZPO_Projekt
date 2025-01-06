package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConfirmDeleteStudentController {
    String deleteType = "";

    @FXML
    public Label ConfirmationLabel;

    @FXML
    public Button CancelDeleteConfirmation;

    @FXML
    public Button DeleteUserConfirmationButton;

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) CancelDeleteConfirmation.getScene().getWindow();
        stage.close();
    }

    public void delete(MouseEvent mouseEvent) {
        Stage stage = (Stage) CancelDeleteConfirmation.getScene().getWindow();
        stage.close();
    }
}
