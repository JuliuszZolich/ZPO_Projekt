package org.example.projekt_zpo.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.projekt_zpo.Error;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static org.example.projekt_zpo.AttendanceList.ip;
/**
 * Kontroler obsługujący dodawanie nowego terminu dla wybranej grupy.
 * Zarządza logiką interfejsu użytkownika oraz interakcją z API serwera.
 *
 * @author Sebastian Cieślik
 * @version 1.0
 */
public class AddTermForGroupController {

    /**
     * Przycisk anulowania operacji.
     * Zamykana jest scena bez wprowadzania zmian.
     */
    @FXML
    public Button cancelButton;

    /**
     * Przycisk dodawania terminu.
     * Inicjuje proces walidacji i wysyłania danych do API.
     */
    @FXML
    public Button addTermButton;

    /**
     * Pole wyboru daty terminu.
     */
    @FXML
    public DatePicker choseTerm;

    /**
     * Etykieta wyświetlająca komunikaty błędów.
     */
    @FXML
    public Label errorLabel;

    /**
     * Pole tekstowe do wpisania nazwy terminu.
     */
    @FXML
    public TextField termNameTextArea;

    /**
     * Identyfikator grupy, do której dodawany jest termin.
     */
    public static int grupaID;

    /**
     * Główny kontroler aplikacji odpowiedzialny za odświeżanie danych.
     */
    public static MainController mainController;

    /**
     * Identyfikator prowadzącego przypisanego do terminu.
     */
    public static int prowadzacyId;

    /**
     * Obsługuje kliknięcie przycisku anulowania. Powoduje zamknięcie bieżącego okna bez zapisania zmian.
     *
     * @param mouseEvent zdarzenie myszy wywołujące metodę.
     * @since 1.0
     */
    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Obsługuje kliknięcie przycisku dodawania terminu. Wykonuje walidację danych wejściowych i wysyła żądanie do API.
     *
     * @param mouseEvent zdarzenie myszy wywołujące metodę.
     * @throws URISyntaxException jeśli adres URI jest nieprawidłowy.
     * @throws IOException jeśli wystąpi błąd podczas komunikacji z API.
     * @throws InterruptedException jeśli żądanie HTTP zostanie przerwane.
     * @since 1.0
     */
    public void add(MouseEvent mouseEvent) throws URISyntaxException, IOException, InterruptedException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        if (choseTerm.getValue() == null) {
            errorLabel.setVisible(true);
            errorLabel.setText("Nie wybrano Terminu");
        } else if (termNameTextArea.getText() == null) {
            errorLabel.setVisible(true);
            errorLabel.setText("Nie wpisano nazwy Terminu");
        } else if (termNameTextArea.getText().length() > 20) {
            errorLabel.setVisible(true);
            errorLabel.setText("Za długa nazwa Terminu");
        } else {
            String chosenDate = choseTerm.getValue().toString();
            String name = termNameTextArea.getText();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(ip + "/api/dodajtermin?grupaId=" + grupaID + "&nazwa=" + URLEncoder.encode(name, StandardCharsets.UTF_8) + "&data=" + chosenDate + "&prowadzacyId=" + prowadzacyId))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                ObjectMapper mapper = new ObjectMapper();
                org.example.projekt_zpo.Error error = mapper.readValue(response.body(), org.example.projekt_zpo.Error.class);
                termNameTextArea.setText("");
                choseTerm.setValue(null);
                Error.errorLabel = errorLabel;
                error.setLabelMessage();
            } else {
                mainController.refreshActualGroup();
                stage.close();
            }
        }
    }
}
