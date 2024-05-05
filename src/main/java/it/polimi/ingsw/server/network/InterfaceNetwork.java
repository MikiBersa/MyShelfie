package it.polimi.ingsw.server.network;

import it.polimi.ingsw.server.RestartServerInterface;
import it.polimi.ingsw.server.Router;
import it.polimi.ingsw.server.network.Message.MessageByClient;
import it.polimi.ingsw.server.network.Message.MessageByServer;
import it.polimi.ingsw.server.network.Observer.oberserverIn.InputInterface;
import it.polimi.ingsw.server.network.Observer.oberserverIn.ManagementObserverIn;
import it.polimi.ingsw.server.network.Observer.oberserverIn.ObservableIn;
import it.polimi.ingsw.server.network.Observer.oberserverOut.ManagementObserver;
import it.polimi.ingsw.server.network.Observer.oberserverOut.Observer;
import it.polimi.ingsw.server.view.VirtualView;
import java.net.Socket;
import java.net.SocketException;
import java.util.Hashtable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import static it.polimi.ingsw.Constant.DELAY_PING;
import static it.polimi.ingsw.Constant.TIME_OUT_SOCKET;

/**
 * InterfaceNetwork has the aim to handler the connection with the clients
 */

public class InterfaceNetwork implements OutputInterface, ObservableIn {
    //I use InputInterface because in this way I block the call of other internal functions of the Router which are made private anyways
    private InputInterface router;
    private Hashtable<Integer, InterfaceClientSend> clients;
    private ManagementObserverIn managementObserverIn;
    private RestartServerInterface server;
    private Pinger pinger;
    private boolean start;
    private final ScheduledExecutorService pingerScheduled = Executors.newSingleThreadScheduledExecutor();


    /**
     * Constructor of the InterfaceNetwork
     * @param server the reference to server in order to restart server at the end of game
     */
    public InterfaceNetwork(RestartServerInterface server){
        this.server = server;
        this.managementObserverIn = new ManagementObserverIn();
        ManagementObserver managmentObserver = new ManagementObserver();
        Observer virtualView = new VirtualView(this);
        managmentObserver.attach(virtualView);

        this.router = new Router(this, managmentObserver);
        this.managementObserverIn.attach(this.router);

        this.clients = new Hashtable<>();
        pinger = new Pinger(this.clients);
        start = true;
    }

    /**
     * restart of the server at the end of game
     */
    private void reInterfaceNetwork(){
        this.server.serverRestar();
    }

    /**
     * send message to client
     * @param id the id of player if it is equal to -1, it will send to everybody (broadcast)
     * @param message messe to send
     */
    public void send(int id, MessageByServer message){
        //use -1 to broadcast them to everyone
        if(id != -1){
            sendToClientPlayer(id, message);
        }else{
            sendToClientAll(message);
        }
    }

    /**
     * send personalized to send a message in a specific way
     * @param id id of the player to send yesTurn
     * @param noTurn message to send to the others
     * @param yesTurn message to send to id player
     */
    public void sendPersonalized(int id, MessageByServer noTurn, MessageByServer yesTurn){
        for (Integer chiave : clients.keySet()) {
            if(chiave == id) clients.get(chiave).send(yesTurn);
            else clients.get(chiave).send(noTurn);
        }
    }

    /**
     * notify the sending of message to the server
     * @param id id of the player who has sent the message
     * @param message message sent by player
     */
    @Override
    public synchronized void notifyToServer(int id, MessageByClient message){
        this.managementObserverIn.notifyToServer(id, message);
    }

    /**
     * send the message to a specific client
     * @param id id of the socket that is connected to the socket
     * @param message message to send
     */
    private void sendToClientPlayer(int id, MessageByServer message){
        // here calls the method of the output class
        if(this.clients.get(id) != null){
            this.clients.get(id).send(message);
        }else{
            // the software can't get here
            System.err.println("ERROR NOT EXIST ID");
        }
    }

    /**
     * add the client to the list of players who had connected to the server
     * @param id the id of the player
     * @param interfaceClientSend represent the socket of the player
     */
    public void addToNetwork(int id, InterfaceClientSend interfaceClientSend){
        // IMPORTANT IS THE USE OF THE ID BECAUSE IT MAPPES THE CLIENTHANDLER'S ID WITH THE FUNDAMENTAL USER'S ID
        this.clients.put(id, interfaceClientSend);
        Socket socket = interfaceClientSend.getSocket();

        if(start){
            pingerScheduled.scheduleAtFixedRate(pinger, 0,DELAY_PING, TimeUnit.MILLISECONDS);
            start = false;
        }

        try {
            // set the timeout for the connection
            socket.setSoTimeout(TIME_OUT_SOCKET);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * remove client from the list of connections
     * @param id the id of the player
     */
    public void RemoveFromNetwork(int id){
        this.clients.get(id).closeSocket();
        this.clients.remove(id);
    }

    /**
     * remove all the players from the list of connections
     */
    public void RemoveAllNetwork(){
        pingerScheduled.shutdownNow();
        for(Integer id : clients.keySet()){
            RemoveFromNetwork(id);
        }
        reInterfaceNetwork();
    }


    /**
     * send message to everybody in broadcast
     * @param message message to send
     */
    private void sendToClientAll(MessageByServer message){
        for (Integer chiave : clients.keySet()) {
            clients.get(chiave).send(message);
        }
    }
}
