package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConfirmDeleteGroupController {
    @FXML
    public Label ConfirmationLabel;

    @FXML
    public Button CancelDeleteGroupConfirmation;

    @FXML
    public Button DeleteGroupConfirmationButton;


    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) CancelDeleteGroupConfirmation.getScene().getWindow();
        stage.close();
    }

    public void delete(MouseEvent mouseEvent) {
        Stage stage = (Stage) CancelDeleteGroupConfirmation.getScene().getWindow();
        stage.close();
    }
}
