package org.example.projekt_zpo.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.projekt_zpo.AttendanceList;
import org.example.projekt_zpo.Prowadzacy;
import org.example.projekt_zpo.Error;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;


/**
 * Kontroler odpowiedzialny za logowanie użytkownika do aplikacji.
 * Obsługuje proces logowania, wyświetlanie błędów, oraz przekierowanie na główną stronę aplikacji.
 * @author Sebastian Cieślik 245787
 * @version 1.0
 * @since 1.0
 */
public class LoginController {
    /** Flaga informująca o statusie logowania użytkownika */
    public static Boolean isLoggedIn = false;

    /** Pole gdzie użytkownik wprowadza login */
    @FXML
    public TextArea loginTextArea;

    /** Przycisk logowania */
    @FXML
    public Button loginButton;

    /** Pole gdzie wyświetlają się błędy przy logowaniu */
    @FXML
    public Label errorLabel;

    /** Pole gdzie użytkownik wprowadza hasło */
    @FXML
    public PasswordField passwordField;

    /**
     * Metoda odpowiedzialna za logowanie użytkownika po naciśnięciu przycisku logowania.
     * Sprawdza poprawność danych logowania, wysyła żądanie do serwera, a następnie przekierowuje na główną stronę aplikacji.
     *
     * @param mouseEvent Zdarzenie kliknięcia przycisku logowania
     * @throws IOException Jeśli wystąpi błąd przy ładowaniu sceny lub komunikacji z serwerem
     */
    public void loginUser(MouseEvent mouseEvent) throws IOException, URISyntaxException, InterruptedException {
        Prowadzacy prowadzacy = null;
        Stage loginStage = (Stage) loginButton.getScene().getWindow();
        String username = loginTextArea.getText();
        String password = passwordField.getText();
        if (assertLoginData(username, password)){
           prowadzacy = login(username, password, null);
           if (isLoggedIn) {
               setDefaultMainScene(prowadzacy, loginStage);
           }
        }
    }

    /**
     * Ustawia tytuł i ikonę okna aplikacji.
     *
     * @param title Tytuł okna aplikacji
     * @param stage Obiekt okna aplikacji
     */
    public void setTitleAndIcon(String title, Stage stage) {
        stage.setTitle(title);
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/app_icon.png")).toExternalForm());
        stage.getIcons().add(image);
    }

    /**
     * Ustawia główną scenę aplikacji po zalogowaniu, przekazując dane użytkownika oraz grupy.
     *
     * @param prowadzacy Obiekt reprezentujący użytkownika (Prowadzacy)
     * @param stage Obiekt okna logowania, które zostanie zamknięte
     * @throws IOException Jeśli wystąpi błąd przy ładowaniu sceny
     */
    public void setDefaultMainScene(Prowadzacy prowadzacy, Stage stage) throws IOException, URISyntaxException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/MainScene.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load());
        Stage mainStage = new Stage();
        setTitleAndIcon("Lista Obecności", mainStage);
        mainStage.setScene(mainScene);
        stage.close();
        mainStage.show();
        MainController mainController = fxmlLoader.getController();
        mainController.setUserName(prowadzacy.getImie(), prowadzacy.getNazwisko());
        MainController.prowadzacyId = prowadzacy.getId();
        mainController.setColumns();
        mainController.refreshScene();
    }

    /**
     * Sprawdza poprawność danych logowania (czy login i hasło nie są puste).
     *
     * @param login Login użytkownika
     * @param password Hasło użytkownika
     * @return True, jeśli dane logowania są poprawne, w przeciwnym razie False
     */
    public boolean assertLoginData(String login, String password) {
        if (login.isBlank() || password.isBlank()) {
            passwordField.setText("");
            loginTextArea.setText("");
            errorLabel.setVisible(true);
            errorLabel.setText("Nie podano Loginu lub hasła!");
            return false;
        }
        return true;
    }

    /**
     * Próbuje zalogować użytkownika na podstawie podanych danych (loginu i hasła).
     * Jeśli dane są poprawne, ustawia flagę isLoggedIn na true i zwraca obiekt Prowadzacy.
     *
     * @param login Login użytkownika
     * @param password Hasło użytkownika
     * @param prowadzacy Obiekt użytkownika (Prowadzacy) do uzupełnienia
     * @return Obiekt Prowadzacy, jeśli logowanie było udane, w przeciwnym razie null
     */
    public Prowadzacy login(String login, String password, Prowadzacy prowadzacy) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest requestLogin = HttpRequest.newBuilder()
                    .uri(new URI( AttendanceList.ip + "/api/login?login=" + login + "&password=" + password))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpResponse<String> responseProwadzacy = null;
            boolean isServerResponding = false;
            try{
                responseProwadzacy = client.send(requestLogin, HttpResponse.BodyHandlers.ofString());
                isServerResponding = true;
            }
            catch (Exception e){
                passwordField.setText("");
                loginTextArea.setText("");
                errorLabel.setVisible(true);
                errorLabel.setText("Serwer nie odpowiada!");
            }
            if(isServerResponding) {
                if (responseProwadzacy.statusCode() != 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    Error error = mapper.readValue(responseProwadzacy.body(), Error.class);
                    passwordField.setText("");
                    loginTextArea.setText("");
                    Error.errorLabel = errorLabel;
                    error.setLabelMessage();
                } else {
                    try {
                        ObjectMapper mapper = new ObjectMapper();
                        prowadzacy = mapper.readValue(responseProwadzacy.body(), Prowadzacy.class);
                        isLoggedIn = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prowadzacy;
    }
}