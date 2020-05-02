package network.share;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import network.protocol.Protocol;
import network.udp.ProtocolEventsUDP;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EventNetworkManager {

    HashMap<String, Protocol<?>> mapEvents;
    ConcurrentLinkedQueue<ObjectNode> queue;
    ObjectMapper objectMapper = new ObjectMapper();

    public EventNetworkManager(){
        mapEvents = new HashMap<>();
    }

    public <T> void OnEvent(Class<T> co, ProtocolEventsUDP<T> proto, DataListener<T> listener){
        mapEvents.put(proto.eventName,proto.getProtocol(co,listener));
    }

    public void OnReceivedData(String obj, IPEndPoint endPoint)
    {
        try {
            ObjectNode actualObj = (ObjectNode) objectMapper.readTree(obj);
            actualObj.put("endpoint",objectMapper.writeValueAsString(endPoint));
//            queue.offer(actualObj);
            if(actualObj.get("data")!= null && actualObj.get("evt")!= null && actualObj.get("endpoint") != null){
                Protocol<?> p = mapEvents.get(actualObj.get("evt").textValue());
                p.onReceive(actualObj.get("data").toString(),endPoint);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
