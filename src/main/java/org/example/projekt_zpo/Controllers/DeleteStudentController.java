package org.example.projekt_zpo.Controllers;

import javafx.event.ActionEvent;
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

public class DeleteStudentController {
    public static MainController mainController;

    public static ArrayList<Student> students;
    @FXML
    public ChoiceBox ChoseStudent;

    @FXML
    public Label DeleteStudentError;

    public void setStudents() {
        ChoseStudent.getItems().clear();
        for (Student student : students) {
            ChoseStudent.getItems().add(student.getIndeks());
        }
    }

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void delete(MouseEvent mouseEvent) throws URISyntaxException, IOException, InterruptedException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        String studentIndex = ChoseStudent.getValue().toString();
        if(studentIndex.equals("Wybierz Studenta")){
            DeleteStudentError.setVisible(true);
            DeleteStudentError.setText("Nie wybrano studenta");
        }
        else{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requestDeleteStudent = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/usunstudentagrupa?studentId=" + studentIndex))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpResponse<String> responseDeleteGrupa = client.send(requestDeleteStudent, HttpResponse.BodyHandlers.ofString());
            mainController.showStudentsInGroup();
            stage.close();
        }
    }
}
