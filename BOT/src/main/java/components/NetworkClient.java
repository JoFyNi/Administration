package components;

public class NetworkClient implements connection {
    protected int multicastPORT;

    public NetworkClient(int port) {
        this.multicastPORT = port;
    }

    public void connect() {
        // Implement connection logic
    }
}