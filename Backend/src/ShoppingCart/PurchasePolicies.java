package ShoppingCart;

import ExternalConnections.Delivery.Delivery;
import ExternalConnections.Payment.Payment;

public class PurchasePolicies {
    private Delivery delivery;
    private Payment payment;

    public PurchasePolicies(Delivery delivery, Payment payment) {
        this.delivery = delivery;
        this.payment = payment;
    }

    public int tryToPurchase (int total,int deliveryDetails){
        int answer =0;

        System.out.println("trying to purchase with total:" + total +" and delivery weight is" +deliveryDetails );
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
