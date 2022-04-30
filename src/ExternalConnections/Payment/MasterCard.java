package ExternalConnections.Payment;
import ExternalConnections.ExternalConnections;
import GlobalSystemServices.Log;


public class MasterCard extends Payment {



    public MasterCard() {
        super(false,"MasterCard",ExternalConnections.getInstance().getId(),false);
    }

    // 0 is
    @Override
    protected int internalPayment(float total) {
        Log.getLogger().logger.fine("try to pay with MasterCard");

        if(total>0)
            return 0;
        return -1;
    }

    @Override
    public boolean connect(int key) {
        Log.getLogger().logger.info("MasterCard connected");

        connected =true;
        return true;
    }



}
