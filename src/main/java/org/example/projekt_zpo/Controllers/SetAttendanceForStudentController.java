package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
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

import static org.example.projekt_zpo.AttendanceList.ip;

public class SetAttendanceForStudentController {
    @FXML
    public ChoiceBox<Integer> choiceStudent;
    @FXML
    public ChoiceBox<String> choiceAttendance;
    public static MainController mainController;
    public static ArrayList<Student> students;
    public static Termin termin;
    private final String[] typeOfAttendance = {"nieobecny", "obecny", "spoźniony", "usprawiedliwiony"};
    @FXML
    public Label errorLabel;

    public void initialize() {
        choiceAttendance.getItems().clear();
        choiceAttendance.getItems().addAll(typeOfAttendance);
    }

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) choiceStudent.getScene().getWindow();
        stage.close();
    }

    public void set(MouseEvent mouseEvent) throws IOException, InterruptedException, URISyntaxException {
        Stage stage = (Stage) choiceStudent.getScene().getWindow();
        Integer studentID = choiceStudent.getValue();
        String attendance = choiceAttendance.getValue();
        int attendanceToInt = 0;
        if(studentID != null && attendance != null) {
            for(int i = 0; i < typeOfAttendance.length; i++) {
                if(typeOfAttendance[i].equals(attendance)) {
                    attendanceToInt = i + 1;
                }
            }
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(ip + "/api/zmienobecnosc?studentId=" + studentID + "&terminId=" + termin.getId() + "&attendance=" + attendanceToInt))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            mainController.refreshGroupAttendanceTable();
            stage.close();
        }
        else{
            errorLabel.setVisible(true);
            choiceStudent.setValue(null);
            choiceAttendance.setValue(null);
            errorLabel.setText("Nie wybrano opcji!");
        }
    }

    public void setStudents() {
        if(students == null) {
            return;
        }
        for (Student student : students) {
            choiceStudent.getItems().add(student.getIndeks());
        }
    }
}
