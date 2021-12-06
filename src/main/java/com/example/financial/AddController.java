package com.example.financial;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class AddController {
    private AddListener addListener;

    @FXML
    public void click(MouseEvent mouseEvent) {
        addListener.clickAdd();
    }

    public void setAddListener(AddListener addListener) {
        this.addListener = addListener;
    }
}
