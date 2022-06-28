package com.example.demo.Tests.Unit;


import com.example.demo.ExternalConnections.Old.Delivery.Delivery;
import com.example.demo.ExternalConnections.Old.Delivery.DeliveryNames;
import com.example.demo.ExternalConnections.Old.Delivery.UPS;
import com.example.demo.ExternalConnections.Old.ExternalConnections;
import com.example.demo.ExternalConnections.Old.Payment.Payment;
import com.example.demo.ExternalConnections.Old.Payment.PaymentNames;
import com.example.demo.ExternalConnections.Old.Payment.Visa;
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