package network.share;

public interface ListenerState {

    void onRunning(String str);
    void onShutdown();
}
