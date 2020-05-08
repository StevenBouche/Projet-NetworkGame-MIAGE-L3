package coco.controller;

import network.client.ClientTCP;

import java.util.List;

public class DataLoadGame {

    public String myId;
    public ClientTCP client;
    public Thread clientThread;
    public List<PlayerData> listPlayerData;

}