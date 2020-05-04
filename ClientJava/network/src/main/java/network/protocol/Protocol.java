package network.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import network.share.DataListener;
import network.share.DataListenerTCP;
import network.share.IPEndPoint;

import java.io.IOException;

public class Protocol<T> extends ProtocolNetwork{

    private DataListener<T> listener;
    private DataListenerTCP<T> listenerTCP;
    private Class classObj;
    public String eventName;

    public Protocol(Class<T> classObj, String name, DataListener<T> listener){
        eventName = name;
        this.listener = listener;
        this.classObj = classObj;
    }

    public Protocol(Class<T> classObj, String name, DataListenerTCP<T> listener){
        eventName = name;
        this.listenerTCP = listener;
        this.classObj = classObj;
    }

    public void onReceive(String obj, IPEndPoint ep)  {
        ObjectMapper objectMapper = new ObjectMapper();
        T o = null;
        try {
            o = (T) objectMapper.readValue(obj.toString(), classObj);
            listener.onData(ep,o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onReceive(String obj)  {
        ObjectMapper objectMapper = new ObjectMapper();
        T o = null;
        try {
            o = (T) objectMapper.readValue(obj.toString(), classObj);
            listenerTCP.onData(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Class getClassGeneric(){
        return classObj;
    }
}
