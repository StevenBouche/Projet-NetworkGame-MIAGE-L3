package network.share;

public interface DataListener<T> {
    void onData(IPEndPoint var1, T var2);
}
