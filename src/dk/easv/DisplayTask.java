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
    private long waitTime;


    public DisplayTask(List<Picture> images, long waitTime) {
        this.pictures = images;
        this.waitTime = waitTime;
    }

    /**
     * The call method is invoked when the task is executed, the method must be overwritten and called by a subclass.
     * The call method performs the background thread logic.
     * @return
     * @throws Exception
     */

    @Override
    protected Picture call() throws Exception {
        while (true){
            getNextImage();
            Thread.sleep(waitTime * 1000L);
        }
    }

    /**
     * This method gets the next image in our slideshow
     */

    private void getNextImage() {
        if (!pictures.isEmpty()) {
            currentImageIndex = (currentImageIndex + 1) % pictures.size();
        }
        Picture image = pictures.get(currentImageIndex);
        updateValue(image);
    }


    /**
     * Returns the currentImageIndex
     * @return
     */

    public int getCurrentImageIndex() {
        return currentImageIndex;
    }

}



