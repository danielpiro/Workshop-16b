package ExternalConnections.Payment;

import ExternalConnections.ExternalConnections;
import GlobalSystemServices.Log;

public class Visa extends Payment {



    public Visa() {
        super(false,"Visa",ExternalConnections.getInstance().getId(),false);
    }


    // 0 is
    @Override
    protected int internalPayment(float total) {
        Log.getLogger().logger.fine("try to pay with Visa");

        if(total>0)
            return 0;
        return -1;
    }

    @Override
    public boolean connect(int key) {
        Log.getLogger().logger.info("Visa connected");

        connected =true;
        return true;
    }
}
