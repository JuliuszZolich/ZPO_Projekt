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
import org.jetbrains.annotations.NotNull;

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

/**
 * Klasa głównego kontrolera aplikacji.
 * Odpowiada za obsługę interfejsu użytkownika i logikę aplikacji.
 *
 * @author Sebastian Cieślik
 * @version 1.0
 */
public class MainController {

    /**
     * Przycisk do wyświetlania studentów w grupie.
     */
    @FXML
    public Button showStudentsInGroupButton;

    /**
     * Przycisk do dodawania nowego studenta do grupy.
     */
    @FXML
    public Button addStudentButton;

    /**
     * Przycisk do dodawania nowego terminu.
     */
    @FXML
    public Button addTerminButton;

    /**
     * Przycisk do usuwania terminu.
     */
    @FXML
    public Button deleteTerminButton;

    /**
     * Przycisk do usuwania studenta z grupy.
     */
    @FXML
    public Button deleteStudentButton;

    /**
     * Przycisk do wylogowania użytkownika.
     */
    @FXML
    public Button logOutButton;

    /**
     * Przycisk do usuwania studenta z bazy danych.
     */
    @FXML
    public Button deleteStudentFromDatabaseButton;

    /**
     * Przycisk do dodawania studenta do bazy danych.
     */
    @FXML
    public Button addStudentToDatabaseButton;

    /**
     * Przycisk do dodawania nowej grupy.
     */
    @FXML
    public Button addGroupButton;

    /**
     * Przycisk do ustawiania obecności dla studenta.
     */
    @FXML
    public Button setAttendanceForStudentButton;

    /**
     * Obraz przedstawiający ikonę usuwania grupy.
     */
    @FXML
    public ImageView deleteGroupImageView;

    /**
     * Pole wyboru terminu w grupie.
     */
    @FXML
    public ChoiceBox<String> groupChoiceTermChoiceBox;

    /**
     * Etykieta wyświetlająca imię i nazwisko użytkownika.
     */
    @FXML
    public Label userNameLabel;

    /**
     * Etykieta wyświetlająca nazwę grupy.
     */
    @FXML
    public Label groupNameLabel;

    /**
     * Etykieta wyświetlająca nazwę terminu.
     */
    @FXML
    public Label terminNameLabel;

    /**
     * Etykieta wyświetlająca informację o wybranym terminie.
     */
    @FXML
    public Label choiceTerminLabel;

    /**
     * Tabela wyświetlająca grupy.
     */
    @FXML
    public TableView<Grupa> groupTableView;

    /**
     * Tabela wyświetlająca studentów z listą obecności.
     */
    @FXML
    public TableView<StudentListaObecnosci> studentsTableView;

    /**
     * Kolumna wyświetlająca nazwy grup.
     */
    @FXML
    public TableColumn<Grupa, String> groupColumn;

    /**
     * Kolumna wyświetlająca indeksy studentów.
     */
    @FXML
    public TableColumn<StudentListaObecnosci, String> studentIndexColumn;

    /**
     * Kolumna wyświetlająca obecności studentów.
     */
    @FXML
    public TableColumn<StudentListaObecnosci, String> attendanceColumn;

    /**
     * Kolumna wyświetlająca imiona studentów.
     */
    @FXML
    public TableColumn<StudentListaObecnosci, String> studentNameColumn;

    /**
     * Kolumna wyświetlająca nazwiska studentów.
     */
    @FXML
    public TableColumn<StudentListaObecnosci, String> studentSurnameColumn;

    /**
     * Linia wyświetlana po prawej stronie interfejsu.
     */
    @FXML
    public Line rightLine;

    /**
     * Tabela wyświetlająca studentów w grupie.
     */
    @FXML
    public TableView<Student> studentsInGroupTableView;

    /**
     * Kolumna wyświetlająca indeksy studentów w grupie.
     */
    @FXML
    public TableColumn<Student, Integer> studentIndexStudentsInGroup;

    /**
     * Kolumna wyświetlająca imiona studentów w grupie.
     */
    @FXML
    public TableColumn<Student, String> studentNameStudentsInGroup;

    /**
     * Kolumna wyświetlająca nazwiska studentów w grupie.
     */
    @FXML
    public TableColumn<Student, String> studentSurnameStudentsInGroup;

    /**
     * Klient HTTP do wykonywania zapytań.
     */
    public HttpClient client = HttpClient.newHttpClient();

    /**
     * ID prowadzącego.
     */
    public static int prowadzacyId;

    /**
     * Aktualnie wybrana grupa.
     */
    public static Grupa actualGroup;

