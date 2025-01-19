package org.example.projekt_zpo;

import javafx.scene.control.Label;

public class Error {
    String error;
    public static Label errorLabel;

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setLabelMessage() {
        errorLabel.setVisible(true);
        errorLabel.setText(error);
    }
}
