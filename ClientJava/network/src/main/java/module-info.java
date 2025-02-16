module org.miage.network {
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires javafx.graphics;
    exports network.client;
    exports network.share;
    exports network.message;
    exports network.udp;
    exports network.message.obj;
    exports network.tcp;
}