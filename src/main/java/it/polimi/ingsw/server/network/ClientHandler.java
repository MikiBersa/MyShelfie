package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.network.Message.MessageByClient;
import it.polimi.ingsw.server.network.Message.MessageByServer;
import it.polimi.ingsw.server.network.Message.input.DisconnectionMessage;
import it.polimi.ingsw.utils.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 * ClientHandler Class has the aim to handler the connection between client and server
 */
public class ClientHandler implements Runnable, InterfaceClientSend {
    private final Socket socket;
    private final InterfaceNetwork interfaceNetwork;
    private final int id;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean loop;

    /**
     * Constructor of ClientHandler
     * @param id the unique id of the player
     * @param socket the socket of the player
     * @param interfaceNetwork the reference to the InterfaceNetwork
     */
    public ClientHandler(int id, Socket socket, InterfaceNetwork interfaceNetwork) {
        this.socket = socket;
        this.interfaceNetwork = interfaceNetwork;
        this.id = id;
        this.loop = true;
        createScanner();
    }

    /**
     * Method that creates the scanner
     */
    private void createScanner() {
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException var) {
            System.out.println("ERROR IN CLIENT of socket.close");
            System.err.println(var.getMessage());
        }
    }

    /**
     * the run function in order to read and to write in the socket
     */
    @Override
    public void run() {
        try {
            socket.setKeepAlive(true);
                while (loop) {
                    MessageByClient message;

                    try {
                        message = (MessageByClient) this.in.readObject();
                        receive(message);
                        /*
                    } catch (IOException | ClassNotFoundException e) {
                        System.err.println("MESSAGE: "+ e.getMessage());
                        System.err.println("MESSAGE e: "+e);
                        throw new RuntimeException(e);
                    }*/
                    } catch (Exception e) {
                        System.out.println("ERRORE: "+e.getMessage());
                        e.getStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            } catch(SocketException e){
                throw new RuntimeException(e);
            }finally {
                System.out.println ("User closed connection: "+id);
                removeClient();
            }
    }

    /**
     * send message to the client
     * @param message message has sent to client by server
     */
    public synchronized void send(MessageByServer message) {
        System.out.println("SENT: "+id+" OUT: "+message);
        try {
            this.out.writeObject(message);
            this.out.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * receive message from the client
     * @param message message has sent to server by client
     */
    public void receive(MessageByClient message) {

        System.out.println("IN: "+message);

        this.interfaceNetwork.notifyToServer(this.id, message);
    }

    /**
     * close the socket of this client
     */
    @Override
    public void closeSocket() {
        try {
            System.err.println("SOCKET: "+id+" CLOSED");
            this.socket.close();
            loop = false;
        }catch (IOException e){
            System.err.println("ERROR CLOSING SOCKET");
        }
    }

    /**
     * get socket of this client
     * @return return Socket
     */
    @Override
    public Socket getSocket(){return this.socket;}

    /**
     * remove client from the list of players' connections in interfaceNetwork
     */
    public void removeClient() {
        this.interfaceNetwork.RemoveFromNetwork(this.id);
        receive(new DisconnectionMessage());
        loop = false;
    }
}
