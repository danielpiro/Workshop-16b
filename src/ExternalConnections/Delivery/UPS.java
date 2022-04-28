package ExternalConnections.Delivery;
import ExternalConnections.ExternalConnections;


public class UPS extends Delivery {


    public UPS() {
        super(false,"MaserCard",ExternalConnections.getInstance().getId(),false);
    }

    @Override
    protected int internalDelivery(float total) {
        if(total>0)
         return 0;
        return -1;
    }

    @Override
    public boolean connect(int key) {
        return true;
    }
}
