package com.example.demo.ExternalConnections;

public interface FinanceService {

    public boolean handshake() throws Exception;
    public String pay(String accName, String ccn, String expireDate, int cvv, String id) throws Exception;
    public String cancel_pay(String tId) throws Exception;
}
