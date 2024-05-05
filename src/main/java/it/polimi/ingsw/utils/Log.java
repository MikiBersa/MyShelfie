package it.polimi.ingsw.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * The Log is a file that is created for viewing the specified action of the application in detail
 */
public class Log {
    private static Logger instance = null;

    /**
     * A method that creates the log file with a specific format
     * @param name name of the file
     */
    public static void createLog(String name) {
        if (instance == null) {
            instance = Logger.getLogger(Log.class.getName());
            try {
                FileHandler fh = new FileHandler("logs/" + name + "logFile.log");
                MyFormat formatter = new MyFormat();
                fh.setFormatter(formatter);
                instance.addHandler(fh);
                instance.setUseParentHandlers(false);
            } catch (IOException e) {
                System.out.println("Non sono riuscito a creare il logger");
                throw new RuntimeException(e);
            }
            instance.info("Logger has been created");
        } else {
            instance.info("You cannot create another log");
        }
    }

    /**
     * getInstance
     * @return Logger
     */
    public static Logger getInstance() {
        return instance;
    }
}
