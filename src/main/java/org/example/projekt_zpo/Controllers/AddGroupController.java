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

public class AddGroupController {
    public static MainController mainController;

    @FXML
    public TextField groupNameTextArea;

    @FXML
    public Button cancelAddGroupButton;

    @FXML
    public Button addGroupButton;

    @FXML
    public Label errorLabel;

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void add(MouseEvent mouseEvent) throws URISyntaxException, IOException, InterruptedException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        String groupName = groupNameTextArea.getText();
        if (groupName.isBlank()) {
            errorLabel.setText("Nie podano nazwy grupy!");
            errorLabel.setVisible(true);
            groupNameTextArea.setText("");
        }
        else{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(ip + "/api/dodajgrupe?nazwa=" + URLEncoder.encode(groupName, StandardCharsets.UTF_8)))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            mainController.refreshScene();
            stage.close();
        }

    }
}
