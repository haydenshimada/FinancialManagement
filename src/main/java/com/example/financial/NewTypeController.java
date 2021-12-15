package com.example.financial;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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

    private final List<Picture> ImageList = new ArrayList<>();
    private List<Picture> getImageSrc() {
        List<Picture> res = new ArrayList<>();

        res.add(new Picture("bank.png"));
        res.add(new Picture("dollar.png"));
        res.add(new Picture("yen.png"));
        res.add(new Picture("c.png"));
        res.add(new Picture("p.png"));

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

    private ChoosePicListener choosePicListener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        grid = new GridPane();
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
