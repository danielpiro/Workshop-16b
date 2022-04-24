package ExternalConnections.Payment;

public class MasterCard implements Payment {

    private boolean connected;
    private String name;
    private Object lock;

    public MasterCard() {
        lock = new Object();
        connected=false;
        name = "MasterCard";


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
