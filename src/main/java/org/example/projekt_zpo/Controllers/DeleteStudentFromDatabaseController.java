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

import static org.example.projekt_zpo.AttendenceList.ip;

public class DeleteStudentFromDatabaseController {
    @FXML
    public ChoiceBox<Integer> choseStudent;
    @FXML
    public Label errorLabel;

    public static MainController mainController;

    public void initialize() {
        ArrayList<Student> students = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requestTerminy = HttpRequest.newBuilder()
                    .uri(new URI(ip + "/api/studenci"))
                    .GET()
                    .build();
            HttpResponse<String> responseTerms = client.send(requestTerminy, HttpResponse.BodyHandlers.ofString());
            try {
                ObjectMapper mapper = new ObjectMapper();
                students = (ArrayList<Student>) mapper.readValue(responseTerms.body(), new TypeReference<List<Student>>() {
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

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.close();
    }

    public void delete(MouseEvent mouseEvent) throws IOException, InterruptedException, URISyntaxException {
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        String value = choseStudent.getValue().toString();
        if (value.equals("Wybierz Studenta")) {
            errorLabel.setVisible(true);
            errorLabel.setText("Nie wybrano studenta!");
        }
        else {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requestDeleteStudentFromDatabase = HttpRequest.newBuilder()
                    .uri(new URI(ip + "/api/usunstudenta?id=" + value))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpResponse<String> responseDeleteStudentFromDatabase = client.send(requestDeleteStudentFromDatabase, HttpResponse.BodyHandlers.ofString());
            mainController.showStudentsInGroup();
        }
        stage.close();
    }
}
