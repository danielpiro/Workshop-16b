package ExternalConnections.Payment;
import ExternalConnections.ExternalConnections;


public class MasterCard extends PaymentAbstract  {




    public MasterCard() {
        connected=false;
        name = "MasterCard";
        taken = false;
        identifier = ExternalConnections.getInstance().getId();


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
    public synchronized int payment(float total) {

            return 0;


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

    @Override
    public synchronized  boolean isTaken() {
        return taken;
    }




}
