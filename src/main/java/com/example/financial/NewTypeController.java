package com.example.financial;

import com.example.financial.SQL.SQL;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class NewTypeController implements Initializable {
    @FXML
    private Button previewButton;

    @FXML
    private ImageView previewImg;

    @FXML
    private ImageView backToMainBtn;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane grid ;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField typeName;

    @FXML
    private Label addSuccess;

    private Picture picture;

    private final List<Picture> ImageList = new ArrayList<>();
    private List<Picture> getImageSrc() {
        List<Picture> res = new ArrayList<>();

        res.add(new Picture("bank.png"));
        res.add(new Picture("dollar.png"));
        res.add(new Picture("yen.png"));
        res.add(new Picture("c.png"));
        res.add(new Picture("p.png"));
        res.add(new Picture("mo.png"));
        res.add(new Picture("currency.png"));
        res.add(new Picture("bitcoin.png"));
        res.add(new Picture("house.png"));
        res.add(new Picture("shopping.png"));
        res.add(new Picture("food.png"));
        res.add(new Picture("water.png"));
        res.add(new Picture("internet.png"));
        res.add(new Picture("baby.png"));
        res.add(new Picture("dog.png"));
        res.add(new Picture("travel.png"));
        res.add(new Picture("diamond.png"));
        res.add(new Picture("bag.png"));
        res.add(new Picture("ball.png"));
        res.add(new Picture("movie.png"));
        res.add(new Picture("health.png"));
        res.add(new Picture("bandage.png"));
        res.add(new Picture("syringe.png"));
        res.add(new Picture("tooth.png"));
        res.add(new Picture("salon.png"));
        res.add(new Picture("clef.png"));
        res.add(new Picture("guitar.png"));
        res.add(new Picture("violin.png"));

        return res;
    }

    @FXML
    void backToMain(MouseEvent mouseEvent) throws IOException {
        Parent newScene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("View/hello-view.fxml")));
        Scene scene = backToMainBtn.getScene();

        newScene.translateXProperty().set(scene.getWidth() * -1);

        StackPane parentContainer = (StackPane) scene.getRoot();
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

    private void setPreviewImg(Picture pic) throws FileNotFoundException {
        picture = pic;
        FileInputStream input = new FileInputStream(pic.getAbsoluteImgSrc());
        previewImg.setImage(new Image(input));
    }

    @FXML
    public void setPreviewColor(ActionEvent actionEvent) {
        Color myColor = colorPicker.getValue();
        previewButton.setStyle(
                "-fx-background-color: " + toRGBCode(myColor) + ";"
                + "-fx-background-radius: 100" + ";"
        );

    }

    private String toRGBCode(Color color)
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    @FXML
    public void tickClick() {
        try {
            SQL sql = new SQL();
            LocalDate date = FinancialController.saveDate;
            sql.addType(typeName.getText(), 0,
                    picture.getImgSrc(),
                    toRGBCode(colorPicker.getValue()),
                    date.getYear(), date.getMonthValue(), date.getDayOfMonth());
            addSuccess.setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ChoosePicListener choosePicListener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addSuccess.setVisible(false);

        ImageList.addAll(getImageSrc());

        choosePicListener = new ChoosePicListener() {
            @Override
            public void onClick(Picture pic) {
                try {
                    setPreviewImg(pic);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        };

        try {
            int column = 0;
            int row = 1;

            for (Picture i : ImageList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("View/choosePic.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                ChoosePicController controller = fxmlLoader.getController();
                controller.setPic(i, choosePicListener);

                if (column == 5) {
                    row++;
                    column = 0;
                }

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(5, 13,15, 14.5)); // top - right - bottom - left
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
