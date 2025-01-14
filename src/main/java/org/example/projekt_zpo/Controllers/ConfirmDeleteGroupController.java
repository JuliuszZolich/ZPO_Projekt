package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConfirmDeleteGroupController {
    @FXML
    public Label ConfirmationLabel;

    @FXML
    public Button CancelDeleteGroupConfirmation;

    @FXML
    public Button DeleteGroupConfirmationButton;
    private int groupID;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) CancelDeleteGroupConfirmation.getScene().getWindow();
        stage.close();
    }

    public void delete(MouseEvent mouseEvent) throws URISyntaxException, IOException, InterruptedException {
        Stage stage = (Stage) CancelDeleteGroupConfirmation.getScene().getWindow();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestDeleteGrupa = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/usungrupe?grupaId=" + groupID))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();
        HttpResponse<String> responseDeleteGrupa = client.send(requestDeleteGrupa, HttpResponse.BodyHandlers.ofString());
        mainController.isAlive = 0;
        mainController.showGroupList();
        stage.close();
    }
}
