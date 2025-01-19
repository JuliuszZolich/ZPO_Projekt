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
import java.util.Date;
import java.util.Objects;

import static org.example.projekt_zpo.AttendenceList.ip;

public class DeleteTerminController {
    @FXML
    public Button cancelButton;

    @FXML
    public Button deleteTermButton;

    @FXML
    public  ChoiceBox<String> choseTerm;

    @FXML
    public Label errorLabel;

    public static ArrayList<Termin> terms;
    public static MainController mainController;

    public void showTerms(){
        choseTerm.getItems().clear();
        for(Termin termin : terms){
            choseTerm.getItems().add(termin.getData().toString());
        }
    }

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void delete(MouseEvent mouseEvent) throws IOException, InterruptedException, URISyntaxException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        if (Objects.equals(choseTerm.getValue(), "Wybierz Termin")) {
            errorLabel.setVisible(true);
            errorLabel.setText("Nie wybrano Terminu!");
        }
        else {
            int id = 0;
            for(Termin termin : terms) {
                if (termin.getData().toString().equals(choseTerm.getValue())) {
                    id = termin.getId();
                    break;
                }
            }
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requestAddGrupa = HttpRequest.newBuilder()
                    .uri(new URI(ip + "/api/usuntermin?terminId=" + id))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpResponse<String> responseAddGrupa = client.send(requestAddGrupa, HttpResponse.BodyHandlers.ofString());
            mainController.refreshActualGroup();
            stage.close();
        }
    }
}
