package org.example.projekt_zpo.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.projekt_zpo.Error;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.example.projekt_zpo.AttendanceList.ip;

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
        String indexValue = indexTextArea.getText();
        if (nameValue.isBlank() || surnameValue.isBlank() || indexValue.isBlank()) {
            errorLabel.setVisible(true);
            errorLabel.setText("Pozostawiono puste Pole!");
            nameTextArea.clear();
            surnameTextArea.clear();
            indexTextArea.clear();
        }
        int index = Integer.parseInt(indexValue);
        if(index < 100000 || index > 999999){
            errorLabel.setVisible(true);
            errorLabel.setText("Zły indeks!");
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestAddStudent = HttpRequest.newBuilder()
                .uri(new URI(ip + "/api/dodajstudenta?indeks=" + index + "&imie=" + nameValue + "&nazwisko=" + surnameValue))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        HttpResponse<String> response = client.send(requestAddStudent, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200){
            ObjectMapper mapper = new ObjectMapper();
            org.example.projekt_zpo.Error error = mapper.readValue(response.body(), org.example.projekt_zpo.Error.class);
            nameTextArea.setText("");
            surnameTextArea.setText("");
            indexTextArea.setText("");
            Error.errorLabel = errorLabel;
            error.setLabelMessage();
        }
        else{
            stage.close();
        }
    }

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) surnameTextArea.getScene().getWindow();
        stage.close();
    }
}
