package com.example.demo.ExternalConnections;



import com.example.demo.CustomExceptions.Exception.ExternalServiceDoesNotExist;
import com.example.demo.ExternalConnections.Delivery.Delivery;
import com.example.demo.ExternalConnections.Delivery.DeliveryNames;
import com.example.demo.ExternalConnections.Payment.Payment;
import com.example.demo.ExternalConnections.Payment.PaymentNames;
import com.example.demo.GlobalSystemServices.Log;

import java.util.LinkedList;
import java.util.List;


//to make singleton?
public class ExternalConnections {

    private static ExternalConnections externalConnections;

    public  List<Payment> payments;
    public  List<Delivery> deliveries;
    private Object lockid;
    private Object lockDelivery;
    private Object lockPayment;
    private int id;


    private ExternalConnections() {
        payments = new LinkedList<>();
        deliveries = new LinkedList<>();
        id =0;
        lockid= new Object();
        lockDelivery= new Object();
        lockPayment= new Object();

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
            Log.getLogger().logger.info("adding payment " + payment.getName() + "to external connections");
            return true;
        }
        else {
            Log.getLogger().logger.warning("payment " + payment.getName() + "could not connect");

            return false;
        }
    }

    public  boolean addDelivery (Delivery delivery){
        if(delivery.connect(123)) {
            deliveries.add(delivery);
            Log.getLogger().logger.info("adding delivery " + delivery.getName() + "to external connections");

            return true;
        }
        else {
            Log.getLogger().logger.warning("delivery " + delivery.getName() + "could not connect");

            return false;
        }
    }

    public  Payment getCertainPayment (PaymentNames name) throws ExternalServiceDoesNotExist {
        synchronized (lockPayment) {
            for (Payment p : payments) {
                if (p.getName().equals(name.toString()) && p.isConnected() && !p.isTaken()) {
                    p.setTakeTrue();
                    return p;
                }
            }
            throw new ExternalServiceDoesNotExist("name");
        }
    }

    public  Delivery getCertainDelivery (DeliveryNames name) throws ExternalServiceDoesNotExist {
        synchronized (lockDelivery) {

            for (Delivery d : deliveries) {
                if (d.getName().equals(name.toString()) && d.isConnected() && !d.isTaken())
                    return d;
            }
            throw new ExternalServiceDoesNotExist("name");
        }
    }
    public  boolean removePayment (PaymentNames payment) {
        synchronized (lockPayment) {
            Log.getLogger().logger.info("removing payment " + payment + "from external connections");


            for (Payment p : payments) {
                if (p.getName().equals(payment.toString())){
                    return true;
                }
            }
                return false;
        }
    }

    public    boolean removeDelivery (DeliveryNames delivery) {
        synchronized (lockDelivery) {

            Log.getLogger().logger.info("removing delivery " + delivery + "from external connections");

            for (Delivery p : deliveries) {
                if (p.getName().equals(delivery.toString())){
                    return true;
                }
            }
            return false;
        }
    }
    public int getId(){
        synchronized (lockid){
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
