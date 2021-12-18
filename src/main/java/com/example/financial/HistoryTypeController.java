package com.example.financial;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;

public class HistoryTypeController {
    @FXML
    private Button button;

    @FXML
    private Label date;

    @FXML
    private ImageView image;

    @FXML
    private Label money;

    @FXML
    private Label typeLabel;

    public void setData(History history) throws FileNotFoundException {
        date.setText(history.getDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));
        money.setText(history.getType().getMoney() + Main.CURRENCY);
        typeLabel.setText(history.getType().getType());

        button.setStyle("-fx-background-color: " + history.getType().getButtonColor() + ";");

        FileInputStream input = new FileInputStream(history.getType().getImageSource());
        image.setImage(new Image(input));
    }
}
