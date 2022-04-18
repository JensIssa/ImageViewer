package dk.easv;

import javafx.scene.image.Image;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class RGBPixelCounter implements Callable {

    private Picture localPicture;
    private Image localImage;
    private int redCounter;
    private int greenCounter;
    private int blueCounter;
    private int mixedCounter;

    public RGBPixelCounter(Picture picture) {
        localPicture = picture;
        localImage = localPicture.getImage();
    }

    @Override
    public ArrayList<Integer> call() throws Exception {
        ArrayList<Integer> redGreenBlueList = new ArrayList<>();
        int width = (int) localImage.getWidth();
        int height = (int) localImage.getHeight();
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(localImage.getPixelReader().getArgb(y, x));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                if (red > green && red > blue) {
                    redCounter++;
                } else if(green > red && green > blue){
                    greenCounter++;
                } else if(blue > red && blue > green) {
                    blueCounter++;
                } else {
                    mixedCounter++;
                }
            }
        }

        redGreenBlueList.add(redCounter);
        redGreenBlueList.add(greenCounter);
        redGreenBlueList.add(blueCounter);
        redGreenBlueList.add(mixedCounter);
        return redGreenBlueList;
    }
}
