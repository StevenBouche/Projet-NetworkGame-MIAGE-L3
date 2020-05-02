package network.udp;

import network.share.Choice;
import network.protocol.ProtocolEvents;

public class ProtocolEventsUDP<T> extends ProtocolEvents<T> {

    public static ProtocolEventsUDP<Choice> SUBSCRIPTION = new ProtocolEventsUDP<Choice>("SUBSCRIPTION");

    private ProtocolEventsUDP(String name){
        super(name);
    }




}
