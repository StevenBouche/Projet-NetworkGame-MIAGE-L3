package network.protocol;

import network.share.DataListener;

public class ProtocolEvents<T> {

    public String eventName;

    public ProtocolEvents(String name){
        eventName = name;
    }

    public Protocol getProtocol(Class<T> co, DataListener<T> listener){
        return new Protocol<T>(co, eventName, listener);
    }

}