    /**
     * Lista aktualnych studentów.
     */
    public static ArrayList<Student> actualStudents;

    /**
     * Lista aktualnych terminów.
     */
    public static ArrayList<Termin> actualTerms;

    /**
     * Lista studentów z przypisaną obecnością.
     */
    public static ArrayList<StudentListaObecnosci> actualStudentsWithAttendance;

    /**
     * Aktualnie wybrany termin.
     */
    public Termin actualTerm;

    /**
     * Inicjalizuje kontroler i ustawia obsługę zdarzeń dla komponentów interfejsu.
     *
     * @since 1.0
     */
    public void initialize() {
        groupTableView.setOnMouseClicked(event -> {
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

    /**
     * Odświeża widok sceny głównej i resetuje dane.
     *
     * @since 1.0
     */
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

    /**
     * Odświeża tabelę z obecnościami dla wybranego terminu.
     *
     * @since 1.0
     */
    public void refreshGroupAttendanceTable() {
        terminNameLabel.setVisible(true);
        choiceTerminLabel.setVisible(true);
        studentsTableView.setVisible(true);
        setAttendanceForStudentButton.setVisible(true);
        studentsInGroupTableView.setVisible(false);
        terminNameLabel.setText(actualTerm.getNazwa());
        showStudentsInGroup();
    }

    /**
     * Ustawia widoczność przycisków i komponentów związanych z grupami.
     *
     * @param value Wartość logiczna określająca widoczność.
     * @since 1.0
     */
    public void setGroupButtons(Boolean value) {
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

    /**
     * Odświeża dane dla aktualnie wybranej grupy.
     *
     * @throws URISyntaxException Błąd składni URI.
     * @throws IOException Błąd wejścia/wyjścia.
     * @throws InterruptedException Przerwanie operacji.
     * @since 1.0
     */
    public void refreshActualGroup() throws URISyntaxException, IOException, InterruptedException {
        groupChoiceTermChoiceBox.getItems().clear();
        studentsTableView.setVisible(false);
        terminNameLabel.setVisible(false);
        studentsInGroupTableView.setVisible(true);
        choiceTerminLabel.setVisible(true);
        actualStudents = null;
        showStudentsInTableView();
        setGroupButtons(true);
        setTermsForGroup();
        setActualGroupTitle(actualGroup);
    }

    /**
     * Ustawia imię i nazwisko użytkownika na etykiecie.
     *
     * @param userName Imię użytkownika.
     * @param userLastName Nazwisko użytkownika.
     * @since 1.0
     */
    public void setUserName(String userName, String userLastName) {
        userNameLabel.setText(userName + " " + userLastName);
    }

    /**
     * Ustawia tytuł i ikonę okna.
     *
     * @param title Tytuł okna.
     * @param stage Obiekt okna.
     * @since 1.0
     */
    public void setTitleAndIconForWindow(String title, @NotNull Stage stage) {
        stage.setTitle(title);
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/app_icon.png")).toExternalForm());
        stage.getIcons().add(image);
    }

    /**
     * Ustawia tytuł aktualnie wybranej grupy.
     *
     * @param selectedGroup Wybrana grupa.
     * @since 1.0
     */
    void setActualGroupTitle(@NotNull Grupa selectedGroup) {
        groupNameLabel.setText(selectedGroup.getNazwa());
    }

    /**
     * Ustawia kolumny tabel na odpowiednie dane.
     *
     * @since 1.0
     */
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

    /**
     * Pobiera terminy dla wybranej grupy i ustawia je w polu wyboru.
     *
     * @throws URISyntaxException Błąd składni URI.
     * @throws IOException Błąd wejścia/wyjścia.
     * @throws InterruptedException Przerwanie operacji.
     * @since 1.0
     */
    public void setTermsForGroup() throws URISyntaxException, IOException, InterruptedException {
        groupChoiceTermChoiceBox.getItems().clear();
        ArrayList<Termin> terms = getActualTerms();
        if (terms != null) {
            for (Termin term : terms) {
                groupChoiceTermChoiceBox.getItems().add(term.getData());
            }
        }
    }

    /**
     * Pobiera listę studentów z przypisaną obecnością dla bieżącego terminu.
     *
     * @return Lista obiektów StudentListaObecnosci.
     * @since 1.0
     */
    public ArrayList<StudentListaObecnosci> getActualStudentsWithAttendance() {
        ArrayList<StudentListaObecnosci> studentsWithAttendance = new ArrayList<>();
        if (actualStudents != null) {
            for (Student student : actualStudents) {
                StudentListaObecnosci temp = new StudentListaObecnosci();
                temp.setStudent(student);
                temp.setAttendance(getAttendanceForStudentInTerm(student.getIndeks()));
                studentsWithAttendance.add(temp);
            }
            return studentsWithAttendance;
        }
        return null;
    }

    /**
     * Pobiera obecność dla danego studenta w bieżącym terminie.
     *
     * @param idStudenta ID studenta.
     * @return Obiekt Obecnosc.
     * @since 1.0
     */
    public Obecnosc getAttendanceForStudentInTerm(int idStudenta) {
        Obecnosc attendance = null;
        try {
            HttpResponse<String> response = requestGet(ip + "/api/sprawdzobecnoscstudenta?studentId=" + idStudenta + "&terminId=" + actualTerm.getId());
            if (response.statusCode() == 200) {
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

    /**
     * Wysyła żądanie GET na wskazany URI.
     *
     * @param uri Adres URI.
     * @return Obiekt HttpResponse zawierający odpowiedź serwera.
     * @throws URISyntaxException Błąd składni URI.
     * @throws IOException Błąd wejścia/wyjścia.
     * @throws InterruptedException Przerwanie operacji.
     * @since 1.0
     */
    public HttpResponse<String> requestGet(String uri) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(uri)).GET().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Pobiera listę terminów dla bieżącej grupy.
     *
     * @return Lista obiektów Termin.
     * @throws URISyntaxException Błąd składni URI.
     * @throws IOException Błąd wejścia/wyjścia.
     * @throws InterruptedException Przerwanie operacji.
     * @since 1.0
     */
    public ArrayList<Termin> getActualTerms() throws URISyntaxException, IOException, InterruptedException {
        ArrayList<Termin> terms = null;
        HttpResponse<String> response = requestGet(ip + "/api/termingrupa?grupaId=" + actualGroup.getId());
        if (response.statusCode() == 200) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                terms = (ArrayList<Termin>) mapper.readValue(response.body(), new TypeReference<List<Termin>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        actualTerms = terms;
        return terms;
    }
    /**
     * Pobiera listę grup.
     *
     * @return Lista grup.
     * @since 1.0
     */
    public ArrayList<Grupa> getGroups() {
        ArrayList<Grupa> groups = null;
        try {
            HttpResponse<String> response = requestGet(ip + "/api/grupy");
            try {
                ObjectMapper mapper = new ObjectMapper();
                groups = (ArrayList<Grupa>) mapper.readValue(response.body(), new TypeReference<List<Grupa>>() {});
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return groups;
    }

    /**
     * Pobiera listę studentów dla wybranej grupy.
     *
     * @return Lista studentów.
     * @since 1.0
     */
    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = null;
        try {
            HttpResponse<String> response = requestGet(ip + "/api/studencigrupa?grupaId=" + actualGroup.getId());
            if (response.statusCode() == 200) {
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    students = (ArrayList<Student>) mapper.readValue(response.body(), new TypeReference<List<Student>>() {});
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

    /**
     * Wyświetla studentów w grupie wraz z ich obecnościami.
     *
     * @since 1.0
     */
    public void showStudentsInGroup() {
        actualStudents = getStudents();
        ArrayList<StudentListaObecnosci> studentsWithAttendance = null;
        if (actualStudents != null) {
            studentsWithAttendance = getActualStudentsWithAttendance();
        }
        if (studentsWithAttendance != null) {
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

    /**
     * Wyświetla listę grup w tabeli.
     *
     * @since 1.0
     */
    public void showStudentsGroupList() {
        ArrayList<Grupa> groups = getGroups();
        ObservableList<Grupa> grupyList = FXCollections.observableArrayList(groups);
        for (int i = 0; i < grupyList.size(); i++) {
            grupyList.set(i, groups.get(i));
        }
        groupTableView.setItems(grupyList);
    }

    /**
     * Wylogowuje użytkownika i otwiera ekran logowania.
     *
     * @param mouseEvent Zdarzenie kliknięcia.
     * @throws IOException Błąd wejścia/wyjścia.
     * @since 1.0
     */
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

    /**
     * Otwiera okno usuwania grupy.
     *
     * @param mouseEvent Zdarzenie kliknięcia.
     * @throws IOException Błąd wejścia/wyjścia.
     * @since 1.0
     */
    public void openDeleteGroupWindow(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = loadScene("/org/example/projekt_zpo/ConfirmDeleteGroup.fxml");
        ConfirmDeleteGroupController.mainController = this;
        ConfirmDeleteGroupController.groupID = actualGroup.getId();
        stage.setScene(scene);
        setTitleAndIconForWindow("Usuń Grupe", stage);
        blockWindow((Stage) deleteGroupImageView.getScene().getWindow(), stage);
    }

    /**
     * Otwiera okno usuwania studenta z grupy.
     *
     * @param mouseEvent Zdarzenie kliknięcia.
     * @throws IOException Błąd wejścia/wyjścia.
     * @since 1.0
     */
    public void openDeleteStudentWindow(MouseEvent mouseEvent) throws IOException {
        if (actualStudents == null) {
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

    /**
     * Otwiera okno dodawania terminu.
     *
     * @param mouseEvent Zdarzenie kliknięcia.
     * @throws IOException Błąd wejścia/wyjścia.
     * @since 1.0
     */
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

    /**
     * Otwiera okno usuwania terminu.
     *
     * @param mouseEvent Zdarzenie kliknięcia.
     * @throws IOException Błąd wejścia/wyjścia.
     * @since 1.0
     */
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

    /**
     * Otwiera okno dodawania studenta do grupy.
     *
     * @param mouseEvent Zdarzenie kliknięcia.
     * @throws IOException Błąd wejścia/wyjścia.
     * @since 1.0
     */
    public void openAddStudentWindow(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = loadScene("/org/example/projekt_zpo/AddStudent.fxml");
        AddStudentToGroupController.groupID = actualGroup.getId();
        AddStudentToGroupController.mainController = this;
        stage.setScene(scene);
        setTitleAndIconForWindow("Dodaj Studenta", stage);
        blockWindow((Stage) addStudentButton.getScene().getWindow(), stage);
    }

    /**
     * Otwiera okno dodawania nowej grupy.
     *
     * @param mouseEvent Zdarzenie kliknięcia.
     * @throws IOException Błąd wejścia/wyjścia.
     * @since 1.0
     */
    public void openAddGroupWindow(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = loadScene("/org/example/projekt_zpo/AddGroup.fxml");
        AddGroupController.mainController = this;
        stage.setScene(scene);
        setTitleAndIconForWindow("Dodaj Grupę", stage);
        blockWindow((Stage) addGroupButton.getScene().getWindow(), stage);
    }

    /**
     * Otwiera okno ustawiania obecności dla studenta.
     *
     * @param mouseEvent Zdarzenie kliknięcia.
     * @throws IOException Błąd wejścia/wyjścia.
     * @since 1.0
     */
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

    /**
     * Otwiera okno dodawania studenta do bazy danych.
     *
     * @param mouseEvent Zdarzenie kliknięcia.
     * @throws IOException Błąd wejścia/wyjścia.
     * @since 1.0
     */
    public void openAddStudentToDatabaseWindow(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = loadScene("/org/example/projekt_zpo/AddStudentToDatabase.fxml");
        AddGroupController.mainController = this;
        stage.setScene(scene);
        setTitleAndIconForWindow("Dodaj studenta do Bazy Danych", stage);
        DeleteStudentFromDatabaseController.mainController = this;
        blockWindow((Stage) addStudentToDatabaseButton.getScene().getWindow(), stage);
    }

    /**
     * Otwiera okno usuwania studenta z bazy danych.
     *
     * @param mouseEvent Zdarzenie kliknięcia.
     * @throws IOException Błąd wejścia/wyjścia.
     * @since 1.0
     */
    public void openDeleteStudentFromDatabaseWindow(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Scene scene = loadScene("/org/example/projekt_zpo/DeleteStudentFromDatabase.fxml");
        AddGroupController.mainController = this;
        stage.setScene(scene);
        setTitleAndIconForWindow("Usuń studenta z Bazy Danych", stage);
        DeleteStudentFromDatabaseController.mainController = this;
        blockWindow((Stage) deleteStudentFromDatabaseButton.getScene().getWindow(), stage);
    }

    /**
     * Blokuje okno nadrzędne podczas otwierania nowego okna.
     *
     * @param ownerStage Okno nadrzędne.
     * @param newWindowStage Nowe okno.
     * @since 1.0
     */
    public void blockWindow(Stage ownerStage, @NotNull Stage newWindowStage) {
        newWindowStage.initOwner(ownerStage);
        newWindowStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        newWindowStage.showAndWait();
    }

    /**
     * Ładuje scenę z podanej ścieżki.
     *
     * @param scenePath Ścieżka do pliku sceny.
     * @return Obiekt sceny.
     * @throws IOException Błąd wejścia/wyjścia.
     * @since 1.0
     */
    public Scene loadScene(String scenePath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(scenePath));
        return new Scene(fxmlLoader.load());
    }

    /**
     * Wyświetla studentów w tabeli grupy.
     *
     * @since 1.0
     */
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