package ExternalConnections.Payment;

import ExternalConnections.ExternalConnections;

public interface Payment {

    // 0 is success, 1 is connection invalid, 2 is insufficient funds
    int payment (int total);

    // use secure key to connect
    boolean connect (int key);


    boolean isConnected();

    String getName();
}
