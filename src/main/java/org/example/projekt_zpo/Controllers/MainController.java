package org.example.projekt_zpo.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.example.projekt_zpo.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.projekt_zpo.AttendanceList.ip;

public class MainController {
    @FXML
    public Button showStudentsInGroupButton, addStudentButton, addTerminButton, deleteTerminButton, deleteStudentButton, logOutButton, deleteStudentFromDatabaseButton, addStudentToDatabaseButton, addGroupButton, setAttendanceForStudentButton;
    @FXML
    public ImageView deleteGroupImageView;
    @FXML
    public ChoiceBox<String> groupChoiceTermChoiceBox;
    @FXML
    public Label userNameLabel, groupNameLabel, terminNameLabel, choiceTerminLabel;
    @FXML
    public TableView<Grupa> groupTableView;
    @FXML
    public TableView<StudentListaObecnosci> studentsTableView;
    @FXML
    public TableColumn<Grupa, String> groupColumn;
    @FXML
    public TableColumn<StudentListaObecnosci, String> studentIndexColumn, attendanceColumn, studentNameColumn, studentSurnameColumn;
    @FXML
    public Line rightLine;
    @FXML
    public TableView<Student> studentsInGroupTableView;
    @FXML
    public TableColumn<Student, Integer> studentIndexStudentsInGroup;
    @FXML
    public TableColumn<Student, String> studentNameStudentsInGroup;
    @FXML
    public TableColumn<Student, String> studentSurnameStudentsInGroup;

    public HttpClient client = HttpClient.newHttpClient();

    public static int prowadzacyId;
    public static Grupa actualGroup;
    public static ArrayList<Student> actualStudents;
    public static ArrayList<Termin> actualTerms;
    public static ArrayList<StudentListaObecnosci> actualStudentsWithAttendance;
    public Termin actualTerm;

