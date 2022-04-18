package dk.easv;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import javax.swing.*;

public class ImageViewerWindowController{
    private final List<Picture> images = new ArrayList<>();
    @FXML
    private Label colorLabel;
    @FXML
    private Label imageName;
    @FXML
    private int currentImageIndex = 0;
    private boolean isStopped = true;
    @FXML
    private TextField displayTime;
    @FXML
    Parent root;

    @FXML
    private ImageView imageView;

    private ObservableList<String> names = FXCollections.observableArrayList();
    private DisplayTask task;

    ScheduledExecutorService executorService;
    ExecutorService service = Executors.newFixedThreadPool(1);


    private int redCounter;
    private int greenCounter;
    private int blueCounter;
    private int mixedCounter;
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
                Image image = (new Image(f.toURI().toString()));
                try {
                    images.add(new Picture(image, f));
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                image.getPixelReader();
            });
            displayImage(images.get(0).getImage());
            imageName.setText(images.get(0).getFileUrl());
        }
    }

    @FXML
    private void handleBtnPreviousAction() {
        if (!images.isEmpty()) {
            currentImageIndex = (currentImageIndex - 1 + images.size()) % images.size();
            displayImage(images.get(currentImageIndex).getImage());
        }
    }

    @FXML
    private void handleBtnNextAction() {
        if (!images.isEmpty()) {
            currentImageIndex = (currentImageIndex + 1) % images.size();
            displayImage(images.get(currentImageIndex).getImage());
        }
    }

    private void displayImage(Image image) {
        if (!images.isEmpty())
        {
            imageView.setImage(image);
        }

    }

    public void setDisplayTime(){
        executorService = Executors.newScheduledThreadPool(1);
        Runnable slide = this::handleBtnNextAction;
        calculateTime();
        executorService.scheduleAtFixedRate(slide, calculateTime(), calculateTime(), TimeUnit.SECONDS);
    }


    public void handleStartSlideShow(ActionEvent actionEvent) throws InterruptedException {
            if (isStopped){
                setDisplayTime();
                isStopped = false;
                DisplayTask displayTask = new DisplayTask(images, calculateTime());
                displayTask.valueProperty().addListener((obs, o, n)->{
                    displayImage(n.getImage());
                    imageName.setText(images.get(displayTask.getCurrentImageIndex()).getFileUrl());
                    displayTask.setCount(colorLabel);
                });
                service.submit(displayTask);
                System.out.println("start");
                System.out.println(redCounter + greenCounter + blueCounter + mixedCounter);
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