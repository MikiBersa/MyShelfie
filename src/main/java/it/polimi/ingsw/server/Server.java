package it.polimi.ingsw.server;

import it.polimi.ingsw.server.network.ClientHandler;
import it.polimi.ingsw.server.network.InterfaceNetwork;
import it.polimi.ingsw.utils.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static it.polimi.ingsw.Constant.PORT;

/**
 * The server fo the game
 */
public class Server implements RestartServerInterface {
    private final int port;
    private int idUser;
    private InterfaceNetwork interfaceNetwork;

    /**
     * Constructor of the server
     *
     * @param port the number of the port in which the server will start to listen
     */
    public Server(int port) {
        this.port = port;
        this.idUser = 0;


        // the server just has to create InterfaceNetwork the other things are created in cascade
        this.interfaceNetwork = new InterfaceNetwork(this);
    }

    /**
     * Main method to start the server
     *
     * @param args
     */
    public static void main(String[] args) {
        Server echoServer = new Server(PORT);
        echoServer.startServer();
    }

    /**
     * Method to restart the server for new game
     */
    public void serverRestar() {
        // controllare da qui indietro
        this.interfaceNetwork = new InterfaceNetwork(this);
        this.idUser = 0;
    }

    /**
     * This method starts the server to ready to receive new connections
     */
    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        System.out.println("Server ready...");

        while(true) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(idUser, socket, interfaceNetwork);
                interfaceNetwork.addToNetwork(idUser, clientHandler);

                executor.submit(clientHandler);
                // to send to the player
                System.out.println("CONNECTED CLIENT ID: " + idUser);

                this.idUser++;

            } catch (IOException var5) {
                System.out.println("CLOSING SERVER SOCKET");
                executor.shutdown();

                try {
                    System.err.println("CLOSING DEL SERVER startServer");
                    serverSocket.close();
                    return;
                } catch (IOException var4) {
                    System.err.println(var4.getMessage());
                    return;
                }
            }
        }
    }
}
