package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SetAttendanceForStudentController {
    @FXML
    public ChoiceBox ChoiceStudent;
    @FXML
    public ChoiceBox ChoiceAttendance;

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) ChoiceStudent.getScene().getWindow();
        stage.close();
    }

    public void set(MouseEvent mouseEvent) {
        Stage stage = (Stage) ChoiceStudent.getScene().getWindow();
        stage.close();
    }
}
