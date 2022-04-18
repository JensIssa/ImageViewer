package dk.easv;

import javafx.scene.image.Image;

import java.io.File;


public class Picture {
    private Image image;
    private String fileUrl;

    public Picture(Image image, File file) {
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
}
