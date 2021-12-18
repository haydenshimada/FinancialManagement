package com.example.financial;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    @FXML
    private ImageView backToMainBtn;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox vbox;

    @FXML
    private Label dateLabel;
    private LocalDate date;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    @FXML
    public void getPreviousDate() {
        date = date.minusMonths(1);
        dateLabel.setText(date.withDayOfMonth(date.lengthOfMonth()).format(dateTimeFormatter));
    }

    @FXML
    public void getNextDate() {
        date = date.plusMonths(1);
        dateLabel.setText(date.withDayOfMonth(date.lengthOfMonth()).format(dateTimeFormatter));
    }

    @FXML
    public void getNowDate() {
        date = LocalDate.now();
        dateLabel.setText(date.withDayOfMonth(date.lengthOfMonth()).format(dateTimeFormatter));
    }

    @FXML
    public void backToMain(MouseEvent mouseEvent) throws IOException {
        Parent newScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("View/hello-view.fxml")));
        Scene scene = backToMainBtn.getScene();

        newScene.translateXProperty().set(scene.getWidth());

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

    private List<History> getData() {
        List<History> types = new ArrayList<>();

        // default list
        types.add(new History(new Type("House", 0, "house.png", "black"), LocalDate.now()));
        types.add(new History(new Type("Shopping", 0, "shopping.png", "orange"), LocalDate.now()));
        types.add(new History(new Type("Food", 0, "food.png", "red"), LocalDate.now()));
        types.add(new History(new Type("Water", 0, "water.png", "blue"), LocalDate.now()));
        types.add(new History(new Type("Electric", 0, "electric.png", "yellow"), LocalDate.now()));

        return types;
    }

    private final List<History> historyList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        historyList.addAll(getData());

        date = LocalDate.now();
        dateLabel.setText(date.withDayOfMonth(date.lengthOfMonth()).format(dateTimeFormatter));

        try {
            for (History i : historyList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("View/historyType.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                HistoryTypeController controller = fxmlLoader.getController();
                controller.setData(i);

                vbox.getChildren().add(anchorPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
