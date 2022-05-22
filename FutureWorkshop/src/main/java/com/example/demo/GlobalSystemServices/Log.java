package com.example.demo.GlobalSystemServices;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    private static Log log_instance = null;
    public Logger logger,error_logger;
    FileHandler fh1,fh2;
    private final String logger1_file_name = "log.txt";
    private final String logger2_file_name = "Error_Log.txt";


    private Log() throws SecurityException, IOException {
        File f1 = new File(logger1_file_name);
        File f2 = new File(logger2_file_name);

        if(!f1.exists())
            f1.createNewFile();

        if(!f2.exists())
            f2.createNewFile();
        fh1 = new FileHandler(logger1_file_name,true);
        fh2 = new FileHandler(logger2_file_name,true);

        logger = Logger.getLogger("test");
        logger.addHandler(fh1);

        error_logger = Logger.getLogger("test2");
        error_logger.addHandler(fh2);

        SimpleFormatter formatter = new SimpleFormatter();
        fh1.setFormatter(formatter);
        fh2.setFormatter(formatter);

    }

    public void warning(String msg){
        error_logger.warning(msg);
    }
    public void info(String msg){
        logger.info(msg);
    }
    public static Log getLogger(){
        if(log_instance == null)
            try {
                log_instance = new Log();
            }catch ( IOException e)
            {
                return null;
            }
        return log_instance;

    }

}
