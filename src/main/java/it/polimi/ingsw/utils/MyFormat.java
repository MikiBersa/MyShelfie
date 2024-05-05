package it.polimi.ingsw.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * MyFormat to help the work of logger to write the action in log file
 */
public class MyFormat extends Formatter {
    /**
     * A method that return the string of the format
     * @param record the log record to be formatted.
     * @return string record formatted
     */
    @Override
    public String format(LogRecord record) {
        ZonedDateTime zonedDateTime = record.getInstant().atZone(ZoneId.of("Europe/Paris"));
        return "Thread:" + Thread.currentThread().getId() + " " + record.getSourceClassName() + " "
                + record.getSourceMethodName() + " "
                + zonedDateTime.getHour() + ":"
                + (zonedDateTime.getMinute() <= 9 ? "0" + zonedDateTime.getMinute() : zonedDateTime.getMinute()) + ":"
                + (zonedDateTime.getSecond() <= 9 ? "0" + zonedDateTime.getSecond() : zonedDateTime.getSecond()) + " "
                + record.getMessage() + "\n";
    }
}
