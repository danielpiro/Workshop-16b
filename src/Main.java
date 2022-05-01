import Controllers.Service;
import GlobalSystemServices.Log;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;


public class Main {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {


        Log.getLogger().logger.setLevel(Level.SEVERE);
        Service service  = new Service();

        Future future1 = service.sign_up("dan","rotman");
        Future future2 = service.sign_up("guy","porat");

        boolean a = (boolean) future1.get();
        future2.get();

        Future future3 = service.login("dan","rotman");
        Future future4 = service.login("guy","porat");
        future3.get();
        future4.get();



        System.out.println("finished running");


        service.shutdown();




    }
}
