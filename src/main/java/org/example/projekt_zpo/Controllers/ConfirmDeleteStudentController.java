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
    public Button CancelDeleteConformation;

    @FXML
    public Button DeleteUserConfirmationButton;

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) CancelDeleteConformation.getScene().getWindow();
        stage.close();
    }

    public void delete(MouseEvent mouseEvent) {
        Stage stage = (Stage) CancelDeleteConformation.getScene().getWindow();
        stage.close();
    }
}
