package org.example.projekt_zpo.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    public ChoiceBox GroupChoiceBox;

    @FXML
    public ChoiceBox GroupChoiceTermin;

    @FXML
    public Button AddTerminButton;

    @FXML
    public Label UserName;

    @FXML
    public Label GroupNameLabel;

    @FXML
    private VBox GroupList;

    @FXML
    private Button AddGroupButton;

    @FXML
    private Button AddStudentButton;

    @FXML
    public VBox StudentsInGroupList;

    public void initialize() throws IOException {
        showStudentsInGroup();
        showGroupList();
        setButtonsVisibility(false, true, false);

        GroupChoiceBox.setOnAction(event -> {
            String selectedOption = (String) GroupChoiceBox.getValue();
            switch (selectedOption) {
                case "Lista Studentów":
                    try {
                        showStudentsInGroup();
                        setButtonsVisibility(false, true, false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Lista Terminów":
                    try {
                        showStudentsAttendanceInTermin();
                        setButtonsVisibility(true, false, true);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        });
    }

    public void openAddGroupWindow(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/AddGroup.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dodaj Grupę");
        Stage ownerStage = (Stage) AddGroupButton.getScene().getWindow();
        stage.initOwner(ownerStage);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void openAddStudentWindow(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/AddStudent.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dodaj Studenta");
        Stage ownerStage = (Stage) AddStudentButton.getScene().getWindow();
        stage.initOwner(ownerStage);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void showStudentsInGroup() throws IOException {
        StudentsInGroupList.getChildren().clear();
        GroupChoiceTermin.setVisible(false);
        AddStudentButton.setVisible(true);
        AddTerminButton.setVisible(false);
        for (int i = 0; i < 10; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/StudentItem.fxml"));
            AnchorPane pane = loader.load();
            StudentsInGroupList.getChildren().add(pane);
        }
    }

    public void setButtonsVisibility(boolean setAddTerminButton, boolean setAddStudentButton, boolean setTerminChoiceBox) {
        GroupChoiceTermin.setVisible(setTerminChoiceBox);
        AddStudentButton.setVisible(setAddStudentButton);
        AddStudentButton.setDisable(!setAddStudentButton);
        AddTerminButton.setVisible(setAddTerminButton);
        AddTerminButton.setDisable(!setAddTerminButton);
    }

    public void showGroupList() throws IOException {
        GroupList.getChildren().clear();
        for (int i = 0; i < 10; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/GroupItem.fxml"));
            AnchorPane pane = loader.load();
            GroupList.getChildren().add(pane);
        }
    }

    public void showStudentsAttendanceInTermin() throws IOException {
        StudentsInGroupList.getChildren().clear();
        for (int i = 0; i < 10; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/StudentItemTerminList.fxml"));
            AnchorPane pane = loader.load();
            StudentsInGroupList.getChildren().add(pane);
        }
    }


    public void addTermin(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/AddTermin.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Dodaj Termin");
        Stage ownerStage = (Stage) AddTerminButton.getScene().getWindow();
        stage.initOwner(ownerStage);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
