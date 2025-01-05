package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddStudentController {
    @FXML
    public Label AddStudentErrorLabel;
    @FXML
    private TextField AddStudentIndex;

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void add(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        try {
            int index = Integer.parseInt(AddStudentIndex.getText());
            if (index < 100000 || index > 999999) {
                AddStudentErrorLabel.setVisible(true);
                AddStudentIndex.setText("");
                AddStudentErrorLabel.setText("Błędne dane!");
            }
            else{
                //TODO : DODAWANIE STUDENTA DO GRUPY
                stage.close();
            }
        }
        catch (NumberFormatException e) {
            AddStudentErrorLabel.setVisible(true);
            AddStudentIndex.setText("");
            AddStudentErrorLabel.setText("Błędne dane!");
        }
    }
}
