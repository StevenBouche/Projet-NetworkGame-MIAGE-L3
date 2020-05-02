package network.main;

import controllerJavafx.ControllerRoot;
import controllerJavafx.ControllerScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.server.ServerUDP;
import network.share.Choice;
import network.share.DataListener;
import network.share.IPEndPoint;
import network.udp.ProtocolEventsUDP;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

public class Main extends Application {

    ControllerRoot managerRoot;
    Parent root;

    Thread threadServer;
    ServerUDP server;

    @Override
    public void start(Stage primaryStage) throws Exception{
        loadPrimaryScene();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.resizableProperty().setValue(false);
        primaryStage.show();
        loadServerUDP();

        managerRoot.setState("Running UDP on : 127.0.0.1:11000");
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        threadServer.interrupt();
    }

    private void loadServerUDP() throws SocketException, UnknownHostException {
        server = new ServerUDP();
       server.OnEvent(Choice.class, ProtocolEventsUDP.SUBSCRIPTION, new DataListener<Choice>() {
            @Override
            public void onData(IPEndPoint var1, Choice var2) {
                managerRoot.appendText(var1.toString()+" : "+var2.message);
                System.out.println("ON EVENT "+var2.ToString());
            }
        });
        threadServer = new Thread(server);
        threadServer.start();
    }

    private void loadPrimaryScene() throws IOException {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("sample.fxml"));
            managerRoot = new ControllerRoot();
            fxmlLoader.setController(managerRoot);
            root = fxmlLoader.load();

    //        manager  = fxmlLoader.getController();




    }

    public static void main(String[] args) {
        launch(args);
    }
}
