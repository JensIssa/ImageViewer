package dk.easv;

import javafx.fxml.FXML;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextField;

public class ThreadStart implements Runnable {

    private final List<Image> listOfSlides;
    private int currentIndex;
    private boolean isStopped = true;
    private long  displayTime;

    public ThreadStart(long displayTime, int currentIndex, boolean isStopped, List<Image> listOfSlides) {
        this.displayTime = displayTime;
        this.currentIndex = currentIndex;
        this.isStopped = isStopped;
        this.listOfSlides = listOfSlides;
    }

    @Override
    public void run() {
        while (!listOfSlides.isEmpty() && !isStopped && displayTime > 0) {
            try {
                Thread.sleep(displayTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
