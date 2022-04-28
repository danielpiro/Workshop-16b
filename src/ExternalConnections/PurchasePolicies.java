package ExternalConnections;

import ExternalConnections.Delivery.Delivery;
import ExternalConnections.Payment.Payment;
import GlobalSystemServices.Log;

public class PurchasePolicies {
    private Delivery delivery;
    private Payment payment;
    private Log my_log ;


    public PurchasePolicies(Delivery delivery, Payment payment) {
        this.delivery = delivery;
        this.payment = payment;
        try {
            my_log = Log.getLogger();
        }catch (Exception e)
        {}
    }


    public int tryToPurchase (float total,float deliveryDetails){
        int answer =0;

        my_log.logger.info("trying to purchase with total:" + total +" and delivery weight is" +deliveryDetails );
        if(delivery.isConnected() & payment.isConnected())
        {
            answer = delivery.Delivery(deliveryDetails);
            answer +=payment.payment(total);
        }





        return answer;

    }
    public String getDeliveryName() {
        return delivery.getName();
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public String getPaymentName() {
        return payment.getName();
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

}
