module org.miage.interfaceFX {
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires org.miage.network;
    requires com.jfoenix;
    exports controllerJavafx;
    exports network.main;
    exports coco.controller;
    exports coco;
    opens network.main to javafx.graphics;
    opens controllerJavafx to javafx.graphics;
    opens coco.controller to javafx.graphics;
    opens coco to javafx.graphics;
}