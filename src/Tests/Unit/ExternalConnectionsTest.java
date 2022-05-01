package Tests.Unit;

import ExternalConnections.Delivery.Delivery;
import ExternalConnections.Delivery.DeliveryNames;
import ExternalConnections.Delivery.UPS;
import ExternalConnections.ExternalConnections;
import ExternalConnections.Payment.Payment;
import ExternalConnections.Payment.PaymentNames;
import ExternalConnections.Payment.Visa;
import History.History;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExternalConnectionsTest {


    ExternalConnections externalConnections;



    @BeforeEach
    void setUp()
    {
        UPS ups = new UPS();
        Visa visa= new Visa();
        externalConnections= ExternalConnections.getInstance();
        externalConnections.addDelivery(ups);
        externalConnections.addPayment(visa);

    }

    @Test
    void getCertainPayment() {
        try {
           Payment p= externalConnections.getCertainPayment(PaymentNames.Visa);
           return;
        } catch (Exception e)
        {
            fail("couldnt get payment");
        }

    }

    @Test
    void getCertainDelivery() {
        try {
            Delivery p= externalConnections.getCertainDelivery(DeliveryNames.UPS);
            return;
        } catch (Exception e)
        {
            fail("couldnt get payment");
        }

    }

    @Test
    void removePayment() {

           if(externalConnections.removePayment(PaymentNames.Visa))
               return;
           else
               fail ("couldnt remove payment");


    }

    @Test
    void removeDelivery() {


        if(externalConnections.removeDelivery(DeliveryNames.UPS))
            return;
        else
            fail ("couldnt remove payment");

    }

}