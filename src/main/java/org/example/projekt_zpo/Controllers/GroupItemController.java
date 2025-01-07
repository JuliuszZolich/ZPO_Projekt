package org.example.projekt_zpo.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class GroupItemController {
    @FXML
    public ImageView DeleteGroupIcon;

    @FXML
    private Label GroupItemName;

    @FXML
    private Line LineUnderGroupItem;

    public void setGroupItemName(String groupItemName) {
        GroupItemName.setText(groupItemName);
    }

    public void setLineUnderGroupItem() {
        LineUnderGroupItem.setVisible(false);
    }


    public void deleteGroup(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/projekt_zpo/ConfirmDeleteGroup.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        setTitleAndIcon("Usuń Studenta", stage);
        Stage ownerStage = (Stage) DeleteGroupIcon.getScene().getWindow();
        stage.initOwner(ownerStage);
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void setTitleAndIcon(String title, Stage stage) {
        stage.setTitle(title);
        Image image = new Image(Objects.requireNonNull(getClass().getResource("/images/app_icon.png")).toExternalForm());
        stage.getIcons().add(image);
    }
}
