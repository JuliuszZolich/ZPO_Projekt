package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
 * Kontroler odpowiedzialny za dodawanie grupy
 *
 * @author Sebastian Cieślik
 * @version 1.0
 */
public class AddGroupController {

    /** Główny kontroler aplikacji. */
    public static MainController mainController;

    /** Pole tekstowe do wpisania nazwy grupy. */
    @FXML
    public TextField groupNameTextArea;

    /** Przycisk anulujący dodawanie grupy. */
    @FXML
    public Button cancelAddGroupButton;

    /** Przycisk zatwierdzający dodanie grupy. */
    @FXML
    public Button addGroupButton;

    /** Etykieta wyświetlająca komunikaty o błędach. */
    @FXML
    public Label errorLabel;

    /**
     * Zamyka aktualne okno.
     *
     * @param mouseEvent zdarzenie kliknięcia myszy.
     * @since 1.0
     */
    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Dodaje nową grupę na podstawie wprowadzonej nazwy.
     *
     * @param mouseEvent zdarzenie kliknięcia myszy.
     * @throws URISyntaxException jeśli URI jest nieprawidłowe.
     * @throws IOException jeśli wystąpi problem z wejściem/wyjściem.
     * @throws InterruptedException jeśli operacja zostanie przerwana.
     * @since 1.0
     */
    public void add(MouseEvent mouseEvent) throws URISyntaxException, IOException, InterruptedException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        String groupName = groupNameTextArea.getText();
        if (groupName.isBlank()) {
            errorLabel.setText("Nie podano nazwy grupy!");
            errorLabel.setVisible(true);
            groupNameTextArea.setText("");
        } else {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(ip + "/api/dodajgrupe?nazwa=" + URLEncoder.encode(groupName, StandardCharsets.UTF_8)))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
            mainController.refreshScene();
            stage.close();
        }
    }
}
