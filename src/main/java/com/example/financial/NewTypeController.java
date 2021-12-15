package com.example.financial;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

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
        res.add(new Picture("date.png"));
        res.add(new Picture("baby.png"));
        res.add(new Picture("dog.png"));
        res.add(new Picture("diamond.png"));
        res.add(new Picture("bag.png"));
        res.add(new Picture("ball.png"));
        res.add(new Picture("movie.png"));
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
        FileInputStream input = new FileInputStream(pic.getImgSrc());
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

    private ChoosePicListener choosePicListener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

                if (column == 6) {
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
