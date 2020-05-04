package network.udp;

import network.message.obj.Choice;
import network.message.obj.DataServerGame;
import network.protocol.ProtocolEvents;

public class ProtocolEventsUDP<T> extends ProtocolEvents<T> {

    public static ProtocolEventsUDP<Choice> SUBSCRIPTION = new ProtocolEventsUDP<Choice>("SUBSCRIPTION");
    public static ProtocolEventsUDP<DataServerGame> GETLISTSERVERGAME = new ProtocolEventsUDP<>("GETLISTSERVERGAME");

    private ProtocolEventsUDP(String name){
        super(name);
    }




}
