package com.company;

public class Proxy implements BridgeInterface {
    private BridgeInterface real;

    public void setReal(BridgeInterface real) {
        this.real = real;
    }

    @Override
    public String login(String username, String password) {
        if(real!=null)
            return real.login(username, password);
        return "logged in successfully as "+username;
    }
}
