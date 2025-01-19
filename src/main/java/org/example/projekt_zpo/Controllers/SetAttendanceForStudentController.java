package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.projekt_zpo.Student;
import org.example.projekt_zpo.Termin;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import static org.example.projekt_zpo.AttendanceList.ip;

/**
 * Kontroler odpowiedzialny za ustawianie obecności studenta na określonym terminie.
 * Umożliwia wybranie studenta oraz statusu obecności (np. obecny, nieobecny, spóźniony, usprawiedliwiony)
 * oraz zapisanie tej informacji w systemie.
 * @author Sebastian Cieślik
 * @version 1.0
 */
public class SetAttendanceForStudentController {

    /**
     * ChoiceBox umożliwiający wybór studenta, dla którego ustawiana jest obecność.
     */
    @FXML
    public ChoiceBox<Integer> choiceStudent;

    /**
     * ChoiceBox umożliwiający wybór statusu obecności studenta.
     * Statusy obecności są zdefiniowane w tablicy `typeOfAttendance`.
     */
    @FXML
    public ChoiceBox<String> choiceAttendance;

    /**
     * Główny kontroler aplikacji, wykorzystywany do odświeżenia widoku po ustawieniu obecności.
     */
    public static MainController mainController;

    /**
     * Lista studentów dostępnych do wyboru w polu wyboru studenta.
     */
    public static ArrayList<Student> students;

    /**
     * Termin, do którego przypisywana jest obecność studenta.
     */
    public static Termin termin;

    /**
     * Tablica z możliwymi typami obecności.
     */
    private final String[] typeOfAttendance = {"nieobecny", "obecny", "spoźniony", "usprawiedliwiony"};

    /**
     * Etykieta wyświetlająca komunikaty o błędach (np. brak wybranego studenta lub obecności).
     */
    @FXML
    public Label errorLabel;

    /**
     * Inicjalizuje dane w polu wyboru statusu obecności (`choiceAttendance`).
     * Dodaje wszystkie dostępne typy obecności do listy w `choiceAttendance`.
     * @since 1.0
     */
    public void initialize() {
        choiceAttendance.getItems().clear();
        choiceAttendance.getItems().addAll(typeOfAttendance);
    }

    /**
     * Metoda zamykająca okno aplikacji po kliknięciu przycisku "Anuluj".
     * @param mouseEvent Zdarzenie kliknięcia myszy, które wywołuje zamknięcie okna.
     * @since 1.0
     */
    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) choiceStudent.getScene().getWindow();
        stage.close();
    }

    /**
     * Metoda odpowiedzialna za zapisanie obecności studenta na danym terminie.
     * Jeśli nie wybrano studenta lub statusu obecności, wyświetla komunikat o błędzie.
     * W przeciwnym razie wysyła zapytanie do serwera, aby zaktualizować obecność studenta.
     * Po pomyślnym zapisaniu, odświeża tabelę obecności grupy.
     * @param mouseEvent Zdarzenie kliknięcia myszy, które wywołuje zapisanie obecności.
     * @throws IOException Jeśli wystąpi problem z wysyłaniem żądania HTTP.
     * @throws InterruptedException Jeśli wystąpi problem z wykonaniem żądania HTTP.
     * @throws URISyntaxException Jeśli wystąpi problem z tworzeniem URI.
     * @since 1.0
     */
    public void set(MouseEvent mouseEvent) throws IOException, InterruptedException, URISyntaxException {
        Stage stage = (Stage) choiceStudent.getScene().getWindow();
        Integer studentID = choiceStudent.getValue();
        String attendance = choiceAttendance.getValue();
        int attendanceToInt = 0;

        if (studentID != null && attendance != null) {
            // Mapowanie wybranego typu obecności na liczbę (1 - nieobecny, 2 - obecny, 3 - spóźniony, 4 - usprawiedliwiony)
            for (int i = 0; i < typeOfAttendance.length; i++) {
                if (typeOfAttendance[i].equals(attendance)) {
                    attendanceToInt = i + 1;
                }
            }

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(ip + "/api/zmienobecnosc?studentId=" + studentID + "&terminId=" + termin.getId() + "&attendance=" + attendanceToInt))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
            mainController.refreshGroupAttendanceTable();
            stage.close();
        } else {
            errorLabel.setVisible(true);
            choiceStudent.setValue(null);
            choiceAttendance.setValue(null);
            errorLabel.setText("Nie wybrano opcji!");
        }
    }

    /**
     * Metoda odpowiedzialna za załadowanie studentów do wyboru w polu {@link #choiceStudent}.
     * Dodaje indeksy studentów do listy w {@link #choiceStudent}.
     * @since 1.0
     */
    public void setStudents() {
        if (students == null) {
            return;
        }
        for (Student student : students) {
            choiceStudent.getItems().add(student.getIndeks());
        }
    }
}
