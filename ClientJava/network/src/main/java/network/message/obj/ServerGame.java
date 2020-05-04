package network.message.obj;

public class ServerGame {

    public String name;
    public String addr;
    public int port;
    public int nbPlayerCurrent;
    public int nbPlayerMax;

    public ServerGame(){

    }

    public String getName(){
        return this.name;
    }

    public String getAddr(){
        return this.addr;
    }

    public int getPort(){
        return this.port;
    }

    public int getNbPlayerCurrent(){
        return this.nbPlayerCurrent;
    }

    public int getNbPlayerMax(){
        return this.nbPlayerMax;
    }
}
