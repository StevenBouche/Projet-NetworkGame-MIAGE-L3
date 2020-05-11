package controllerJavafx;

import javafx.scene.image.Image;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class LoaderRessource {


    private static LoaderRessource instance =null;

    public static LoaderRessource getInstance(){
        if(instance == null )instance = new LoaderRessource();
        return instance;
    }

    public Image wheelBackground;
    public String document;

    private LoaderRessource(){
        loadWheelImage();

    }

    public void setDocumentBase(String documentBase){
        document = documentBase;
    }

    private void loadWheelImage() {
        Resource resource = new ClassPathResource("wheel.jpg");
        InputStream input = null;
        try {
            input = resource.getInputStream();
            wheelBackground = new Image(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
