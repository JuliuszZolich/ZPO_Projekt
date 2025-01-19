package org.example.projekt_zpo.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.projekt_zpo.Student;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.example.projekt_zpo.AttendanceList.ip;

/**
 * Kontroler odpowiedzialny za usunięcie studenta z bazy danych.
 * Umożliwia wybór studenta z listy pobranej z serwera oraz wysyłanie zapytania do serwera w celu usunięcia studenta.
 * @author Sebastian Cieślik
 * @version  1.0
 */
public class DeleteStudentFromDatabaseController {

    /**
     * ChoiceBox, w którym użytkownik wybiera studenta do usunięcia z bazy danych.
     * Wartości w tym ChoiceBox to indeksy studentów pobrane z serwera.
     */
    @FXML
    public ChoiceBox<Integer> choseStudent;

    /**
     * Etykieta wyświetlająca komunikaty o błędach.
     * Jest widoczna, gdy użytkownik nie wybierze studenta do usunięcia.
     */
    @FXML
    public Label errorLabel;

    /**
     * Główny kontroler aplikacji, wykorzystywany do odświeżenia listy studentów po usunięciu studenta.
     */
    public static MainController mainController;

    /**
     * Metoda inicjalizująca kontroler. Pobiera listę studentów z serwera i ustawia ją w polu {@link #choseStudent}.
     * Lista studentów jest pobierana z endpointu API `/api/studenci`.
     * Indeksy studentów są dodawane do ChoiceBoxa, aby użytkownik mógł je wybrać.
     * @since 1.0
     */
    public void initialize() {
        ArrayList<Student> students = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(ip + "/api/studenci"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
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
        assert students != null;
        choseStudent.getItems().clear();
        for(Student student : students){
            choseStudent.getItems().add(student.getIndeks());
        }
    }

    /**
     * Metoda zamykająca okno aplikacji po kliknięciu przycisku "Anuluj".
     * @param mouseEvent Zdarzenie kliknięcia myszy, które wywołuje zamknięcie okna.
     * @since 1.0
     */
    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.close();
    }

    /**
     * Metoda odpowiedzialna za usunięcie studenta z bazy danych.
     * Jeśli nie został wybrany żaden student, wyświetla komunikat o błędzie.
     * W przeciwnym razie, wysyła zapytanie do serwera, aby usunąć wybranego studenta.
     * Po pomyślnym usunięciu studenta, odświeża listę studentów w głównym oknie.
     * @param mouseEvent Zdarzenie kliknięcia myszy, które wywołuje usunięcie studenta.
     * @throws IOException Jeśli wystąpi problem z wysyłaniem żądania HTTP.
     * @throws InterruptedException Jeśli wystąpi problem z wykonaniem żądania HTTP.
     * @throws URISyntaxException Jeśli wystąpi problem z tworzeniem URI.
     * @since 1.0
     */
    public void delete(MouseEvent mouseEvent) throws IOException, InterruptedException, URISyntaxException {
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        String value = choseStudent.getValue().toString();
        if (value.equals("Wybierz Studenta")) {
            errorLabel.setVisible(true);
            errorLabel.setText("Nie wybrano studenta!");
        }
        else {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(ip + "/api/usunstudenta?id=" + value))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            mainController.showStudentsInGroup();
        }
        stage.close();
    }
}
