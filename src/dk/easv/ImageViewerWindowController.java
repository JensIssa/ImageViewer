package dk.easv;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
<<<<<<< Updated upstream
=======
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

>>>>>>> Stashed changes
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
<<<<<<< Updated upstream
import javafx.scene.control.TextField;
=======
import javafx.scene.control.*;
>>>>>>> Stashed changes
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class ImageViewerWindowController implements Initializable {


<<<<<<< Updated upstream
public class ImageViewerWindowController extends Thread {
    private final List<Image> images = new ArrayList<>();
=======
    private final List<Image> images = new ArrayList<>();


    public Slider durationSlider;
    public Button btnPrevious;
    public Button btnLoad;
    public Button btnNext;
    public Button stopBtn;
    @FXML
    private TextArea displayArea;
>>>>>>> Stashed changes
    private int currentImageIndex = 0;
    private boolean isStopped = false;
    @FXML
    private TextField displayTime;

    private int threadTime;
    @FXML
    Parent root;
    @FXML
    private ImageView imageView;

<<<<<<< Updated upstream
=======
    public DisplayTask displayTask;

    public int waitTime = 2;

    ScheduledExecutorService executorService;
>>>>>>> Stashed changes

    public ImageViewerWindowController() {
    }

<<<<<<< Updated upstream
=======

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stopBtn.setDisable(true);

        durationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            displayTime.setText(newValue.intValue() + "");
            waitTime = newValue.intValue();
            if (displayTask != null && displayTask.isRunning())
                displayTask.setWaitTime(newValue.intValue());
        });
        durationSlider.setValue(3);
    }

>>>>>>> Stashed changes


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
<<<<<<< Updated upstream
            start();
            isStopped = false;

        }
=======
        if (isStopped) {

            displayTask = new DisplayTask(images, waitTime);
            executorService = Executors.newScheduledThreadPool(1);

            displayTask.valueProperty().addListener(((observable, oldValue, newValue) -> {
                imageView.setImage(newValue);
            });

            displayArea.textProperty().bind(displayTask.messageProperty());

            displayTask.setOnSucceeded(event -> {
                displayImage();
            });

            Runnable slide = this::handleBtnNextAction;
            calculateTime();
            executorService.scheduleAtFixedRate(slide, calculateTime(), calculateTime(), TimeUnit.SECONDS);
            isStopped = false;
            System.out.println("start");
        }
    }

    public Long calculateTime() {
        return Long.parseLong(displayTime.getText());
    }
>>>>>>> Stashed changes

    //FIX ME ADD POP TO DECLARE SLEEP TIME, BETWEEN 1000-5000
    public void handleStopSlideShow(ActionEvent actionEvent) throws InterruptedException {
<<<<<<< Updated upstream
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
=======
        if (!isStopped) {
            executorService.shutdown();
            isStopped = true;
        }
>>>>>>> Stashed changes
    }
}