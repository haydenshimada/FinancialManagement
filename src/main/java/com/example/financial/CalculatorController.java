package com.example.financial;

import com.example.financial.SQL.SQL;
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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {
    @FXML
    private Label resultLabel;

    @FXML
    private Pane btnEqual;

    @FXML
    private Pane btnTick;

    private SQL sql;

    private int initial = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnEqual.setVisible(false);
        btnTick.setVisible(true);

        try {
            sql = new SQL();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void numberClick(MouseEvent mouseEvent) {
        int value = Integer.parseInt(((Pane) mouseEvent.getSource()).getId().replace("btn", ""));

        if (resultLabel.getText().equals("Divide by 0 error") || resultLabel.getText().equals("Saved successfully")) {
            resultLabel.setText("");
        }

        resultLabel.setText(resultLabel.getText().equals("")
                ? String.valueOf(value)
                : String.valueOf(Integer.parseInt(resultLabel.getText()) * 10 + value)
        );
    }

    private int num1 = 0;
    private String operation = "+";

    @FXML
    public void symbolClick(MouseEvent mouseEvent) {
        String symbol = ((Pane) mouseEvent.getSource()).getId().replace("btn", "");
        if (symbol.equals("Equal")) {
            int num2 = Integer.parseInt(resultLabel.getText());
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
        } else if (symbol.equals("Tick")) {
            try {
                sql.updateType(FinancialController.saveType,
                        resultLabel.getText().equals("") ? initial : (Integer.parseInt(resultLabel.getText())) + initial);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            resultLabel.setText("Saved successfully");
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

                num1 = Integer.parseInt(resultLabel.getText());
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
    private String imgeSrc = "";

    @FXML
    private Label typeLabel;

    public void setData(Type type) throws FileNotFoundException {
        initial = type.getMoney();

        typeLabel.setText(type.getType());
        typeCircle.setStyle("-fx-background-color: " + type.getButtonColor() + ";"
                + "-fx-background-radius: 100");
        typeColor.setStyle("-fx-background-color: " + type.getButtonColor() + ";");

        imgeSrc = type.getAbsoluteImageSource();
        FileInputStream input = new FileInputStream(imgeSrc);
        typeImg.setImage(new Image(input));
    }
}
