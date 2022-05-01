package CustomExceptions;

public class ExternalServiceDoesNotExist extends Exception {
    public ExternalServiceDoesNotExist (String message){
        super(message);
    }
}
