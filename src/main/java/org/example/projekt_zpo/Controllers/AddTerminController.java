package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddTerminController {
    @FXML
    public ChoiceBox ChoseGroup;

    @FXML
    public Button CancelAddTerminButton;

    @FXML
    public Button AddTerminButton;

    @FXML
    public DatePicker ChoseTermin;

    @FXML
    public Label AddTerminError;

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void add(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        if(ChoseTermin.getValue() == null) {
            AddTerminError.setVisible(true);
            AddTerminError.setText("Nie wybrano Terminu");
        }
        else if (ChoseGroup.getSelectionModel().getSelectedItem() == null) {
            AddTerminError.setVisible(true);
            AddTerminError.setText("Nie wybrano Grupy");
        }
        String chosenDate = ChoseTermin.getValue().toString();
        stage.close();
    }
}
