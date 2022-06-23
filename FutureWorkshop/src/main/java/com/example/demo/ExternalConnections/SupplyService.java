package com.example.demo.ExternalConnections;

public interface SupplyService {

    public boolean handshake() throws Exception;
    public boolean supply(String name, String address, String city, String country, String zip) throws Exception;
    public boolean cancel_supply(String transaction_id) throws Exception;
}
