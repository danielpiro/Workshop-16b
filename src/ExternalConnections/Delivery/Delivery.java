package ExternalConnections.Delivery;



public interface Delivery {


    // 0 is success, 1 is connection invalid, 2 not deliverable.
    // Recieve list of items
    // todo what does delievery recieve.
    int Delivery (int delivery);

    // use secure key to connect
    boolean connect (int key);

    boolean isConnected ();

    String getName ();
}
