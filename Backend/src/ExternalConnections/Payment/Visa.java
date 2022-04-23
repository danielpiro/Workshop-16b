package ExternalConnections.Payment;

public class Visa implements Payment {

    private boolean connected;
    private String name;
    private Object lock;

    public Visa() {
        lock = new Object();
        connected=false;
        name = "Visa";


    }

    @Override
    public int payment(int total) {
        synchronized (lock) {

            return 0;
        }

    }

    @Override
    public boolean connect(int key) {
        connected=true;
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