    public void initialize(){

        groupTableView.setOnMouseClicked(event ->
        {
            if (event.getClickCount() == 1) {
                actualGroup = groupTableView.getSelectionModel().getSelectedItem();
                try {
                    refreshActualGroup();
                } catch (URISyntaxException | IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        groupChoiceTermChoiceBox.setOnAction(event -> {
            String selectedTerm = groupChoiceTermChoiceBox.getValue();
            for (Termin termin : actualTerms) {
                if (termin.getData().equals(selectedTerm)) {
                    actualTerm = termin;
                    break;
                }
            }
            refreshGroupAttendanceTable();
        });
    }

    public void refreshScene() {
        showStudentsGroupList();
        actualStudents = null;
        actualTerms = null;
        actualStudentsWithAttendance = null;
        actualTerm = null;
        studentsInGroupTableView.setVisible(false);
        choiceTerminLabel.setVisible(false);
        setGroupButtons(false);
    }

    public void refreshGroupAttendanceTable() {
        terminNameLabel.setVisible(true);
        choiceTerminLabel.setVisible(true);
        studentsTableView.setVisible(true);
        setAttendanceForStudentButton.setVisible(true);
        studentsInGroupTableView.setVisible(false);
        terminNameLabel.setText(actualTerm.getNazwa());
        showStudentsInGroup();
    }

    public void setGroupButtons(Boolean value){
        addStudentButton.setVisible(value);
        addTerminButton.setVisible(value);
        deleteTerminButton.setVisible(value);
        deleteStudentButton.setVisible(value);
        deleteGroupImageView.setVisible(value);
        groupChoiceTermChoiceBox.setVisible(value);
        groupNameLabel.setVisible(value);
        rightLine.setVisible(value);
        showStudentsInGroupButton.setVisible(value);
    }

    public void refreshActualGroup() throws URISyntaxException, IOException, InterruptedException {
        groupChoiceTermChoiceBox.getItems().clear();
        studentsTableView.setVisible(false);
        terminNameLabel.setVisible(false);
        studentsInGroupTableView.setVisible(true);
        actualStudents = null;
        showStudentsInTableView();
        setGroupButtons(true);
        setTermsForGroup();
        setActualGroupTitle(actualGroup);
    }

    public void setUserName(String userName, String userLastName) {
        userNameLabel.setText(userName + " " + userLastName);
    }

    public void setTitleAndIconForWindow(String title, Stage stage) {
        stage.setTitle(title);
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/app_icon.png")).toExternalForm());
        stage.getIcons().add(image);
    }

    void setActualGroupTitle(Grupa selectedGroup) {
        groupNameLabel.setText(selectedGroup.getNazwa());
    }

    public void setColumns() {
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        studentIndexColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudentIndexToString()));
        studentNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudentNameToString()));
        studentSurnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudentSurnameToString()));
        attendanceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAttendanceToString()));
        studentIndexStudentsInGroup.setCellValueFactory(new PropertyValueFactory<>("indeks"));
        studentNameStudentsInGroup.setCellValueFactory(new PropertyValueFactory<>("imie"));
        studentSurnameStudentsInGroup.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
    }

    public void setTermsForGroup() throws URISyntaxException, IOException, InterruptedException {
        groupChoiceTermChoiceBox.getItems().clear();
        ArrayList<Termin> terms = getActualTerms();
        if(terms != null) {
            for (Termin term : terms) {
                groupChoiceTermChoiceBox.getItems().add(term.getData());
            }
        }
    }

    public ArrayList<StudentListaObecnosci> getActualStudentsWithAttendance() {
        ArrayList<StudentListaObecnosci> studentsWithAttendance = new ArrayList<>();
        if(actualStudents != null) {
            for (Student student : actualStudents) {
                StudentListaObecnosci temp = new StudentListaObecnosci();
                temp.setStudent(student);
                temp.setAttendance(getAttendanceForStudentInTerm(student.getIndeks()));
                studentsWithAttendance.add(temp);
            }
            return studentsWithAttendance;
        }
        System.out.println(2);
        return null;
    }

    public Obecnosc getAttendanceForStudentInTerm(int idStudenta) {
        Obecnosc attendance = null;
        try {
            HttpResponse<String> response = requestGet(ip + "/api/sprawdzobecnoscstudenta?studentId=" + idStudenta + "&terminId=" + actualTerm.getId());
            if(response.statusCode() == 200) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    attendance = mapper.readValue(response.body(), Obecnosc.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attendance;
    }

    public HttpResponse<String> requestGet(String uri) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(uri)).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public ArrayList<Termin> getActualTerms() throws URISyntaxException, IOException, InterruptedException {
        ArrayList<Termin> terms = null;
        HttpResponse<String> response = requestGet(ip + "/api/termingrupa?grupaId=" + actualGroup.getId());
        if(response.statusCode() == 200) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                terms = (ArrayList<Termin>) mapper.readValue(response.body(), new TypeReference<List<Termin>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        actualTerms = terms;
        return terms;
    }

    public ArrayList<Grupa> getGroups() {
        ArrayList<Grupa> groups = null;
        try {
            HttpResponse<String> response = requestGet(ip + "/api/grupy");
            try {
                ObjectMapper mapper = new ObjectMapper();
                groups = (ArrayList<Grupa>) mapper.readValue(response.body(), new TypeReference<List<Grupa>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groups;
    }

    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = null;
        try {
            HttpResponse<String> response = requestGet(ip + "/api/studencigrupa?grupaId=" + actualGroup.getId());
            if(response.statusCode() == 200) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    students = (ArrayList<Student>) mapper.readValue(response.body(), new TypeReference<List<Student>>() {
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        actualStudents = students;
        return students;
    }

    public void showStudentsInGroup(){
        actualStudents = getStudents();
        ArrayList<StudentListaObecnosci> studentsWithAttendance = null;
        if(actualStudents != null) {
            studentsWithAttendance = getActualStudentsWithAttendance();
        }
        if(studentsWithAttendance != null) {
            actualStudentsWithAttendance = studentsWithAttendance;
            if (!actualStudentsWithAttendance.isEmpty()) {
                ObservableList<StudentListaObecnosci> studentsList = FXCollections.observableArrayList(studentsWithAttendance);
                for (int i = 0; i < studentsList.size(); i++) {
                    studentsList.set(i, studentsWithAttendance.get(i));
                }
                studentsTableView.setItems(studentsList);
            }
        }
    }

    public void showStudentsGroupList() {
        ArrayList<Grupa> groups = getGroups();
        ObservableList<Grupa> grupyList = FXCollections.observableArrayList(groups);
        for (int i = 0; i < grupyList.size(); i++) {
            grupyList.set(i, groups.get(i));
        }
        groupTableView.setItems(grupyList);
    }

    public void logOut(MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/LoginScene.fxml"));
        Scene loginScene = new Scene(fxmlLoader.load());
        Stage loginStage = new Stage();
        setTitleAndIconForWindow("Lista Obecności", loginStage);
        LoginController.isLoggedIn = false;
        loginStage.setScene(loginScene);
        stage.close();
        loginStage.show();
    }

    public void openDeleteGroupWindow(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = loadScene("/org/example/projekt_zpo/ConfirmDeleteGroup.fxml");
        ConfirmDeleteGroupController.mainController = this;
        ConfirmDeleteGroupController.groupID = actualGroup.getId();
        stage.setScene(scene);
        setTitleAndIconForWindow("Usuń Grupe", stage);
        blockWindow((Stage) deleteGroupImageView.getScene().getWindow(), stage);
    }

    public void openDeleteStudentWindow(MouseEvent mouseEvent) throws IOException {
        if(actualStudents == null) {
            actualStudents = getStudents();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/DeleteStudent.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        setTitleAndIconForWindow("Usuń Studenta", stage);
        DeleteStudentController.mainController = this;
        DeleteStudentController.students = actualStudents;
        DeleteStudentController controller = fxmlLoader.getController();
        controller.setStudents();
        blockWindow((Stage) deleteStudentButton.getScene().getWindow(), stage);
    }

    public void openAddTermWindow(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = loadScene("/org/example/projekt_zpo/AddTermin.fxml");
        stage.setScene(scene);
        setTitleAndIconForWindow("Dodaj Termin", stage);
        AddTermForGroupController.mainController = this;
        AddTermForGroupController.grupaID = actualGroup.getId();
        AddTermForGroupController.prowadzacyId = prowadzacyId;
        blockWindow((Stage) addTerminButton.getScene().getWindow(), stage);
    }

    public void openDeleteTermWindow(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/DeleteTermin.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        DeleteTerminController controller = fxmlLoader.getController();
        setTitleAndIconForWindow("Usuń Termin", stage);
        DeleteTerminController.mainController = this;
        DeleteTerminController.terms = actualTerms;
        controller.showTerms();
        blockWindow((Stage) deleteTerminButton.getScene().getWindow(), stage);
    }

    public void openAddStudentWindow(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = loadScene("/org/example/projekt_zpo/AddStudent.fxml");
        AddStudentToGroupController.groupID = actualGroup.getId();
        AddStudentToGroupController.mainController = this;
        stage.setScene(scene);
        setTitleAndIconForWindow("Dodaj Studenta", stage);
        blockWindow((Stage) addStudentButton.getScene().getWindow(), stage);
    }

    public void openAddGroupWindow(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = loadScene("/org/example/projekt_zpo/AddGroup.fxml");
        AddGroupController.mainController = this;
        stage.setScene(scene);
        setTitleAndIconForWindow("Dodaj Grupę", stage);
        blockWindow((Stage) addGroupButton.getScene().getWindow(), stage);
    }

    public void openSetAttendanceForStudentWindow(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/SetAttendanceForStudent.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        SetAttendanceForStudentController controller = fxmlLoader.getController();
        SetAttendanceForStudentController.mainController = this;
        SetAttendanceForStudentController.students = actualStudents;
        SetAttendanceForStudentController.termin = actualTerm;
        controller.setStudents();
        stage.setScene(scene);
        setTitleAndIconForWindow("Ustaw Obecość", stage);
        blockWindow((Stage) setAttendanceForStudentButton.getScene().getWindow(), stage);
    }

    public void openAddStudentToDatabaseWindow(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = loadScene("/org/example/projekt_zpo/AddStudentToDatabase.fxml");
        AddGroupController.mainController = this;
        stage.setScene(scene);
        setTitleAndIconForWindow("Dodaj studenta do Bazy Danych", stage);
        DeleteStudentFromDatabaseController.mainController = this;
        blockWindow((Stage) addStudentToDatabaseButton.getScene().getWindow(), stage);
    }

    public void openDeleteStudentFromDatabaseWindow(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = loadScene("/org/example/projekt_zpo/DeleteStudentFromDatabase.fxml");
        AddGroupController.mainController = this;
        stage.setScene(scene);
        setTitleAndIconForWindow("Usuń studenta z Bazy Danych", stage);
        DeleteStudentFromDatabaseController.mainController = this;
        blockWindow((Stage) deleteStudentFromDatabaseButton.getScene().getWindow(), stage);
    }

    public void blockWindow(Stage ownerStage, Stage newWindowStage) {
        newWindowStage.initOwner(ownerStage);
        newWindowStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        newWindowStage.showAndWait();
    }

    public Scene loadScene(String scenePath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(scenePath));
        return new Scene(fxmlLoader.load());
    }

    public void showStudentsInTableView() {
        setAttendanceForStudentButton.setVisible(false);
        studentsTableView.setVisible(false);
        terminNameLabel.setVisible(false);
        studentsInGroupTableView.setVisible(true);
        actualStudents = getStudents();
        ObservableList<Student> students = FXCollections.observableArrayList(actualStudents);
        for (int i = 0; i < actualStudents.size(); i++) {
            students.set(i, actualStudents.get(i));
        }
        studentsInGroupTableView.setItems(students);
    }
}
