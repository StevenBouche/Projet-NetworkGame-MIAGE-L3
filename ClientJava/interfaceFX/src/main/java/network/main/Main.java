package network.main;

import coco.controller.PlayerData;
import controllerJavafx.ControllerRoot;
import controllerJavafx.LoaderRessource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.client.ClientTCP;

import java.io.IOException;
import java.util.List;

public final class Main {

    private Main()
    {

    }

    public static void main(final String[] args)
    {
        Application.launch(ApplicationWheel.class, args);
    }

}


