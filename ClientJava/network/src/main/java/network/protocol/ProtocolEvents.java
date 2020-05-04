package network.protocol;

import network.share.DataListener;
import network.share.DataListenerTCP;

public class ProtocolEvents<T> {

    public String eventName;

    public ProtocolEvents(String name){
        eventName = name;
    }

    public Protocol getProtocol(Class<T> co, DataListener<T> listener){
        return new Protocol<T>(co, eventName, listener);
    }

    public Protocol getProtocol(Class<T> co, DataListenerTCP<T> listener){
        return new Protocol<T>(co, eventName, listener);
    }

}
