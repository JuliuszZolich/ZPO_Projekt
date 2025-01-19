package org.example.projekt_zpo;

import javafx.scene.control.Label;

/**
 * Klasa odpowiedzialna za obsługę błędów w aplikacji.
 * Umożliwia ustawienie komunikatu o błędzie oraz wyświetlenie go w odpowiedniej etykiecie.
 *
 * @author Sebastian Cieślik
 * @version 1.0
 */
public class Error {

    /**
     * Zmienna przechowująca komunikat o błędzie.
     */
    String error;

    /**
     * Statyczna etykieta, która wyświetla komunikat o błędzie w interfejsie użytkownika.
     */
    public static Label errorLabel;

    /**
     * Ustawia komunikat o błędzie.
     *
     * @param error Komunikat o błędzie do ustawienia.
     * @since 1.0
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Zwraca obecny komunikat o błędzie.
     *
     * @return Komunikat o błędzie.
     * @since 1.0
     */
    public String getError() {
        return error;
    }

    /**
     * Ustawia wiadomość o błędzie w etykiecie i wyświetla ją.
     *
     * @since 1.0
     */
    public void setLabelMessage() {
        errorLabel.setVisible(true);
        errorLabel.setText(error);
    }
}
