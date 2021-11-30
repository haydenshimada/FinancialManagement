package com.example.financial;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TypeController {
    @FXML
    private Button typeButton;

    @FXML
    private ImageView typeImage;

    @FXML
    private Label typeMoney;

    @FXML
    private Label typeName;

    @FXML
    public void click(MouseEvent mouseEvent) {
        listener.onClicked(type);
    }

    private Type type;
    private ChooseListener listener;

    public void setData(Type type, ChooseListener listener) throws FileNotFoundException {
        this.type = type;
        this.listener = listener;

        typeName.setText(type.getType());
        typeMoney.setText(type.getMoney() + Main.CURRENCY);
        typeButton.setStyle("-fx-background-color:" + type.getButtonColor() + ";");

        FileInputStream input = new FileInputStream(type.getImageSource());
        typeImage.setImage(new Image(input));
    }
}
