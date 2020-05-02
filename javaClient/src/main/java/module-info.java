module javaClient {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    exports controller;
    exports main;
    opens main to javafx.graphics;
    opens controller to javafx.graphics;
}