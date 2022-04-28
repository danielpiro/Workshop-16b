package ExternalConnections.Payment;

public abstract class PaymentAbstract   {
    private boolean connected;
    private String name;
    private int identifier;
    private boolean taken;

    //-1 already taken,
    public synchronized int payment (float total){
        if(taken == true)
            return -1;
        int ans = internalPayment(total);
        taken=false;
        return ans;

    }

    public abstract int internalPayment(float total);

    // use secure key to connect
    public abstract boolean connect (int key);


    public boolean isConnected(){
        return connected;
    }

    public String getName(){
        return name;
    }

    public boolean isTaken(){
        return taken;
    }



}
