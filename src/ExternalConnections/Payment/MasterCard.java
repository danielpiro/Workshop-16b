package ExternalConnections.Payment;
import ExternalConnections.ExternalConnections;


public class MasterCard extends Payment {



    public MasterCard() {
        super(false,"MasterCard",ExternalConnections.getInstance().getId(),false);
    }

    // 0 is
    @Override
    protected int internalPayment(float total) {
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
