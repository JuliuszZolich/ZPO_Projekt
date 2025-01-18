package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AddStudentToGroupController {
    @FXML
    public Label errorLabel;
    @FXML
    private TextField addStudentIndex;
    public static int groupID;
    public static MainController mainController;

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void add(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        try {
            int index = Integer.parseInt(addStudentIndex.getText());
            if (index < 100000 || index > 999999) {
                errorLabel.setVisible(true);
                addStudentIndex.setText("");
                errorLabel.setText("Błędne dane!");
            }
            else{
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest requestAddGrupa = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:8080/api/dodajstudentagrupa?studentId=" + index + "&groupId=" + groupID))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .POST(HttpRequest.BodyPublishers.ofString(""))
                        .build();
                HttpResponse<String> responseAddGrupa = client.send(requestAddGrupa, HttpResponse.BodyHandlers.ofString());
                mainController.showStudentsInGroup();
                stage.close();
            }
        }
        catch (NumberFormatException e) {
            errorLabel.setVisible(true);
            addStudentIndex.setText("");
            errorLabel.setText("Błędne dane!");
        } catch (InterruptedException | URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
