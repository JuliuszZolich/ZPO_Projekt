package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class AddGroupController {
    public static MainController mainController;

    @FXML
    public TextField GroupNameTextArea;

    @FXML
    public Button CancelAddGroupButton;

    @FXML
    public Button AddGroupButton;

    @FXML
    public Label ErrorMessageLabel;

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void add(MouseEvent mouseEvent) throws URISyntaxException, IOException, InterruptedException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        String groupName = GroupNameTextArea.getText();
        if (groupName.isBlank()) {
            ErrorMessageLabel.setText("Nie podano nazwy grupy!");
            ErrorMessageLabel.setVisible(true);
            GroupNameTextArea.setText("");
        } else if (groupName.length() > 40) {
            ErrorMessageLabel.setText("Za długa nazwa grupy!");
            ErrorMessageLabel.setVisible(true);
            GroupNameTextArea.setText("");
        } else {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requestAddGrupa = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/dodajgrupe?nazwa=" + URLEncoder.encode(groupName, StandardCharsets.UTF_8)))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpResponse<String> responseAddGrupa = client.send(requestAddGrupa, HttpResponse.BodyHandlers.ofString());
            mainController.showGroupList();
            stage.close();
        }

    }
}
