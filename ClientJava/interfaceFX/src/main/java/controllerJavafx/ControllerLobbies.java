package controllerJavafx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import network.message.PacketMessage;
import network.message.obj.DataServerGame;
import network.message.obj.ServerGame;
import network.client.ClientUDP;
import network.share.DataListener;
import network.share.IPEndPoint;
import network.share.ListenerState;
import network.udp.ProtocolEventsUDP;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class ControllerLobbies implements Initializable {

    @FXML
    public Rectangle rectRunning;
    @FXML
    public Label labelRunning;
    @FXML
    public TableView<ServerGame> tableView;
    @FXML
    public Button buttonConnect;
    @FXML
    public Button buttonRefresh;
    @FXML
    public TextField name;

    ClientUDP server;
    Thread threadServer;
    private ServerGame srcGame;
    INotifyEventUI observer;
    String nameField;

    public ControllerLobbies(INotifyEventUI observer){
        this.observer = observer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stateDisconnected();
        initTable();
        initButton();
        initNameField();
        try {
            loadServerUDP();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void switchSceneAndConnectToServerGame() {
        stop(); // stop thread curretn scene
        Platform.runLater(() -> {
            try {
            observer.startSceneLobbyGame(this.srcGame, nameField);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void updateTable(DataServerGame data){
        Platform.runLater(() ->  {
            ObservableList<ServerGame> list = FXCollections.observableArrayList(data.listServer);
            tableView.setItems(list);
        });
    }

    private void stateConnected(String str){
        Platform.runLater(() ->  {
            rectRunning.setFill(Color.GREEN);
            labelRunning.setText(str);
            buttonRefresh.setDisable(false);
        });
    }

    private void stateDisconnected(){
        Platform.runLater(() ->  {
            rectRunning.setFill(Color.RED);
            labelRunning.setText("Server UDP not Running");
            buttonRefresh.setDisable(true);
        });
    }

    private void requestServerGame() {
        PacketMessage<DataServerGame> packet = new PacketMessage<>();
        packet.evt = ProtocolEventsUDP.GETLISTSERVERGAME.eventName;
        packet.data = new DataServerGame();;
        try {
            server.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setServerGame() {
        Platform.runLater(() ->  {
            ServerGame srcGame = tableView.getSelectionModel().getSelectedItem();
            int index = tableView.getSelectionModel().getFocusedIndex();

            if(srcGame == null) {
                srcGame = tableView.getItems().get(index);
                tableView.getSelectionModel().select(index);
            }

            if(this.srcGame == null){
                if(srcGame.nbPlayerCurrent < srcGame.nbPlayerMax) this.srcGame = srcGame;
                else tableView.getSelectionModel().clearSelection(index);
            }
            else if(this.srcGame != srcGame)  {
                this.srcGame = srcGame;
            } else {
                tableView.getSelectionModel().clearSelection(index);
                this.srcGame= null;
            }

            System.out.println(this.srcGame);
            buttonConnect.setDisable(this.srcGame == null || nameField == null || nameField.length() < 3 );
        });
    }

    public void stop() {
        server.stop();
        try {
            threadServer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initNameField() {
        name.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                nameField = name.getText();
                buttonConnect.setDisable(nameField.length() <= 3 || srcGame == null);
            }
        });
    }

    private void initButton() {
        buttonRefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                requestServerGame();
                actionEvent.consume();
            }
        });

        buttonConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                switchSceneAndConnectToServerGame();
                actionEvent.consume();
            }
        });
    }

    private void initTable() {
        tableView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                setServerGame();
                keyEvent.consume();
            }
        });
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setServerGame();
                mouseEvent.consume();
            }
        });
        tableView.setEditable(false);
        TableColumn<ServerGame,String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<ServerGame,String> addrColumn = new TableColumn<>("Address");
        addrColumn.setCellValueFactory(new PropertyValueFactory<>("addr"));
        TableColumn<ServerGame,Integer> portColumn = new TableColumn<>("Port");
        portColumn.setCellValueFactory(new PropertyValueFactory<>("port"));
        TableColumn<ServerGame,Integer> currentColumn = new TableColumn<>("Current player");
        currentColumn.setCellValueFactory(new PropertyValueFactory<>("nbPlayerCurrent"));
        TableColumn<ServerGame,Integer> maxColumn = new TableColumn<>("Max player");
        maxColumn.setCellValueFactory(new PropertyValueFactory<>("nbPlayerMax"));
        tableView.getColumns().addAll(nameColumn, addrColumn, portColumn, currentColumn,maxColumn);
    }

    private void loadServerUDP() throws SocketException, UnknownHostException {

        server = new ClientUDP(new ListenerState() {
            @Override
            public void onRunning(String str) {
                stateConnected(str);
                requestServerGame();
            }
            @Override
            public void onShutdown() {
                stateDisconnected();
            }
        });

        server.OnEvent(DataServerGame.class, ProtocolEventsUDP.GETLISTSERVERGAME, new DataListener<DataServerGame>() {
            @Override
            public void onData(IPEndPoint var1, DataServerGame var2) {

                System.out.println("ON EVENT "+var2.listServer.toString());
                updateTable(var2);
            }
        });
        threadServer = new Thread(server);
        threadServer.start();

    }

}
