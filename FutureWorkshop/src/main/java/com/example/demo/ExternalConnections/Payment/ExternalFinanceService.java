package com.example.demo.ExternalConnections.Payment;


import com.example.demo.ExternalConnections.FinanceService;
import com.example.demo.ExternalConnections.HTTPPostClient;

import java.io.IOException;
import java.net.ConnectException;

public class ExternalFinanceService implements FinanceService {


    public boolean handshake() throws Exception {
        String response = HTTPPostClient.send(HTTPPostClient.makeHandshakeParams());
        boolean success = response.equals("OK");
        if (!success)
            throw new ConnectException();

        return true;
    }

    public String pay(String accName, String ccn, String expireDate, int cvv, String id) throws Exception {
        String[] date = expireDate.split("/");
        try {
            String response = HTTPPostClient.send(HTTPPostClient.makePayParams(ccn, date[0], date[1], accName, String.valueOf(cvv), id));
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
