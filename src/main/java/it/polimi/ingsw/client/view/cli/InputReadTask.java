package it.polimi.ingsw.client.view.cli;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * InputReadTask
 */
public class InputReadTask implements Callable<String> {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Constructor of InputReadTask
     */
    public InputReadTask() {}

    /**
     * Method to read the task when it's ready
     * @return string
     * @throws Exception Exception
     */
    @Override
    public String call() throws Exception {
        while (!bufferedReader.ready()) {
            Thread.sleep(200);
        }
        return bufferedReader.readLine();
    }
}
