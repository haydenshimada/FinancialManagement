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
import javafx.scene.control.ListView;
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

public class EditController implements Initializable {
    @FXML
    private ImageView backToMainBtn;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label emptyLabel;

    @FXML
    private VBox vbox;

    @FXML
    private Label dateLabel;
    private LocalDate date;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    private List<Type> typeList = new ArrayList<>();

    private List<Type> getData() {
        List<Type> types = new ArrayList<>();

        // default list
        types.add(new Type("House", 10, "house.png", "black"));
        types.add(new Type("Shopping", 20, "shopping.png", "orange"));
        types.add(new Type("Food", 0, "food.png", "red"));
        types.add(new Type("Water", 0, "water.png", "blue"));
        types.add(new Type("Electric", 0, "electric.png", "yellow"));
        types.add(new Type("Transport", 0, "travel.png", "green"));
        types.add(new Type("Internet", 0, "internet.png", "cyan"));
        types.add(new Type("Health", 0, "health.png", "pink"));

        return types;
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

    private EditListener editListener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set default date to real time
        date = LocalDate.now();
        dateLabel.setText(date.withDayOfMonth(date.lengthOfMonth()).format(dateTimeFormatter));

        typeList.addAll(getData());
        emptyLabel.setVisible(typeList.isEmpty());

        editListener = new EditListener() {
            @Override
            public void deleteClick(Type type) {
                typeList.remove(type);
                for (Type i : typeList) {
                    System.out.println(i.getType());
                }
                System.out.println();

                /*
                int i = indexOf(typeList, type);
                if (i != -1) {
                    vbox.getChildren().remove(i);
                }
                 */
                vbox.getChildren().clear();
                loadFXML();
            }
        };
/*
        try {
            for (Type type : typeList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("View/editType.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                EditTypeController editTypeController = fxmlLoader.getController();
                editTypeController.setData(type, editListener);

                vbox.getChildren().add(anchorPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

 */
        loadFXML();
    }

    private void loadFXML() {
        try {
            for (Type type : typeList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("View/editType.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                EditTypeController editTypeController = fxmlLoader.getController();
                editTypeController.setData(type, editListener);

                vbox.getChildren().add(anchorPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
