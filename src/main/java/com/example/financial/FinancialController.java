package com.example.financial;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class FinancialController implements Initializable {
    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label dateLabel;
    private LocalDate date;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    @FXML
    private StackPane stackPane = new StackPane();

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private PieChart pieChart;

    @FXML
    private AnchorPane slider;

    @FXML
    private AnchorPane blackPane;

    private final List<Type> typeList = new ArrayList<>();

    private ChooseListener chooseListener;
    private AddListener addListener;

    private List<Type> getData() {
        List<Type> types = new ArrayList<>();

        // default list
        types.add(new Type("House", 0, "house.png", "black"));
        types.add(new Type("Shopping", 0, "shopping.png", "orange"));
        types.add(new Type("Food", 0, "food.png", "red"));
        types.add(new Type("Water", 0, "water.png", "blue"));
        types.add(new Type("Electric", 0, "electric.png", "yellow"));
        types.add(new Type("Transport", 0, "travel.png", "green"));
        types.add(new Type("Internet", 0, "internet.png", "cyan"));
        types.add(new Type("Health", 0, "health.png", "pink"));

        return types;
    }

    private void setChosenType (Type type) throws IOException { // call calculator
        blackPane.setVisible(true);
        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), blackPane);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.play();

        blackPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");    // transparent black

        slider.setVisible(true);
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.5));

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("View/calculator.fxml"));

        slider.getChildren().add(fxmlLoader.load());

        CalculatorController controller = fxmlLoader.getController();
        controller.setData(type);

        slide.setNode(slider);

        slide.setToY(0);
        slide.play();

        slider.setTranslateY(480); // height of slider
    }

    @FXML
    public void backToMenu(MouseEvent mouseEvent) {
        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), blackPane);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.play();
        fade.setOnFinished(e -> blackPane.setVisible(false));

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.5));

        slide.setNode(slider);

        slide.setToY(480); // height of slider
        slide.play();

        slider.setTranslateY(0);
        slide.setOnFinished(e -> slider.setVisible(false));
    }

    private void switchToAddScene() throws IOException{
        Parent newScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("View/newType.fxml")));
        Scene scene = dateLabel.getScene();

        newScene.translateXProperty().set(scene.getWidth());
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
        dateLabel.setText(date.format(dateTimeFormatter));
    }

    @FXML
    public void getNextDate() {
        date = date.plusMonths(1);
        dateLabel.setText(date.format(dateTimeFormatter));
    }

    @FXML
    public void getNowDate() {
        date = LocalDate.now();
        dateLabel.setText(date.format(dateTimeFormatter));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        slider.setVisible(false);
        blackPane.setVisible(false);

        // set default date to real time
        date = LocalDate.now();
        dateLabel.setText(date.format(dateTimeFormatter));

        // pie chart's data
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("House", 50),
                new PieChart.Data("Shopping", 30),
                new PieChart.Data("Food", 25),
                new PieChart.Data("Water", 10),
                new PieChart.Data("Electric", 43),
                new PieChart.Data("Transport", 86),
                new PieChart.Data("Internet", 90),
                new PieChart.Data("Health", 5)
        );

        // get pie chart data
        pieChart.getData().addAll(pieData);
        pieChart.setTitle("Financial");

        // get categories
        typeList.addAll(getData());

        chooseListener = new ChooseListener() {
            @Override
            public void onClicked(Type type) {
                try {
                    setChosenType(type);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        addListener = new AddListener() {
            @Override
            public void clickAdd() {
                try {
                    switchToAddScene();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        int column = 0;
        int row = 0;

        try {
            for (Type type : typeList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("View/type.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                TypeController typeController = fxmlLoader.getController();
                typeController.setData(type, chooseListener);

                if (row == 2) {
                    row = 0;
                    column++;
                }

                grid.add(anchorPane, column, row++);
                GridPane.setMargin(anchorPane, new Insets(3, 15,0, 8)); // top - right - bottom - left
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // add newType button
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("View/add.fxml"));

            AnchorPane anchorPane = fxmlLoader.load();

            AddController addController = fxmlLoader.getController();
            addController.setAddListener(addListener);

            if (row == 2) {
                row = 0;
                column++;
            }

            grid.add(anchorPane, column, row);
            GridPane.setMargin(anchorPane, new Insets(3, 15,0, 8)); // top - right - bottom - left
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}