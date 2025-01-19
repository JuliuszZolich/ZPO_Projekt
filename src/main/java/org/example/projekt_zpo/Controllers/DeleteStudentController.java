package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
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

import static org.example.projekt_zpo.AttendanceList.ip;

/**
 * Kontroler odpowiedzialny za usuwanie studenta z listy.
 * Umożliwia wybór studenta z listy i wysłanie zapytania do serwera w celu usunięcia go z grupy.
 * @author Sebastian Cieślik
 * @version  1.0
 */
public class DeleteStudentController {

    /**
     * Główny kontroler aplikacji, wykorzystywany do odświeżania grupy po usunięciu studenta.
     */
    public static MainController mainController;

    /**
     * Lista studentów dostępnych do usunięcia.
     * Jest to lista obiektów typu {@link Student}, zawierająca wszystkich studentów w danej grupie.
     */
    public static ArrayList<Student> students;

    /**
     * ChoiceBox, w którym użytkownik wybiera studenta do usunięcia.
     * Wartości w tym ChoiceBox są indeksami studentów.
     */
    @FXML
    public ChoiceBox<Integer> choseStudent;

    /**
     * Etykieta wyświetlająca komunikaty o błędach.
     * Jest widoczna, gdy użytkownik nie wybierze studenta z listy.
     */
    @FXML
    public Label errorLabel;

    /**
     * Metoda ustawia listę studentów w polu {@link #choseStudent}.
     * Dodaje do ChoiceBoxa indeksy studentów znajdujących się na liście {@link #students}.
     * @since 1.0
     */
    public void setStudents() {
        choseStudent.getItems().clear();
        for (Student student : students) {
            choseStudent.getItems().add(student.getIndeks());
        }
    }

    /**
     * Metoda zamyka okno aplikacji po kliknięciu przycisku "Anuluj".
     * @param mouseEvent Zdarzenie kliknięcia myszy, które wywołuje zamknięcie okna.
     * @since 1.0
     */
    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Metoda odpowiadająca za usunięcie studenta z grupy.
     * Jeśli nie został wybrany żaden student, wyświetli komunikat o błędzie.
     * W przeciwnym razie, wysyła zapytanie do serwera, aby usunąć wybranego studenta.
     * Po pomyślnym usunięciu studenta, okno zostaje zamknięte.
     * @param mouseEvent Zdarzenie kliknięcia myszy, które wywołuje usunięcie studenta.
     * @throws URISyntaxException Jeśli wystąpi problem z tworzeniem URI.
     * @throws IOException Jeśli wystąpi problem z wysyłaniem żądania HTTP.
     * @throws InterruptedException Jeśli wystąpi problem z wykonaniem żądania HTTP.
     * @since 1.0
     */
    public void delete(MouseEvent mouseEvent) throws URISyntaxException, IOException, InterruptedException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        if(choseStudent.getValue() == null){
            errorLabel.setVisible(true);
            errorLabel.setText("Nie wybrano studenta");
        }
        else{
            String studentIndex = choseStudent.getValue().toString();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(ip + "/api/usunstudentagrupa?studentId=" + studentIndex))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            client.send(request, HttpResponse.BodyHandlers.ofString());
            mainController.refreshActualGroup();
            stage.close();
        }
    }
}
