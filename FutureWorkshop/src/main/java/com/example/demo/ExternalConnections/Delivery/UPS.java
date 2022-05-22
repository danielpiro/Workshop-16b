package com.example.demo.ExternalConnections.Delivery;


import com.example.demo.ExternalConnections.ExternalConnections;
import com.example.demo.GlobalSystemServices.Log;

public class UPS extends Delivery {


    public UPS() {
        super(false,"UPS", ExternalConnections.getInstance().getId(),false);
    }

    @Override
    protected int internalDelivery(float total) {
        Log.getLogger().fine("try to Deliver UPS");

        if(total>=0)
            return (int) total;
        return -1;
    }

    @Override
    public boolean connect(int key) {
        Log.getLogger().info("UPS connected");

        connected = true;
        return true;
    }
}
