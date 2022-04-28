package ExternalConnections;

import CustomExceptions.ExternalServiceDoesNotExist;
import ExternalConnections.Delivery.Delivery;
import ExternalConnections.Delivery.FedEx;
import ExternalConnections.Payment.Payment;
import ExternalConnections.Payment.Visa;
import History.History;

import java.util.LinkedList;
import java.util.List;


//to make singleton?
public class ExternalConnections {

    private static ExternalConnections externalConnections;

    public  List<Payment> payments;
    public  List<Delivery> deliveries;
    private Object lock;
    private int id;


    private ExternalConnections() {
        payments = new LinkedList<>();
        deliveries = new LinkedList<>();
        id =0;
        lock = new Object();
    }



    //todo check if admin
    public static ExternalConnections getInstance() {

        // most will skip synchronized section, only in the beggining will one thread enter and create history.
        if (externalConnections == null) {
            synchronized (ExternalConnections.class) {
                if (externalConnections == null) {
                    externalConnections = new ExternalConnections();
                }
            }
        }
        return externalConnections;
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

    public synchronized Payment getCertainPayment (String name) throws ExternalServiceDoesNotExist {
        for (Payment p : payments)
        {
            if(p.getName().equals(name) && p.isConnected() && !p.isTaken()) {
                p.setTakenTrue();
                return p;
            }
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

    public  boolean removePayment (Payment payment){
        if(payments.contains(payment)) {
            payments.remove(payment);
            return true;
        }
        else
            return false;
    }

    public  boolean removeDelivery (Delivery delivery){
        if(payments.contains(delivery)) {
            payments.remove(delivery);
            return true;
        }
        else
            return false;
    }
    public int getId(){
        synchronized (lock){
            id++;
            return id;
        }
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
