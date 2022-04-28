package ExternalConnections;

import ExternalConnections.Delivery.Delivery;
import ExternalConnections.Payment.Payment;
import GlobalSystemServices.Log;

public class PurchasePolicies {
    private String delivery;
    private String payment;
    private Log my_log ;


    public PurchasePolicies(String delivery, String payment) {
        this.delivery = delivery;
        this.payment = payment;
        try {
            my_log = Log.getLogger();
        }catch (Exception e)
        {}
    }


    public int tryToPurchase (float total,float deliveryDetails){
        int answer =0;
        boolean gotDelivery=false;
        boolean gotPayment = false;
        Delivery deliveryObject=null;
        Payment paymentObject=null;


        my_log.logger.info("trying to purchase with total:" + total +" and delivery weight is" +deliveryDetails );




        while (!gotDelivery) {
            try {
                deliveryObject = ExternalConnections.getInstance().getCertainDelivery(delivery);
                gotDelivery=true;
            }catch (Exception e){ }

        }
        while (!gotPayment) {
            try {
                paymentObject = ExternalConnections.getInstance().getCertainPayment(payment);
                gotPayment=true;
            }catch (Exception e){ }

        }

        answer = deliveryObject.delivery(deliveryDetails);
        answer +=paymentObject.payment(total);






        return answer;

    }
    public String getDeliveryName() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getPaymentName() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

}
