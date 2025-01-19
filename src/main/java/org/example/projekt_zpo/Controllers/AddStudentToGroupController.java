package org.example.projekt_zpo.Controllers;

import org.example.projekt_zpo.Error;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.example.projekt_zpo.AttendanceList.ip;

/**
 * Klasa AddStudentToGroupController obsługuje proces dodawania studenta do grupy.
 * <p>
 * Waliduje dane wprowadzone przez użytkownika i przesyła je do serwera za pomocą zapytań HTTP.
 * <p>
 * Autor: Sebastian Cieślik
 * @since 1.0
 */
public class AddStudentToGroupController {
    /**
     * Etykieta wyświetlająca komunikaty o błędach.
     */
    @FXML
    public Label errorLabel;

    /**
     * Pole tekstowe, w którym użytkownik wprowadza indeks studenta.
     */
    @FXML
    private TextField addStudentIndex;

    /**
     * Identyfikator grupy, do której dodawany jest student.
     */
    public static int groupID;

    /**
     * Kontroler głównego widoku aplikacji.
     */
    public static MainController mainController;

    /**
     * Anuluje operację i zamyka bieżące okno.
     *
     * @param mouseEvent zdarzenie myszy, które wywołuje metodę
     * @since 1.0
     */
    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Dodaje studenta do grupy na podstawie wprowadzonego indeksu.
     * <p>
     * Metoda weryfikuje poprawność indeksu studenta i przesyła dane do serwera.
     * Jeśli serwer zwróci błąd, metoda wyświetli odpowiedni komunikat.
     *
     * @param mouseEvent zdarzenie myszy, które wywołuje metodę
     * @since 1.0
     */
    public void add(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        try {
            int index = Integer.parseInt(addStudentIndex.getText());
            if (index < 100000 || index > 999999) {
                errorLabel.setVisible(true);
                addStudentIndex.setText("");
                errorLabel.setText("Błędne dane!");
            } else {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(ip + "/api/dodajstudentagrupa?studentId=" + index + "&groupId=" + groupID))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .POST(HttpRequest.BodyPublishers.ofString(""))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    Error error = mapper.readValue(response.body(), Error.class);
                    addStudentIndex.setText("");
                    Error.errorLabel = errorLabel;
                    error.setLabelMessage();
                } else {
                    mainController.refreshActualGroup();
                    stage.close();
                }
            }
        } catch (NumberFormatException e) {
            errorLabel.setVisible(true);
            addStudentIndex.setText("");
            errorLabel.setText("Błędne dane!");
        } catch (InterruptedException | URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
