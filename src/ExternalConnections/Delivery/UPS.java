package ExternalConnections.Delivery;


public class UPS implements Delivery{

    private boolean connected;
    private Object lock;
    private String name;

    public UPS() {
        lock = new Object();
        this.connected = false;
        name = "UPS";
    }


    @Override
    public int Delivery(float delivery) {
        synchronized (lock) {

            return 0;
        }    }

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
