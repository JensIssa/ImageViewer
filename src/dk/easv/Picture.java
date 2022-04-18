package dk.easv;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Picture {
    private Image image;
    private String fileUrl;
    private ExecutorService executorService;
    private List<Integer> rgbColorList;

    public Picture(Image image, File file) throws ExecutionException, InterruptedException {
        this.image = image;
        fileUrl = file.getName();

    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void rgbThread() throws ExecutionException, InterruptedException {
        RGBPixelCounter redGreenBluePixelCounter = new RGBPixelCounter(this);
        Future<ArrayList<Integer>> future = executorService.submit(redGreenBluePixelCounter);
        rgbColorList = future.get();
    }



    public List<Integer> getRgbColorList() {
        return rgbColorList;
    }
}
