package dk.easv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import javax.swing.*;

public class ImageViewerWindowController extends Thread {
    private final List<Image> images = new ArrayList<>();
    private int currentImageIndex = 0;
    private boolean isStopped = false;
    @FXML
    private TextField displayTime;

    private int threadTime;
    @FXML
    Parent root;

    @FXML
    private ImageView imageView;


    public ImageViewerWindowController() {
    }


    @FXML
    private void handleBtnLoadAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Images",
                "*.png", "*.jpg", "*.gif", "*.tif", "*.bmp"));
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());

        if (!files.isEmpty()) {
            files.forEach((File f) ->
            {
                images.add(new Image(f.toURI().toString()));
            });
            displayImage();
        }
    }

    @FXML
    private void handleBtnPreviousAction() {
        if (!images.isEmpty()) {
            currentImageIndex =
                    (currentImageIndex - 1 + images.size()) % images.size();
            displayImage();
        }
    }

    @FXML
    private void handleBtnNextAction() {
        if (!images.isEmpty()) {
            currentImageIndex = (currentImageIndex + 1) % images.size();
            displayImage();
        }
    }

    private void displayImage() {
        if (!images.isEmpty()) {
            imageView.setImage(images.get(currentImageIndex));
        }
    }

    public void handleStartSlideShow(ActionEvent actionEvent) throws InterruptedException {
            start();
            isStopped = false;

        }

    //FIX ME ADD POP TO DECLARE SLEEP TIME, BETWEEN 1000-5000
    public void handleStopSlideShow(ActionEvent actionEvent) throws InterruptedException {
            isStopped = true;
            stop();
        }


    @Override
    public void run() {

            while (!images.isEmpty() && !isStopped) {
                handleBtnNextAction();
                try {
                    long display = Long.parseLong(displayTime.getText());
                    Thread.sleep(display);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
}