package com.example.demo.ExternalConnections.Payment;


import com.example.demo.ExternalConnections.ExternalConnections;
import com.example.demo.GlobalSystemServices.Log;

public class MasterCard extends Payment {



    public MasterCard() {
        super(false,"MasterCard", ExternalConnections.getInstance().getId(),false);
    }

    // todo remove = zero
    @Override
    protected int internalPayment(float total) {
        Log.getLogger().fine("try to pay with MasterCard");

        if(total>=0)
            return (int) total;
        return -1;
    }

    @Override
    public boolean connect(int key) {
        Log.getLogger().info("MasterCard connected");

        connected =true;
        return true;
    }



}
