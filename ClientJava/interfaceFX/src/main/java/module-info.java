module org.miage.interfaceFX {
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires org.miage.network;
    requires com.jfoenix;
    requires spring.core;
    exports controllerJavafx;
    exports network.main;
    exports coco.controller;
    exports coco.state;
    exports coco;
    opens network.main to javafx.graphics;
    opens controllerJavafx to javafx.graphics;
    opens coco.controller to javafx.graphics;
    opens coco.state to javafx.graphics;
    opens coco to javafx.graphics;
}