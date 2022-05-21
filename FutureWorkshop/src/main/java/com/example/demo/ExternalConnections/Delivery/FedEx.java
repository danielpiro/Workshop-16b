package com.example.demo.ExternalConnections.Delivery;


import com.example.demo.ExternalConnections.ExternalConnections;
import com.example.demo.GlobalSystemServices.Log;

public class FedEx extends Delivery {


    public FedEx() {
        super(false,"FedEx", ExternalConnections.getInstance().getId(),false);
    }


    @Override
    protected int internalDelivery(float total) {
        Log.getLogger().logger.fine("try to Deliver FedEx");

        if(total>=0)
            return (int) total;
        return -1;
    }

    @Override
    public boolean connect(int key) {
        Log.getLogger().logger.info("FedEx connected");


        connected =true;
        return true;
    }
}
