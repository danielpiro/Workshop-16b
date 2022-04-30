package ExternalConnections.Delivery;
import ExternalConnections.ExternalConnections;
import GlobalSystemServices.Log;


public class UPS extends Delivery {


    public UPS() {
        super(false,"UPS",ExternalConnections.getInstance().getId(),false);
    }

    @Override
    protected int internalDelivery(float total) {
        Log.getLogger().logger.fine("try to Deliver UPS");

        if(total>0)
         return 0;
        return -1;
    }

    @Override
    public boolean connect(int key) {
        Log.getLogger().logger.info("UPS connected");

        connected = true;
        return true;
    }
}
