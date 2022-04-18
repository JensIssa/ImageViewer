package dk.easv;

import javafx.concurrent.Task;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

public class DisplayTask extends Task<Picture> {


    private List<Picture> pictures;
    private boolean isRunning = true;
    private int currentImageIndex = 0;
    private int waitTime;


    public DisplayTask(List<Picture> images, int waitTime) {
        this.pictures = images;
        this.waitTime = waitTime;
    }


    @Override
    protected Picture call() throws Exception {
        while (true){
            getNextImage();
            Thread.sleep(waitTime * 1000L);
        }
    }

    private void getNextImage() {
        if (!pictures.isEmpty()) {
            currentImageIndex = (currentImageIndex + 1) % pictures.size();
        }
        Picture image = pictures.get(currentImageIndex);
        updateValue(image);
    }


    public int getCurrentImageIndex() {
        return currentImageIndex;
    }

}



