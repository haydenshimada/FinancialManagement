package com.example.financial;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {
    @FXML
    private Label resultLabel;

    @FXML
    private Label equalLabel;

    @FXML
    private ImageView tickImg;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        equalLabel.setVisible(false);
    }

    @FXML
    public void numberClick(MouseEvent mouseEvent) {
        int value = Integer.parseInt(((Pane)mouseEvent.getSource()).getId().replace("btn", ""));

        if (resultLabel.getText().equals("Divide by 0 error")) {
            resultLabel.setText(String.valueOf(0.0));
        }

        resultLabel.setText(Double.parseDouble(
                resultLabel.getText()) == 0
                        ? String.valueOf((double) value)
                        : String.valueOf(Double.parseDouble(resultLabel.getText()) * 10 + value)
        );
    }

    private double num1 = 0.0;
    private String operation = "+";

    @FXML
    public void symbolClick(MouseEvent mouseEvent) {
        String symbol = ((Pane)mouseEvent.getSource()).getId().replace("btn", "");
        if (symbol.equals("Equal")) {
            if (equalLabel.isVisible()) {
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

                equalLabel.setVisible(false);
                tickImg.setVisible(true);
            }
        } else if (symbol.equals("Clear")) {    // AC
            resultLabel.setText(String.valueOf(0.0));
            operation = ".";
        }
        else {    // operation
            tickImg.setVisible(false);
            equalLabel.setVisible(true);

            switch (symbol) {
                case "Plus" -> operation = "+";
                case "Minus" -> operation = "-";
                case "Mul" -> operation = "*";
                case "Div" -> operation = "/";
            }
            num1 = Double.parseDouble(resultLabel.getText());
            resultLabel.setText(String.valueOf(0.0));
        }
    }
}
