package com.example.demo.ExternalConnections.Delivery;


import com.example.demo.ExternalConnections.HTTPPostClient;
import com.example.demo.ExternalConnections.SupplyService;

import java.net.ConnectException;

public class ExternalSupplyService implements SupplyService {

    public boolean handshake() throws Exception {
        String response = HTTPPostClient.send(HTTPPostClient.makeHandshakeParams());
        boolean success = response.equals("OK");
        if (!success)
            throw new ConnectException();

        return true;
    }


    public boolean supply(String name, String address, String city, String country, String zip) throws Exception {

        String response = HTTPPostClient.send(
                HTTPPostClient.makeSupplyParams(name, address, city, country, zip)
        );

        return !response.equals("-1");

    }

    public boolean cancel_supply(String transaction_id) throws Exception{

        String response = HTTPPostClient.send(
                HTTPPostClient.makeCancelSupplyParams(transaction_id)
        );

        return !response.equals("-1");
    }
}
