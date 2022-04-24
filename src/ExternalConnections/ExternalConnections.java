package ExternalConnections;

import CustomExceptions.ExternalServiceDoesNotExist;
import ExternalConnections.Delivery.Delivery;
import ExternalConnections.Delivery.FedEx;
import ExternalConnections.Payment.Payment;
import ExternalConnections.Payment.Visa;

import java.util.LinkedList;
import java.util.List;


//to make singleton?
public class ExternalConnections {

    public static List<Payment> payments;
    public static List<Delivery> deliveries;


    public ExternalConnections() {
        payments = new LinkedList<>();
        deliveries = new LinkedList<>();
    }


    public  boolean addPayment (Payment payment){
        if(payment.connect(123)) {
            payments.add(payment);
            return true;
        }
        else
            return false;
    }

    public  boolean addDelivery (Delivery delivery){
        if(delivery.connect(123)) {
            deliveries.add(delivery);
            return true;
        }
        else
            return false;
    }

    public  Payment getCertainPayment (String name) throws ExternalServiceDoesNotExist {
        for (Payment p : payments)
        {
            if(p.getName().equals(name))
                return p;
        }
        throw new ExternalServiceDoesNotExist(name);
    }

    public  Delivery getCertainDelivery (String name) throws ExternalServiceDoesNotExist {
        for (Delivery p : deliveries)
        {
            if(p.getName().equals(name))
                return p;
        }
        throw new ExternalServiceDoesNotExist(name);
    }


    /*//return the payment methods that didnt connect
    private List<Payment>  connectPayments (){
        List<Payment> didntConnect = new LinkedList<>();
        for (Payment p : payments){
            if (!p.connect(111))
                didntConnect.add(p);
        }
        return didntConnect;
    }

    //return the deliveries methods that didnt connect
    private List<Delivery>  connectDelivery (){
        List<Delivery> didntConnect = new LinkedList<>();
        for (Delivery d : deliveries){
            if (!d.connect(111))
                didntConnect.add(d);
        }
        return didntConnect;
    }*/


}
