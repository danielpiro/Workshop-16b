package com.company;

public class Real implements BridgeInterface{

    private MarketSystem msApp;

    public Real(MarketSystem msApp) {
        this.msApp = msApp;
    }

    @Override
    public String login(String username, String password) {
        throw new UnsupportedOperationException("Not Implemented Yet!");
    }
}
