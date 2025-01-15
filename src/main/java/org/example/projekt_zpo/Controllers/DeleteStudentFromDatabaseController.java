package org.example.projekt_zpo.Controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.projekt_zpo.Student;
import org.example.projekt_zpo.Termin;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class DeleteStudentFromDatabaseController {
    @FXML
    public Label DeleteStudentError;
    @FXML
    public ChoiceBox ChoseStudent;
    @FXML
    public Label AddStudentErrorLabel;

    public static MainController mainController;

    public void initialize() {
        ArrayList<Student> students = null;
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requestTerminy = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/studenci"))
                    .GET()
                    .build();
            HttpResponse<String> responseTerimy = client.send(requestTerminy, HttpResponse.BodyHandlers.ofString());
            try {
                ObjectMapper mapper = new ObjectMapper();
                students = (ArrayList<Student>) mapper.readValue(responseTerimy.body(), new TypeReference<List<Student>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert students != null;
        ChoseStudent.getItems().clear();
        for(Student student : students){
            ChoseStudent.getItems().add(student.getIndeks());
        }
    }

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) DeleteStudentError.getScene().getWindow();
        stage.close();
    }

    public void delete(MouseEvent mouseEvent) throws IOException, InterruptedException, URISyntaxException {
        Stage stage = (Stage) DeleteStudentError.getScene().getWindow();
        String value = ChoseStudent.getValue().toString();
        if (value.equals("Wybierz Studenta")) {
            AddStudentErrorLabel.setVisible(true);
            AddStudentErrorLabel.setText("Nie wybrano studenta!");
        }
        else {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requestAddGrupa = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/usunstudenta?id=" + value))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpResponse<String> responseAddGrupa = client.send(requestAddGrupa, HttpResponse.BodyHandlers.ofString());
            mainController.showStudentsInGroup();
        }
        stage.close();
    }
}
