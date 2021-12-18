package com.example.financial;

import com.example.financial.SQL.SQL;
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
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FinancialController implements Initializable {
    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label dateLabel;
    private LocalDate date;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

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

    private SQL sql;
    public static String saveType;
    public static LocalDate saveDate;

    /*
    private void getData() {
        // default list
        typeList.add(new Type("House", 0, "house.png", "black"));
        typeList.add(new Type("Shopping", 0, "shopping.png", "orange"));
        typeList.add(new Type("Food", 0, "food.png", "red"));
        typeList.add(new Type("Water", 0, "water.png", "blue"));
        typeList.add(new Type("Electric", 0, "electric.png", "yellow"));
        typeList.add(new Type("Transport", 0, "travel.png", "green"));
        typeList.add(new Type("Internet", 0, "internet.png", "cyan"));
        typeList.add(new Type("Health", 0, "health.png", "pink"));

    }

     */

    private void setChosenType(Type type) throws IOException { // call calculator
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

        saveType = type.getType();
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

    private void switchToAddScene() throws IOException {
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
    public void switchToEditScene() throws IOException {
        Parent newScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("View/edit.fxml")));
        Scene scene = dateLabel.getScene();

        newScene.translateXProperty().set(-scene.getWidth());
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
    public void switchToHistoryScene() throws IOException {
        Parent newScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("View/history.fxml")));
        Scene scene = dateLabel.getScene();

        newScene.translateXProperty().set(-scene.getWidth());
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
        date = date.withDayOfMonth(date.lengthOfMonth());

        dateLabel.setText(date.withDayOfMonth(date.lengthOfMonth()).format(dateTimeFormatter));
        saveDate = date;

        setSceneData(date);
    }

    @FXML
    public void getNextDate() {
        date = date.plusMonths(1);
        date = date.withDayOfMonth(date.lengthOfMonth());

        dateLabel.setText(date.format(dateTimeFormatter));
        saveDate = date;

        setSceneData(date);
    }

    @FXML
    public void getNowDate() {
        date = LocalDate.now();
        date = date.withDayOfMonth(date.lengthOfMonth());

        dateLabel.setText(date.format(dateTimeFormatter));
        saveDate = date;

        setSceneData(date);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            sql = new SQL();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        slider.setVisible(false);
        blackPane.setVisible(false);

        // set default date to real time
        date = LocalDate.now();
        date = date.withDayOfMonth(date.lengthOfMonth());
        dateLabel.setText(date.format(dateTimeFormatter));
        saveDate = date;

        /*
        // pie chart's data
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                /*
                new PieChart.Data("House", 0),
                new PieChart.Data("Shopping", 0),
                new PieChart.Data("Food", 0),
                new PieChart.Data("Water", 0),
                new PieChart.Data("Electric", 0),
                new PieChart.Data("Transport", 0),
                new PieChart.Data("Internet", 0),
                new PieChart.Data("Health", 0)

        );


        // get categories
        try {
            typeList.addAll(sql.getList(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Type i : typeList) {
            pieData.addAll(new PieChart.Data(i.getType(), i.getMoney()));
        }

        // get pie chart data
        pieChart.getData().addAll(pieData);

        try {
            pieChart.setTitle("Spent amount: \n" + sql.sum(date.getMonthValue(), date.getYear()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // set pie chart color

        if (!typeList.isEmpty()) {
            int i = 0;
            for (PieChart.Data data : pieData) {
                data.getNode().setStyle("-fx-pie-color:" + typeList.get(i++ % typeList.size()).getButtonColor() + ";");
            }
        }

         */

        setSceneData(date);

        /*
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

         */
/*
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
        */
    }

    @FXML
    public void reloadData() {
        setSceneData(date);
    }

    @FXML
    private void setSceneData(LocalDate localDate) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        // get categories
        try {
            typeList.clear();
            typeList.addAll(sql.getList(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        pieData.clear();
        for (Type i : typeList) {
            pieData.addAll(new PieChart.Data(i.getType(), i.getMoney()));
        }

        // get pie chart data
        pieChart.getData().clear();
        pieChart.getData().addAll(pieData);

        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        try {
            pieChart.setTitle("Spent amount: \n" + currencyVN.format(sql.sum(localDate.getMonthValue(), localDate.getYear())));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // set pie chart color

        if (!typeList.isEmpty()) {
            int i = 0;
            for (PieChart.Data data : pieData) {
                data.getNode().setStyle("-fx-pie-color:" + typeList.get(i++ % typeList.size()).getButtonColor() + ";");
            }
        }

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
            grid.getChildren().clear();
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
                GridPane.setMargin(anchorPane, new Insets(3, 15, 0, 8)); // top - right - bottom - left
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
            GridPane.setMargin(anchorPane, new Insets(3, 15, 0, 8)); // top - right - bottom - left
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}