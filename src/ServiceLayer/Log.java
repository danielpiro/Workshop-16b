package ServiceLayer;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    private static  Log log_instance = null;
    public Logger logger;
    FileHandler fh;

    private Log(String file_name) throws SecurityException, IOException{
        File f = new File(file_name);
        if(!f.exists()) f.createNewFile();
        fh = new FileHandler(file_name,true);
        logger = Logger.getLogger("test");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);

    }

    public static Log getLogger() throws IOException {
        if(log_instance == null) log_instance = new Log("log.txt");
        return log_instance;


    }

}
