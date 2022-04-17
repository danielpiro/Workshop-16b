package ExternalConnections;

import ExternalConnections.Delivery.Delivery;
import ExternalConnections.Delivery.FedEx;
import ExternalConnections.Payment.Payment;
import ExternalConnections.Payment.Visa;

public class ExternalConnections {

        public static Payment visa;
        public static Delivery fedEx;

    public ExternalConnections() {
        visaConnector();
        fedExConnector();

    }


    public static Payment getVisa (){
        return visa;
    }

    public static Delivery getFedEx(){
        return fedEx;
    }

    private void visaConnector (){
        visa = new Visa();
        visa.connect(123);
    }

    private void fedExConnector(){
        fedEx = new FedEx();
        fedEx.connect(456);
    }
}
