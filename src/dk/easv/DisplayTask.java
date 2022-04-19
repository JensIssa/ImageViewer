package dk.easv;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

public class DisplayTask extends Task<Picture> {


    private List<Picture> pictures;
    private boolean isRunning = true;
    private int currentImageIndex = 0;
    private long waitTime;
    private int red;
    private int green;
    private int blue;
    private int mixed;
    private javafx.scene.control.Label count;
    private boolean running = true;


    public DisplayTask(List<Picture> images, long waitTime) {
        this.pictures = images;
        this.waitTime = waitTime;
    }

    public DisplayTask(List<Picture> images){
        this.pictures = images;
    }

    public javafx.scene.control.Label getCount() {
        return count;
    }

    public void setCount(Label count) {
        this.count = count;
    }




    public void setRunning(boolean running) {
        this.running = running;
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
        getPixelInfo();
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


    private void getPixelInfo(){
        Picture img = pictures.get(currentImageIndex);
        PixelReader pReader = img.getImage().getPixelReader();
        red = 0;
        green = 0;
        blue = 0;
        mixed = 0;

        for (int readY = 0; readY < img.getImage().getHeight(); readY++){
            for (int readX = 0; readX < img.getImage().getWidth(); readX++){
                Color color = pReader.getColor(readX,readY);
                if(color.getBlue() > color.getGreen() && color.getBlue() > color.getRed()){
                    blue++;
                }else if(color.getGreen() > color.getBlue() && color.getGreen() > color.getRed()){
                    green++;
                }else if(color.getRed() > color.getBlue() && color.getRed() > color.getGreen()){
                    red++;
                }
                else if(blue == red || blue == green || red == green) {
                    mixed++;
                }
            }
        }
        Platform.runLater(() ->{
            count.setText("Red: " +  red + "\nGreen: " + green + "\nBlue: " + blue + "\nMixed: " + mixed);
        });
    }



}



