package ExternalConnections.Payment;

public interface Payment {

    // 0 is success, 1 is connection invalid, 2 is insufficient funds
    int payment (float total);

    // use secure key to connect
    boolean connect (int key);


    boolean isConnected();

    String getName();
}
