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
    public Media wheelFortuneMp3;
    public Media soundBetter;

    private LoaderRessource(){
        loadWheelImage();
        loadMp3();
    }

    private void loadMp3() {
        String str = getClass().getClassLoader().getResource("wheel.mp3").getPath();
        String str2 = getClass().getClassLoader().getResource("Better.mp3").getPath();
        wheelFortuneMp3 = new Media(new File(str).toURI().toString());
        soundBetter = new Media(new File(str2).toURI().toString());
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
