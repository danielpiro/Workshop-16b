package CustomExceptions;

public class CantPurchaseException  extends Exception {
    public CantPurchaseException(String message) {
        super(message);
    }

}
