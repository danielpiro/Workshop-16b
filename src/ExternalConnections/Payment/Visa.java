package ExternalConnections.Payment;

import ExternalConnections.ExternalConnections;

public class Visa implements Payment {

    private boolean connected;
    private String name;
    private boolean taken;
    private int identifier;

    public Visa() {
        connected=false;
        name = "Visa";
        identifier = ExternalConnections.getInstance().getId();
    }



    private int internalPayment (float total){



    }
    




    public synchronized boolean setTakenTrue(){
        if(taken == false) {
            taken = true;
            return true;
        }
        return false;
    }
    public synchronized boolean setTakenFree(){
        if(taken == true) {
            taken = false;
            return true;
        }
        return false;
    }

    @Override
    public  synchronized boolean isTaken() {
        return taken;
    }

}
