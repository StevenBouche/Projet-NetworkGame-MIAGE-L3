package network.udp;

import network.message.obj.Choice;
import network.message.obj.DataServerGame;
import network.protocol.ProtocolEvents;

public class ProtocolEventsUDP<T> extends ProtocolEvents<T> {

    public static ProtocolEventsUDP<Choice> SUBSCRIPTION = new ProtocolEventsUDP<Choice>("SUBSCRIPTION");
    public static ProtocolEventsUDP<DataServerGame> GETLISTSERVERGAME = new ProtocolEventsUDP<>("GETLISTSERVERGAME");
    public static ProtocolEventsUDP<String> NEWGAME = new ProtocolEventsUDP<String>("NEWGAME");
    public static ProtocolEventsUDP<String> REMOVEGAME = new ProtocolEventsUDP<String>("REMOVEGAME");
    private ProtocolEventsUDP(String name){
        super(name);
    }




}
