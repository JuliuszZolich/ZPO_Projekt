package org.example.projekt_zpo.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.projekt_zpo.Grupa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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
    public Button LogOutButton;

    @FXML
    public Button DeleteTerminButton;

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
        setButtonsVisibility(false, true, false, false);

        GroupChoiceBox.setOnAction(event -> {
            String selectedOption = (String) GroupChoiceBox.getValue();
            switch (selectedOption) {
                case "Lista Studentów":
                    try {
                        showStudentsInGroup();
                        setButtonsVisibility(false, true, false, false);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "Lista Terminów":
                    try {
                        showStudentsAttendanceInTermin();
                        setButtonsVisibility(true, false, true, true);
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
        setTitleAndIcon("Dodaj Grupę", stage);
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
        setTitleAndIcon("Dodaj Studenta", stage);
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

    public void setButtonsVisibility(boolean setAddTerminButton, boolean setAddStudentButton, boolean setTerminChoiceBox, boolean setDeleteTerminButton) {
        GroupChoiceTermin.setVisible(setTerminChoiceBox);
        AddStudentButton.setVisible(setAddStudentButton);
        AddStudentButton.setDisable(!setAddStudentButton);
        AddTerminButton.setVisible(setAddTerminButton);
        AddTerminButton.setDisable(!setAddTerminButton);
        DeleteTerminButton.setVisible(setDeleteTerminButton);
        DeleteTerminButton.setDisable(!setDeleteTerminButton);
    }

    public void showGroupList(ArrayList<Grupa> grupy) throws IOException {
        GroupList.getChildren().clear();
        for (int i = 0; i < grupy.size(); i++) {
            Grupa temp = grupy.get(i);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/GroupItem.fxml"));
            AnchorPane pane = loader.load();
            GroupItemController controller = loader.getController();
            controller.setGroupItemName(temp.getNazwa());
            if (i == grupy.size() - 1) {
                controller.setLineUnderGroupItem();
            }
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
        setTitleAndIcon("Dodaj Termin", stage);
        Stage ownerStage = (Stage) AddTerminButton.getScene().getWindow();
        stage.initOwner(ownerStage);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void logout(MouseEvent mouseEvent) {
        Stage stage = (Stage) LogOutButton.getScene().getWindow();
        stage.close();
    }

    public void openDeleteTerminWindow(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/DeleteTermin.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        setTitleAndIcon("Usuń Termin", stage);
        Stage ownerStage = (Stage) DeleteTerminButton.getScene().getWindow();
        stage.initOwner(ownerStage);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void setUserName(String userName, String userLastName) {
        UserName.setText(userName + " " + userLastName);
    }

    public void setTitleAndIcon(String title, Stage stage) {
        stage.setTitle(title);
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/app_icon.png")).toExternalForm());
        stage.getIcons().add(image);
    }
}
