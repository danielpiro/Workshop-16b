package ExternalConnections.Delivery;
import ExternalConnections.ExternalConnections;


public class FedEx extends Delivery {


    public FedEx() {
        super(false,"FedEx", ExternalConnections.getInstance().getId(),false);
    }


    @Override
    protected int internalDelivery(float total) {
        if(total>0)
            return 0;
        return -1;
    }

    @Override
    public boolean connect(int key) {
        connected =true;
        return true;
    }
}
