package ExternalConnections.Payment;

import ExternalConnections.ExternalConnections;

public class Visa extends Payment {



    public Visa() {
        super(false,"Visa",ExternalConnections.getInstance().getId(),false);
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
        return false;
    }
}
