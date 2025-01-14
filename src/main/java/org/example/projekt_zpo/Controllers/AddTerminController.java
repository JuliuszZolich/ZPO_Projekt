package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
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

public class AddTerminController {
    @FXML
    public Button CancelAddTerminButton;

    @FXML
    public Button AddTerminButton;

    @FXML
    public DatePicker ChoseTermin;

    @FXML
    public Label AddTerminError;

    @FXML
    public TextField NameTermin;

    public static int grupaID;

    public static MainController mainController;

    public static int prowadzacyId;

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void add(MouseEvent mouseEvent) throws URISyntaxException, IOException, InterruptedException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        if(ChoseTermin.getValue() == null) {
            AddTerminError.setVisible(true);
            AddTerminError.setText("Nie wybrano Terminu");
        }
        else if (NameTermin.getText() == null) {
            AddTerminError.setVisible(true);
            AddTerminError.setText("Nie wpisano nazwy Terminu");
        }
        else if(NameTermin.getText().length() > 5){
            AddTerminError.setVisible(true);
            AddTerminError.setText("Za długa nazwa Terminu");
        }
        else{
            String chosenDate = ChoseTermin.getValue().toString();
            String name = NameTermin.getText();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requestAddTermin =  HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/dodajtermin?grupaId=" + grupaID + "&nazwa=" + URLEncoder.encode(name, StandardCharsets.UTF_8) + "&data=" + chosenDate + "&prowadzacyId=" + prowadzacyId))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpResponse<String> responseAddGrupa = client.send(requestAddTermin, HttpResponse.BodyHandlers.ofString());
            mainController.showGroupList();
            stage.close();
        }
    }
}
