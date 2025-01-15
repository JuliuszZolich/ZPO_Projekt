package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.projekt_zpo.Termin;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class DeleteTerminController {
    @FXML
    public Button CancelDeleteTerminButton;

    @FXML
    public Button DeleteTerminButton;

    @FXML
    public  ChoiceBox ChoseTermin;

    @FXML
    public Label DeleteTerminError;

    public static ArrayList<Termin> terminy;
    public static MainController mainController;

    public void showTerminy(){
        ChoseTermin.getItems().clear();
        for(Termin termin : terminy){
            ChoseTermin.getItems().add(termin.getData());
        }
    }

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void delete(MouseEvent mouseEvent) throws IOException, InterruptedException, URISyntaxException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        if (ChoseTermin.getValue() == "Wybierz Termin") {
            DeleteTerminError.setVisible(true);
            DeleteTerminError.setText("Nie wybrano Terminu!");
        }
        else {
            int id = 0;
            for(Termin termin : terminy) {
                if (termin.getData().equals(ChoseTermin.getValue())) {
                    id = termin.getId();
                    break;
                }
            }
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requestAddGrupa = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/usuntermin?terminId=" + id))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpResponse<String> responseAddGrupa = client.send(requestAddGrupa, HttpResponse.BodyHandlers.ofString());
            mainController.setTermsForGroup();
            stage.close();
        }
    }
}
