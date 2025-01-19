package org.example.projekt_zpo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Główna klasa aplikacji odpowiedzialna za uruchomienie programu.
 * Tworzy i wyświetla początkowy ekran logowania.
 *
 * @author Sebastian Cieślik
 * @version 1.0
 */
public class AttendanceList extends Application {

    /**
     * Stała zawierająca adres IP serwera, z którym aplikacja będzie się komunikować.
     */
    public static final String ip = "http://localhost:8080";

    /**
     * Metoda odpowiedzialna za uruchomienie aplikacji, załadowanie ekranu logowania
     * oraz wyświetlenie go użytkownikowi.
     *
     * @param stage Główne okno aplikacji.
     * @throws IOException Jeśli wystąpi błąd podczas ładowania pliku FXML.
     * @since 1.0
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AttendanceList.class.getResource("LoginScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/app_icon.png")).toExternalForm());
        stage.getIcons().add(image);
        stage.setTitle("Lista Obecności - Panel logowania");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metoda uruchamiająca aplikację.
     *
     * @param args Argumenty przekazywane do aplikacji przy uruchomieniu (nie są wykorzystywane w tej aplikacji).
     * @since 1.0
     */
    public static void main(String[] args) {
        launch();
    }
}
