package org.example.projekt_zpo;

import javafx.fxml.FXML;
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
    public TextField surnameTextArea;
    @FXML
    public TextField nameTextArea;
    @FXML
    public TextField indexTextArea;
    @FXML
    public Label errorLabel;

    public void add(MouseEvent mouseEvent) throws IOException, InterruptedException, URISyntaxException {
        Stage stage = (Stage) surnameTextArea.getScene().getWindow();
        String nameValue = nameTextArea.getText();
        String surnameValue = surnameTextArea.getText();
        String indeksValue = indexTextArea.getText();
        if (nameValue.isBlank() || surnameValue.isBlank() || indeksValue.isBlank()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Pozostawiono puste Pole!");
            nameTextArea.clear();
            surnameTextArea.clear();
            indexTextArea.clear();
        }
        int indeks = Integer.parseInt(indeksValue);
        if(indeks < 100000 || indeks > 999999){
            errorLabel.setVisible(true);
            errorLabel.setText("Zły indeks!");
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
        Stage stage = (Stage) surnameTextArea.getScene().getWindow();
        stage.close();
    }
}
