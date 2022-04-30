package ExternalConnections.Delivery;
import ExternalConnections.ExternalConnections;
import GlobalSystemServices.Log;


public class FedEx extends Delivery {


    public FedEx() {
        super(false,"FedEx", ExternalConnections.getInstance().getId(),false);
    }


    @Override
    protected int internalDelivery(float total) {
        Log.getLogger().logger.fine("try to Deliver FedEx");

        if(total>0)
            return 0;
        return -1;
    }

    @Override
    public boolean connect(int key) {
        Log.getLogger().logger.info("FedEx connected");


        connected =true;
        return true;
    }
}
