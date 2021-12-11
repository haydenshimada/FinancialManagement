package com.example.financial;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class NewTypeController {
    @FXML
    private ImageView backToMainBtn;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    void backToMain(MouseEvent mouseEvent) throws IOException {
        Parent newScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("View/hello-view.fxml")));
        Scene scene = backToMainBtn.getScene();

        newScene.translateXProperty().set(scene.getWidth() * -1);

        StackPane parentContainer = (StackPane) scene.getRoot();
        parentContainer.getChildren().add(newScene);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(newScene.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);

        timeline.getKeyFrames().add(kf);

        timeline.setOnFinished(e -> {
            parentContainer.getChildren().remove(anchorPane);
        });

        timeline.play();
    }
}
