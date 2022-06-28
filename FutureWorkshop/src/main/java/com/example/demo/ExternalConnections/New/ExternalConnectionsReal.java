package com.example.demo.ExternalConnections.New;

import com.example.demo.CustomExceptions.Exception.ExternalServiceDoesNotExist;

import java.io.IOException;
import java.net.ConnectException;

public class ExternalConnectionsReal {

    private static ExternalConnectionsReal externalConnections;

    public static ExternalConnectionsReal getInstance() {

        // most will skip synchronized section, only in the beggining will one thread enter and create history.
        if (externalConnections == null) {
            synchronized (ExternalConnectionsReal.class) {
                if (externalConnections == null) {
                    externalConnections = new ExternalConnectionsReal();
                }
            }
        }
        return externalConnections;
    }



    public boolean handshake() throws ExternalServiceDoesNotExist {
        String response;
        try {
            response = HTTPPostClient.send(HTTPPostClient.makeHandshakeParams());
        }
        catch (Exception e){
            throw  new ExternalServiceDoesNotExist("could not send params");
        }
        boolean success = response.equals("OK");
        if (!success)
            return false;

        return true;
    }


    public String supply(String name, String address, String city, String country, String zip) throws Exception {

        String response = HTTPPostClient.send(
                HTTPPostClient.makeSupplyParams(name, address, city, country, zip)
        );

        return response;

    }

    public boolean cancel_supply(String transaction_id) throws Exception{

        String response = HTTPPostClient.send(
                HTTPPostClient.makeCancelSupplyParams(transaction_id)
        );

        return !response.equals("-1");
    }

    public String pay(String holder, String cardNumber, String expireDate, int cvv, String id) throws Exception {
        String[] date = expireDate.split("/");
        try {
            String response = HTTPPostClient.send(HTTPPostClient.makePayParams(cardNumber, date[0], date[1], holder, String.valueOf(cvv), id));
            return response;
        }catch (IOException e){
            System.out.println("Time out !");
            return "-1";
        }

    }

    public String cancel_pay(String tId) throws Exception {

        return HTTPPostClient.send(HTTPPostClient.makeCancelPayParams(tId));
    }
}
