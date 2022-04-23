package ExternalConnections.Delivery;


public class FedEx implements Delivery{

    private boolean connected;
    private Object lock;
    private String name;

    public FedEx() {
        lock = new Object();
        this.connected = false;
        name = "FedEx";
    }

    @Override
    public int Delivery(int delivery) {
        synchronized (lock) {

            return 0;
        }
    }


    @Override
    public boolean connect(int key) {
        connected = true;
        return true;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public String getName() {
        return name;
    }
}
