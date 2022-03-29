package dk.easv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

public class ImageViewerWindowController{
    private final List<Image> images = new ArrayList<>();
    private int currentImageIndex = 0;
    private boolean isStopped = true;
    @FXML
    private TextField displayTime;
    @FXML
    Parent root;

    @FXML
    private ImageView imageView;

    ScheduledExecutorService executorService;

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
            if (isStopped){
                executorService = Executors.newScheduledThreadPool(1);
                Runnable slide = this::handleBtnNextAction;
                calculateTime();
                executorService.scheduleAtFixedRate(slide, calculateTime(), calculateTime(), TimeUnit.SECONDS);
                isStopped = false;
                System.out.println("start");
            }
        }

    public Long calculateTime(){
        return Long.parseLong(displayTime.getText());
    }

    public void handleStopSlideShow(ActionEvent actionEvent) throws InterruptedException {
            if (!isStopped){
                executorService.shutdown();
                isStopped = true;
            }
        }

}