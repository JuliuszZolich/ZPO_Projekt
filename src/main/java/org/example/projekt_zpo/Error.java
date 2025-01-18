package org.example.projekt_zpo;

import javafx.scene.control.Label;

public class Error {
    String error;
    int error_code;

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public int getError_code() {
        return error_code;
    }

    public void setLabelMessage(Label label) {
        label.setVisible(true);
        label.setText(error);
    }
}
