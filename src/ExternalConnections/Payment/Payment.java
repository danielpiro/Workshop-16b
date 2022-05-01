package ExternalConnections.Payment;

public abstract class Payment {
    protected boolean connected;
    private String name;
    private int identifier;
    private boolean taken;
    private Object lock;

    public Payment(boolean connected, String name, int identifier, boolean taken) {
        this.connected = connected;
        this.name = name;
        this.identifier = identifier;
        this.taken = taken;
        lock = new Object();
    }

    //-1 already taken,
    public synchronized int payment (float total){


        int ans = internalPayment(total);
        taken=false;
        return ans;

    }

    protected abstract int internalPayment(float total);

    // use secure key to connect
    public abstract boolean connect (int key);



    public boolean isConnected(){
        return connected;
    }

    public String getName(){
        return name;
    }

    public  boolean isTaken(){
        synchronized (lock) {
            return taken;
        }
    }
    public   boolean setTakeTrue() {
        synchronized (lock) {
            taken = true;
        }
        return true;
    }




}
