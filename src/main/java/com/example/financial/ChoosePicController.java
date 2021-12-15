package com.example.financial;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ChoosePicController {
    @FXML
    private Button chooseImg;

    @FXML
    private ImageView img;

    public void click(MouseEvent mouseEvent) {
        listener.onClick(pic);
    }

    private Picture pic;
    private ChoosePicListener listener;

    public void setPic(Picture pic, ChoosePicListener listener) throws FileNotFoundException {
        this.pic = pic;
        this.listener = listener;

        FileInputStream input = new FileInputStream(pic.getImgSrc());
        img.setImage(new Image(input));
    }
}
