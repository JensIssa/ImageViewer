package dk.easv;

import javafx.concurrent.Task;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

public class DisplayTask extends Task<Image> {



    private List<Image> animalList;

    private int sleepDuration;
    private boolean isRunning = true;
    private int currentImageIndex = 0;
    private Map<String, Integer> count;
    private int waitTime;


    public DisplayTask(List<Image> images, int waitTime) {
        this.animalList = images;
        this.waitTime = waitTime;
    }


    @Override
    protected Image call() throws Exception {
        while (isRunning) {
            Thread.sleep(sleepDuration * 1000L);
            getNextImage();
        }
        return null;
    }

    private void getNextImage() {
        if (!animalList.isEmpty()) {
            currentImageIndex = (currentImageIndex + 1) % animalList.size();
            Image image = animalList.get(currentImageIndex);
            updateValue(image);
            updateMessage(new File(image.getUrl()).getName());
        }
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
}

