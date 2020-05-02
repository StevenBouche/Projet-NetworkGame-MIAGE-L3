module org.miage.network {
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    exports network.server;
    exports network.share;
    exports network.message;
    exports network.udp;
}