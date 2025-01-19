package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
 * Klasa kontrolera odpowiedzialna dodawanie grupy.
 *
 * @author Sebastian Cieślik
 * @version 1.0
 */
public class ConfirmDeleteGroupController {

    /**
     * Przycisk służący do anulowania operacji usunięcia grupy.
     */
    @FXML
    public Button cancelButton;

    /**
     * Przycisk służący do potwierdzenia i usunięcia grupy.
     */
    @FXML
    public Button deleteGroupButton;

    /**
     * Statyczna zmienna przechowująca ID grupy do usunięcia.
     */
    public static int groupID;

    /**
     * Statyczna referencja do głównego kontrolera, która jest używana do odświeżenia głównej sceny po usunięciu grupy.
     */
    public static MainController mainController;

    /**
     * Obsługuje usunięcie grupy po kliknięciu przycisku
     * <p>
     * Metoda ta wysyła żądanie HTTP POST w celu usunięcia grupy o podanym ID, a następnie odświeża główną scenę.
     * </p>
     *
     * @param mouseEvent Zdarzenie kliknięcia przycisku
     * @throws URISyntaxException Błąd związany z formatem URI
     * @throws IOException Błąd wejścia/wyjścia
     * @throws InterruptedException Błąd związany z przerwaniem operacji
     * @since 1.0
     */
    public void delete(MouseEvent mouseEvent) throws URISyntaxException, IOException, InterruptedException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(ip + "/api/usungrupe?grupaId=" + groupID))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        mainController.refreshScene();
        stage.close();
    }

    /**
     * Metoda ta zamyka okno potwierdzenia bez wykonywania żadnej operacji.
     *
     * @param mouseEvent Zdarzenie kliknięcia przycisku
     * @since 1.0
     */
    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
