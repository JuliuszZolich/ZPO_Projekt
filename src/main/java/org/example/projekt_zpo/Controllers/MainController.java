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

public class MainController {
    @FXML
    public Button addStudentButton, addTerminButton, deleteTerminButton, deleteStudentButton, logOutButton, deleteStudentFromDatabaseButton, addStudentToDatabaseButton, addGroupButton, setAttendanceForStudentButton;
    @FXML
    public ImageView deleteGroupImageView;
    @FXML
    public ChoiceBox<String> groupChoiceTerminChoiceBox;
    @FXML
    public Label userNameLabel, groupNameLabel, terminNameLabel;
    @FXML
    public TableView<Grupa> groupTableView;
    @FXML
    public TableView<StudentListaObecnosci> studentsTableView;
    @FXML
    public TableColumn<Grupa, String> groupColumn;
    @FXML
    public TableColumn<StudentListaObecnosci, String> studentIndexColumn, attendanceColumn, studentNameColumn, studentSurnameColumn;

    public HttpClient client = HttpClient.newHttpClient();

    public static int prowadzacyId;
    int actualGroup = 0;
    //jak to nazwać XD
    public int isAlive = 0;

    public static ArrayList<Student> actualStudents;
    public static ArrayList<Termin> actualTerms;
    public static ArrayList<StudentListaObecnosci> actualStudentsWithAttendance;
    public Termin actualTerm;

    public void initialize(){

        groupTableView.setOnMouseClicked(event ->
        {
            if (event.getClickCount() == 1) {
                Grupa selectedGroup = groupTableView.getSelectionModel().getSelectedItem();
                actualGroup = selectedGroup.getId();
                try {
                    setTermsForGroup();
                    System.out.println(actualTerms.size());
                    if (!actualTerms.isEmpty()) {
                        showStudentsInGroup();
                    }
                } catch (IOException | URISyntaxException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                setActualGroupTitle(selectedGroup);
            }
        });

        groupChoiceTerminChoiceBox.setOnAction(event -> {
            String selectedTermin = groupChoiceTerminChoiceBox.getValue().toString();
            for (Termin termin : actualTerms) {
                if (termin.getData().equals(selectedTermin)) {
                    actualTerm = termin;
                    break;
                }
            }
        });
    }

    public void refreshAll() {

    }

    public void refreshActualGroup() {

    }

    public void setActualGroup(int id) {
        actualGroup = id;
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
    }

    public void setTermsForGroup() throws URISyntaxException, IOException, InterruptedException {
        groupChoiceTerminChoiceBox.getItems().clear();
        ArrayList<Termin> terminy = getActualTerms();
        for (Termin termin : terminy) {
            if (!terminy.isEmpty()) {
                groupChoiceTerminChoiceBox.setValue(termin.getData().toString());
                terminNameLabel.setText(termin.getNazwa());
            }
            groupChoiceTerminChoiceBox.getItems().add(termin.getData().toString());
        }
    }

    public ArrayList<StudentListaObecnosci> getActualStudentsWithAttendance() {
        ArrayList<StudentListaObecnosci> studentsWithAttendance = new ArrayList<>();
        for (Student student : actualStudents) {
            StudentListaObecnosci temp = new StudentListaObecnosci();
            temp.setStudent(student);
            temp.setAttendance(getAttendanceForStudentInTerm(student.getIndeks()));
            studentsWithAttendance.add(temp);
        }
        return studentsWithAttendance;
    }

    public Obecnosc getAttendanceForStudentInTerm(int idStudenta) {
        Obecnosc attendance = null;
        try {
            HttpResponse<String> response = requestGet("http://localhost:8080/api/sprawdzobecnoscstudenta?studentId=" + idStudenta + "&terminId=" + actualTerm.getId());
            try {
                ObjectMapper mapper = new ObjectMapper();
                attendance = mapper.readValue(response.body(), Obecnosc.class);
            } catch (Exception e) {
                e.printStackTrace();
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
        HttpResponse<String> response = requestGet("http://localhost:8080/api/termingrupa?grupaId=" + actualGroup);
        try {
            ObjectMapper mapper = new ObjectMapper();
            terms = (ArrayList<Termin>) mapper.readValue(response.body(), new TypeReference<List<Termin>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        actualTerms = terms;
        assert actualTerms != null;
        if (!actualTerms.isEmpty()) {
            actualTerm = terms.getFirst();
        }
        return terms;
    }

    public ArrayList<Grupa> getGroups() {
        ArrayList<Grupa> groups = null;
        try {
            HttpResponse<String> response = requestGet("http://localhost:8080/api/grupy");
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
            HttpResponse<String> response = requestGet("http://localhost:8080/api/studencigrupa?grupaId=" + actualGroup);
            try {
                ObjectMapper mapper = new ObjectMapper();
                students = (ArrayList<Student>) mapper.readValue(response.body(), new TypeReference<List<Student>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        actualStudents = students;
        return students;
    }

    public void showStudentsInGroup() throws IOException {
        actualStudents = getStudents();
        ArrayList<StudentListaObecnosci> studentsWithAttendance = getActualStudentsWithAttendance();
        actualStudentsWithAttendance = studentsWithAttendance;
        if (!actualStudentsWithAttendance.isEmpty()) {
            ObservableList<StudentListaObecnosci> studentsList = FXCollections.observableArrayList(studentsWithAttendance);
            for (int i = 0; i < studentsList.size(); i++) {
                studentsList.set(i, studentsWithAttendance.get(i));
            }
            studentsTableView.setItems(studentsList);
        }
    }

    public void showGroupList() {
        ArrayList<Grupa> groups = getGroups();
        ObservableList<Grupa> grupyList = FXCollections.observableArrayList(groups);
        for (int i = 0; i < grupyList.size(); i++) {
            if (isAlive == 0) {
                setActualGroup(groups.get(i).getId());
                setActualGroupTitle(groups.get(i));
                isAlive = 1;
            }
            grupyList.set(i, groups.get(i));
        }
        groupTableView.setItems(grupyList);
    }

    public void logOut(MouseEvent mouseEvent) {
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        stage.close();
    }

    public void openDeleteGroupWindow(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = loadScene("/org/example/projekt_zpo/ConfirmDeleteGroup.fxml");
        ConfirmDeleteGroupController.mainController = this;
        ConfirmDeleteGroupController.groupID = actualGroup;
        stage.setScene(scene);
        setTitleAndIconForWindow("Usuń Grupe", stage);
        blockWindow((Stage) deleteGroupImageView.getScene().getWindow(), stage);
    }

    public void openDeleteStudentWindow(MouseEvent mouseEvent) throws IOException {
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
        AddTerminController.mainController = this;
        AddTerminController.grupaID = actualGroup;
        AddTerminController.prowadzacyId = prowadzacyId;
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
        DeleteTerminController.terminy = actualTerms;
        controller.showTerminy();
        blockWindow((Stage) deleteTerminButton.getScene().getWindow(), stage);
    }

    public void openAddStudentWindow(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = loadScene("/org/example/projekt_zpo/AddStudent.fxml");
        AddStudentController.groupID = actualGroup;
        AddStudentController.mainController = this;
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
        Scene scene = loadScene("/org/example/projekt_zpo/SetAttendanceForStudent.fxml");
        AddGroupController.mainController = this;
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
}
