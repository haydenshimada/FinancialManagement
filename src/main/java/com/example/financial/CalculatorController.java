package com.example.financial;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {
    @FXML
    private Label resultLabel;

    @FXML
    private Pane btnEqual;

    @FXML
    private Pane btnTick;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnEqual.setVisible(false);
        btnTick.setVisible(true);
    }

    @FXML
    public void numberClick(MouseEvent mouseEvent) {
        int value = Integer.parseInt(((Pane) mouseEvent.getSource()).getId().replace("btn", ""));

        if (resultLabel.getText().equals("Divide by 0 error")) {
            resultLabel.setText("");
        }

        resultLabel.setText(resultLabel.getText().equals("")
                ? String.valueOf((double) value)
                : String.valueOf(Double.parseDouble(resultLabel.getText()) * 10 + value)
        );
    }

    private double num1 = 0.0;
    private String operation = "+";

    @FXML
    public void symbolClick(MouseEvent mouseEvent) {
        String symbol = ((Pane) mouseEvent.getSource()).getId().replace("btn", "");
        if (symbol.equals("Equal")) {
            double num2 = Double.parseDouble(resultLabel.getText());
            switch (operation) {
                case "+" -> resultLabel.setText((num1 + num2) + "");
                case "-" -> resultLabel.setText((num1 - num2) + "");
                case "*" -> resultLabel.setText((num1 * num2) + "");
                case "/" -> resultLabel.setText((num1 / num2) + "");
            }
            if (resultLabel.getText().equals("Infinity")) {
                resultLabel.setText("Divide by 0 error");
            }
            operation = ".";

            btnTick.setVisible(true);
            btnEqual.setVisible(false);

        } else if (symbol.equals("Clear")) {    // AC
            resultLabel.setText("");
            operation = ".";
        } else {    // operation
            if (!resultLabel.getText().equals("")) {
                btnEqual.setVisible(true);
                btnTick.setVisible(false);

                switch (symbol) {
                    case "Plus" -> operation = "+";
                    case "Minus" -> operation = "-";
                    case "Mul" -> operation = "*";
                    case "Div" -> operation = "/";
                }

                num1 = Double.parseDouble(resultLabel.getText());
                resultLabel.setText("");
            }
        }
    }

    @FXML
    private Button typeCircle;

    @FXML
    private Pane typeColor;

    @FXML
    private ImageView typeImg;

    @FXML
    private Label typeLabel;

    public void setData(Type type) throws FileNotFoundException {
        typeLabel.setText(type.getType());
        typeCircle.setStyle("-fx-background-color: " + type.getButtonColor() + ";"
                            + "-fx-background-radius: 100");
        typeColor.setStyle("-fx-background-color: " + type.getButtonColor() + ";");

        FileInputStream input = new FileInputStream(type.getImageSource());
        typeImg.setImage(new Image(input));
    }

}
