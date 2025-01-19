package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.projekt_zpo.Termin;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Objects;

import static org.example.projekt_zpo.AttendanceList.ip;

/**
 * Kontroler odpowiedzialny za usunięcie terminu z systemu.
 * Umożliwia wybór terminu z listy oraz wysłanie zapytania do serwera w celu jego usunięcia.
 * @author Sebastian Cieślik
 * @version 1.0
 */
public class DeleteTerminController {

    /**
     * Przycisk do anulowania operacji usuwania.
     */
    @FXML
    public Button cancelButton;

    /**
     * Przycisk do usunięcia wybranego terminu.
     */
    @FXML
    public Button deleteTermButton;

    /**
     * ChoiceBox, w którym użytkownik wybiera termin do usunięcia.
     * Wartości w tym ChoiceBox to daty terminów dostępnych do usunięcia.
     */
    @FXML
    public  ChoiceBox<String> choseTerm;

    /**
     * Etykieta wyświetlająca komunikaty o błędach.
     * Jest widoczna, gdy użytkownik nie wybierze terminu przed próbą jego usunięcia.
     */
    @FXML
    public Label errorLabel;

    /**
     * Lista dostępnych terminów do usunięcia.
     * Zawiera obiekty klasy {@link Termin} reprezentujące poszczególne terminy.
     */
    public static ArrayList<Termin> terms;

    /**
     * Główny kontroler aplikacji, wykorzystywany do odświeżenia widoku po usunięciu terminu.
     */
    public static MainController mainController;

    /**
     * Metoda wyświetlająca dostępne terminy w polu {@link #choseTerm}.
     * Dodaje do ChoiceBoxa daty terminów dostępnych do usunięcia.
     * @since 1.0
     */
    public void showTerms() {
        choseTerm.getItems().clear();
        for(Termin termin : terms) {
            choseTerm.getItems().add(termin.getData().toString());
        }
    }

    /**
     * Metoda zamykająca okno aplikacji po kliknięciu przycisku "Anuluj".
     * @param mouseEvent Zdarzenie kliknięcia myszy, które wywołuje zamknięcie okna.
     * @since 1.0
     */
    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Metoda odpowiedzialna za usunięcie wybranego terminu z systemu.
     * Jeśli nie wybrano terminu, wyświetli komunikat o błędzie.
     * W przeciwnym razie, wysyła zapytanie do serwera, aby usunąć wybrany termin.
     * Po pomyślnym usunięciu terminu, odświeża widok grupy.
     * @param mouseEvent Zdarzenie kliknięcia myszy, które wywołuje usunięcie terminu.
     * @throws IOException Jeśli wystąpi problem z wysyłaniem żądania HTTP.
     * @throws InterruptedException Jeśli wystąpi problem z wykonaniem żądania HTTP.
     * @throws URISyntaxException Jeśli wystąpi problem z tworzeniem URI.
     * @since 1.0
     */
    public void delete(MouseEvent mouseEvent) throws IOException, InterruptedException, URISyntaxException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        if (Objects.equals(choseTerm.getValue(), "Wybierz Termin")) {
            errorLabel.setVisible(true);
            errorLabel.setText("Nie wybrano Terminu!");
        } else {
            int id = 0;
            for (Termin termin : terms) {
                if (termin.getData().toString().equals(choseTerm.getValue())) {
                    id = termin.getId();
                    break;
                }
            }
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(ip + "/api/usuntermin?terminId=" + id))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            mainController.refreshActualGroup();
            stage.close();
        }
    }
}
