package ExternalConnections.Delivery;
import ExternalConnections.ExternalConnections;



public class UPS implements Delivery{

    private boolean connected;
    private String name;
    private boolean taken;
    private int identifier;


    public UPS() {
        this.connected = false;
        name = "UPS";
        taken = false;
        identifier = ExternalConnections.getInstance().getId();

    }


    @Override
    public  synchronized int Delivery(float delivery) {

            return 0;
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
