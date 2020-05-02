module org.miage.interfaceFX {
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires org.miage.network;
    exports controllerJavafx;
    exports network.main;
    opens network.main to javafx.graphics;
    opens controllerJavafx to javafx.graphics;
}