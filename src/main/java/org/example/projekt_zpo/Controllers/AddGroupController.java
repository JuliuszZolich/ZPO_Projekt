package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddGroupController {
    @FXML
    public TextField GroupNameTextArea;

    @FXML
    public Button CancelAddGroupButton;

    @FXML
    public Button AddGroupButton;

    @FXML
    public Label ErrorMessageLabel;

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void add(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        String groupName = GroupNameTextArea.getText();
        if (groupName.isBlank()) {
            ErrorMessageLabel.setText("Nie podano nazwy grupy!");
            ErrorMessageLabel.setVisible(true);
            GroupNameTextArea.setText("");
        }
        else if (groupName.length() > 20) {
            ErrorMessageLabel.setText("Za długa nazwa grupy!");
            ErrorMessageLabel.setVisible(true);
            GroupNameTextArea.setText("");
        }
        else {
            stage.close();
        }
    }
}
