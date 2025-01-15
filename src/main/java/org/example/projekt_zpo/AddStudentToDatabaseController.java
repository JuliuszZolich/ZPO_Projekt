package org.example.projekt_zpo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AddStudentToDatabaseController {
    @FXML
    public TextField SurnameTextArea;
    @FXML
    public TextField NameTextArea;
    @FXML
    public TextField IndeksTextArea;
    @FXML
    public Label ErrorLabel;

    public void add(MouseEvent mouseEvent) throws IOException, InterruptedException, URISyntaxException {
        Stage stage = (Stage) SurnameTextArea.getScene().getWindow();
        String nameValue = NameTextArea.getText();
        String surnameValue = SurnameTextArea.getText();
        String indeksValue = IndeksTextArea.getText();
        if (nameValue.isBlank()) {
            ErrorLabel.setVisible(true);
            ErrorLabel.setText("Pole Imię jest puste!");
        }
        else if (surnameValue.isBlank()) {
            ErrorLabel.setVisible(true);
            ErrorLabel.setText("Pole Nazwisko jest puste!");
        }
        else if (indeksValue.isBlank()) {
            ErrorLabel.setVisible(true);
            ErrorLabel.setText("Pole Indeks jest puste!");
        }
        int indeks = Integer.parseInt(indeksValue);
        if(indeks < 100000 || indeks > 999999){
            ErrorLabel.setVisible(true);
            ErrorLabel.setText("Zły indeks!");
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestAddStudent = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/dodajstudenta?indeks=" + indeks + "&imie=" + nameValue + "&nazwisko=" + surnameValue))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        HttpResponse<String> responseAddStudent = client.send(requestAddStudent, HttpResponse.BodyHandlers.ofString());
        stage.close();
    }

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) SurnameTextArea.getScene().getWindow();
        stage.close();
    }
}
