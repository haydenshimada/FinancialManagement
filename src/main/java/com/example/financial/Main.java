package com.example.financial;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("View/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Financial Management");
        stage.getIcons().add(new Image("file:src/main/resources/com/example/financial/Icon/app.jpg"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}