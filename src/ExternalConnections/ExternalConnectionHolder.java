package ExternalConnections;

import ExternalConnections.Delivery.Delivery;
import ExternalConnections.Delivery.DeliveryNames;
import ExternalConnections.Payment.Payment;
import ExternalConnections.Payment.PaymentNames;
import GlobalSystemServices.Log;

public class ExternalConnectionHolder {
    private DeliveryNames delivery;
    private PaymentNames payment;
    private static int TRYTOCONNECT = 20;
    private Log my_log;


    public ExternalConnectionHolder(DeliveryNames delivery, PaymentNames payment) {
        this.delivery = delivery;
        this.payment = payment;
        try {
            my_log = Log.getLogger();
        } catch (Exception e) {
        }
    }


    public int tryToPurchase(float total, float deliveryDetails) {
        int answer = 0;
        boolean gotDelivery = false;
        boolean gotPayment = false;
        Delivery deliveryObject = null;
        Payment paymentObject = null;


        my_log.logger.info("trying to purchase with total:" + total + " and delivery weight is" + deliveryDetails);


        for (int i = 0; i < TRYTOCONNECT; i++) {
            try {
                deliveryObject = ExternalConnections.getInstance().getCertainDelivery(delivery);
                gotDelivery = true;
            } catch (Exception e) {
            }

        }

        for (int i = 0; i < TRYTOCONNECT; i++) {

            try {
                paymentObject = ExternalConnections.getInstance().getCertainPayment(payment);
                gotPayment = true;
            } catch (Exception e) {
            }

        }

        if (gotDelivery==false | gotPayment == false){
            my_log.logger.warning("could not purchase, because couldn't not obtain external connection of " + delivery+ " or " + payment );

            return -1;
        }

        answer = deliveryObject.delivery(deliveryDetails);
        answer += paymentObject.payment(total);


        return answer;

    }

    public DeliveryNames getDeliveryName() {
        return delivery;
    }

    public void setDelivery(DeliveryNames delivery) {
        this.delivery = delivery;
    }

    public PaymentNames getPaymentName() {
        return payment;
    }

    public void setPayment(PaymentNames payment) {
        this.payment = payment;
    }

}
