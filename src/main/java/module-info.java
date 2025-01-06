module org.example.projekt_zpo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires org.json;

    opens org.example.projekt_zpo to javafx.fxml;
    exports org.example.projekt_zpo;
    exports org.example.projekt_zpo.Controllers;
    opens org.example.projekt_zpo.Controllers to javafx.fxml;
}