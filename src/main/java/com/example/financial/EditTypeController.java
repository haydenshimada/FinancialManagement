package com.example.financial;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EditTypeController {
    @FXML
    private Button button;

    @FXML
    private ImageView image;

    @FXML
    private Label label;

    @FXML
    public void deleteClick(MouseEvent mouseEvent) {
        listener.deleteClick(type);
    }

    private Type type;
    private EditListener listener;

    public void setData(Type type, EditListener listener) throws FileNotFoundException {
        this.type = type;
        this.listener = listener;

        label.setText(type.getType());
        button.setStyle("-fx-background-color: " + type.getButtonColor() + ";");

        FileInputStream input = new FileInputStream(type.getAbsoluteImageSource());
        image.setImage(new Image(input));
    }
}
