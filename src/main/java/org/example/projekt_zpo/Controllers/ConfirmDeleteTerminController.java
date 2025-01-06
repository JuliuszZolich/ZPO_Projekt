package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConfirmDeleteTerminController {
    @FXML
    public Button CancelDeleteTerminConformation;

    @FXML
    public Button DeleteTerminConfirmationButton;

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) CancelDeleteTerminConformation.getScene().getWindow();
        stage.close();
    }

    public void delete(MouseEvent mouseEvent) {
        Stage stage = (Stage) CancelDeleteTerminConformation.getScene().getWindow();
        stage.close();
    }
}
