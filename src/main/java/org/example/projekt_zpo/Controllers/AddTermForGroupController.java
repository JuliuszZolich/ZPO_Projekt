package org.example.projekt_zpo.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.projekt_zpo.Error;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static org.example.projekt_zpo.AttendanceList.ip;

public class AddTermForGroupController {
    @FXML
    public Button cancelButton;

    @FXML
    public Button addTermButton;

    @FXML
    public DatePicker choseTerm;

    @FXML
    public Label errorLabel;

    @FXML
    public TextField termNameTextArea;

    public static int grupaID;

    public static MainController mainController;

    public static int prowadzacyId;

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void add(MouseEvent mouseEvent) throws URISyntaxException, IOException, InterruptedException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        if(choseTerm.getValue() == null) {
            errorLabel.setVisible(true);
            errorLabel.setText("Nie wybrano Terminu");
        }
        else if (termNameTextArea.getText() == null) {
            errorLabel.setVisible(true);
            errorLabel.setText("Nie wpisano nazwy Terminu");
        }
        else if(termNameTextArea.getText().length() > 20){
            errorLabel.setVisible(true);
            errorLabel.setText("Za długa nazwa Terminu");
        }
        else{
            String chosenDate = choseTerm.getValue().toString();
            String name = termNameTextArea.getText();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request =  HttpRequest.newBuilder()
                    .uri(new URI(ip + "/api/dodajtermin?grupaId=" + grupaID + "&nazwa=" + URLEncoder.encode(name, StandardCharsets.UTF_8) + "&data=" + chosenDate + "&prowadzacyId=" + prowadzacyId))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() != 200){
                ObjectMapper mapper = new ObjectMapper();
                org.example.projekt_zpo.Error error = mapper.readValue(response.body(), org.example.projekt_zpo.Error.class);
                termNameTextArea.setText("");
                choseTerm.setValue(null);
                Error.errorLabel = errorLabel;
                error.setLabelMessage();
            }
            else{
                mainController.refreshActualGroup();
                stage.close();
            }
        }
    }
}
