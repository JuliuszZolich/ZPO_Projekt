package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class DeleteTerminController {
    @FXML
    public Button CancelDeleteTerminButton;

    @FXML
    public Button DeleteTerminButton;

    @FXML
    public Label AddStudentErrorLabel;

    @FXML
    public ChoiceBox ChoseTermin;

    @FXML
    public Label DeleteTerminError;

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void delete(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        if (ChoseTermin.getSelectionModel().getSelectedItem() == null) {
            DeleteTerminError.setVisible(true);
            DeleteTerminError.setText("Nie wybrano Terminu!");
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/ConfirmDeleteStudent.fxml"));
            Stage confirmStage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            confirmStage.setScene(scene);
            stage.close();
            confirmStage.show();
        }
    }
}
