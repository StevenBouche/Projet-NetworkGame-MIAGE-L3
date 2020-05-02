package network.share;

public class IPEndPoint {
    public String addr;
    public int port;
    public String toString(){
        return addr+":"+port;
    }
}
