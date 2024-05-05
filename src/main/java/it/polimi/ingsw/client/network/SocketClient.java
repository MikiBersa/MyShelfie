package it.polimi.ingsw.client.network;

import it.polimi.ingsw.Constant;
import it.polimi.ingsw.server.network.Message.ErrorMessage;
import it.polimi.ingsw.server.network.Message.MessageByClient;
import it.polimi.ingsw.server.network.Message.MessageByServer;
import it.polimi.ingsw.utils.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Concrete class that implements SocketTCP
 */
public class SocketClient implements SocketTCP {
    private final Socket socket;
    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    private final InterfaceNetwork interfaceNetwork;
    private final ExecutorService readExecutor = Executors.newSingleThreadExecutor();
    private final Logger log = Log.getInstance();

    /**
     * Create s SocketClient class
     *
     * @param ip               ip of the server
     * @param port             port of the process
     * @param interfaceNetwork the interface network that handles the messages
     */
    public SocketClient(String ip, int port, InterfaceNetwork interfaceNetwork) {
        this.interfaceNetwork = interfaceNetwork;
        try {
            socket = new Socket(ip, port);
            socket.setSoTimeout(Constant.TIME_OUT_SOCKET);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            log.info("Exception during the opening of the socket and the streams: " + e.getMessage());
            shutdown();
            throw new RuntimeException(e);
        }
    }

    /**
     * Send the message to the server
     *
     * @param message the message to send
     */
    @Override
    public void sendMessage(MessageByClient message) {
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.reset();
        } catch (IOException e) {
            log.info("Exception during the write of a message: " + e.getMessage());
            interfaceNetwork.read(new ErrorMessage("Exception during the write of a message"));
        }
    }

    /**
     * Asynchronous function that awaits for message from the server and then it is passed to the InterfaceNetwork object
     */
    @Override
    public void receiveMessage() {
        readExecutor.execute(() -> {
            while (!readExecutor.isShutdown()) {
                MessageByServer message;
                try {
                    message = (MessageByServer) objectInputStream.readObject();
                } catch (SocketTimeoutException e) {
                    log.info("SocketTimeoutException: " + e.getMessage());
                    message = new ErrorMessage("SocketTimeoutException");
                } catch (IOException | ClassNotFoundException e) {
                    log.info("Exception during the read of a message: " + e.getMessage());
                    message = new ErrorMessage("You have disconnected from the game or a generic exception occured");
                }

                interfaceNetwork.read(message);
            }
        });
    }

    /**
     * Shutdown the client socket
     */
    @Override
    public void shutdown() {
        try {
            socket.close();
            objectOutputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            log.info("An exception has occurred during the close of the socket " + e.getMessage());
        }

        readExecutor.shutdown();
        try {
            if (!readExecutor.awaitTermination(3, TimeUnit.SECONDS)) {
                readExecutor.shutdownNow();

                if (!readExecutor.awaitTermination(3, TimeUnit.SECONDS))
                    log.info("Thread did not terminate in the correct way");
            }
        } catch (InterruptedException ie) {
            readExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
