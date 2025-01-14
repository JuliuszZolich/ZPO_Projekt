package org.example.projekt_zpo.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.projekt_zpo.Grupa;
import org.example.projekt_zpo.Student;
import org.example.projekt_zpo.Termin;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainController {
    @FXML
    public Button AddStudent;

    @FXML
    public Button AddTermin;
    @FXML
    public Button DeleteTermin;
    @FXML
    public ImageView DeleteGroup;
    @FXML
    public Button DeleteStudent;

    @FXML
    public ChoiceBox GroupChoiceTermin;

    @FXML
    public Label UserName;

    @FXML
    public Label GroupNameLabel;

    @FXML
    public Button LogOutButton;
    @FXML
    public TableView<Grupa> GroupTable;
    @FXML
    public TableColumn<Grupa, String> groupColumn;
    @FXML
    public TableColumn<Student, Integer> StudentIndex;
    @FXML
    public TableColumn<Student, String> StudentName;
    @FXML
    public TableColumn<Student, String> StudentSurname;

    @FXML
    public TableView<Student> StudentsTable;

    @FXML
    private Button AddGroupButton;

    public static int prowadzacyId;

    int actualGroup = 0;

    public int isAlive = 0;

    public static ArrayList<Student> actualStudents;

    public void initialize(){

        GroupTable.setOnMouseClicked(event ->
        {
            if (event.getClickCount() == 1) {
                Grupa selectedGroup = GroupTable.getSelectionModel().getSelectedItem();
                actualGroup = selectedGroup.getId();
                try {
                    showStudentsInGroup();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(actualGroup);
                setTitle(selectedGroup);
            }
        });
    }

    public void setActualGroup(int id) {
        actualGroup = id;
    }

    public void setUserName(String userName, String userLastName) {
        UserName.setText(userName + " " + userLastName);
    }

    public void setTitleAndIcon(String title, Stage stage) {
        stage.setTitle(title);
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/app_icon.png")).toExternalForm());
        stage.getIcons().add(image);
    }

    public void setColumns() {
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        StudentIndex.setCellValueFactory(new PropertyValueFactory<>("indeks"));
        StudentName.setCellValueFactory(new PropertyValueFactory<>("imie"));
        StudentSurname.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
    }

    public void setTerminsForGroup(){
        GroupChoiceTermin.getItems().clear();
        ArrayList<Termin> arrayList = getActualTermins();
    }

    public ArrayList<Termin> getActualTermins() {
        ArrayList<Termin> grupy = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requestTerminy = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/grupy"))
                    .GET()
                    .build();
            HttpResponse<String> responseTerimy = client.send(requestTerminy, HttpResponse.BodyHandlers.ofString());
            try {
                ObjectMapper mapper = new ObjectMapper();
                grupy = (ArrayList<Termin>) mapper.readValue(responseTerimy.body(), new TypeReference<List<Termin>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grupy;
    }

    public ArrayList<Grupa> getGroups() {
        ArrayList<Grupa> grupy = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requestGrupy = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/grupy"))
                    .GET()
                    .build();
            HttpResponse<String> responseGrupy = client.send(requestGrupy, HttpResponse.BodyHandlers.ofString());
            try {
                ObjectMapper mapper = new ObjectMapper();
                grupy = (ArrayList<Grupa>) mapper.readValue(responseGrupy.body(), new TypeReference<List<Grupa>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grupy;
    }

    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requestGrupy = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/studencigrupa?grupaId=" + actualGroup))
                    .GET()
                    .build();
            HttpResponse<String> responseGrupy = client.send(requestGrupy, HttpResponse.BodyHandlers.ofString());
            try {
                ObjectMapper mapper = new ObjectMapper();
                students = (ArrayList<Student>) mapper.readValue(responseGrupy.body(), new TypeReference<List<Student>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public void showStudentsInGroup() throws IOException {
        ArrayList<Student> students = getStudents();
        actualStudents = students;
        ObservableList<Student> studentsList = FXCollections.observableArrayList(students);
        for (int i = 0; i < studentsList.size(); i++) {
            studentsList.set(i, students.get(i));
        }
        StudentsTable.setItems(studentsList);
    }

    public void showGroupList() {
        ArrayList<Grupa> grupy = getGroups();
        ObservableList<Grupa> grupyList = FXCollections.observableArrayList(grupy);
        for (int i = 0; i < grupyList.size(); i++) {
            if (isAlive == 0) {
                setActualGroup(grupy.get(i).getId());
                setTitle(grupy.get(i));
                isAlive = 1;
            }
            grupyList.set(i, grupy.get(i));
        }
        GroupTable.setItems(grupyList);
    }

    public void showStudentsAttendanceInTermin() throws IOException {
    }

    public void logout(MouseEvent mouseEvent) {
        Stage stage = (Stage) LogOutButton.getScene().getWindow();
        stage.close();
    }

    void setTitle(Grupa selectedGroup) {
        GroupNameLabel.setText(selectedGroup.getNazwa());
            /*Attendence.setCellFactory(column -> new TableCell<>() {
                private final ChoiceBox<String> choiceBox = new ChoiceBox<>();
                {
                    choiceBox.getItems().addAll("Obecny", "Nieobecny", "Spóźniony", "Usprawiedliwiony");
                    choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
                        if (newValue != null) {
                            Student student = getTableView().getItems().get(getIndex());
                            System.out.println("Wybrano: " + newValue + " dla indexu: " + student.getIndeks());
                        }
                    });
                }
            });*/
    }



    public void deleteGroup(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/ConfirmDeleteGroup.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        ConfirmDeleteGroupController controller = fxmlLoader.getController();
        controller.setMainController(this);
        controller.setGroupID(actualGroup);
        stage.setScene(scene);
        setTitleAndIcon("Usuń Grupe", stage);
        Stage ownerStage = (Stage) DeleteGroup.getScene().getWindow();
        stage.initOwner(ownerStage);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void openDeleteStudentWindow(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/DeleteStudent.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        setTitleAndIcon("Usuń Studenta", stage);
        Stage ownerStage = (Stage) DeleteStudent.getScene().getWindow();
        DeleteStudentController.mainController = this;
        DeleteStudentController.students = actualStudents;
        DeleteStudentController controller = fxmlLoader.getController();
        controller.setStudents();
        stage.initOwner(ownerStage);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void openAddTerminWindow(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/AddTermin.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        setTitleAndIcon("Dodaj Termin", stage);
        Stage ownerStage = (Stage) AddTermin.getScene().getWindow();
        AddTerminController.mainController = this;
        AddTerminController.grupaID = actualGroup;
        AddTerminController.prowadzacyId = prowadzacyId;
        stage.initOwner(ownerStage);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void openDeleteTerminWindow(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/DeleteTermin.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        setTitleAndIcon("Usuń Termin", stage);
        Stage ownerStage = (Stage) DeleteTermin.getScene().getWindow();
        stage.initOwner(ownerStage);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void openAddStudentWindow(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/AddStudent.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        AddStudentController.groupID = actualGroup;
        AddStudentController.mainController = this;
        stage.setScene(scene);
        setTitleAndIcon("Dodaj Studenta", stage);
        Stage ownerStage = (Stage) AddStudent.getScene().getWindow();
        stage.initOwner(ownerStage);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void openAddGroupWindow(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/AddGroup.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        AddGroupController.mainController = this;
        stage.setScene(scene);
        setTitleAndIcon("Dodaj Grupę", stage);
        Stage ownerStage = (Stage) AddGroupButton.getScene().getWindow();
        stage.initOwner(ownerStage);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
