package jbattleship;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;

/**
 *
 * @author Ben Cochrane
 */
public class JBattleShipServer {

    JBattleShipServer() {

        try {
            mServer = new ServerSocket(13000, 10);
        } catch (IOException e) {
            System.out.println("Unable to create Server");
            System.exit(0);
        }

    }
    
    public static synchronized void addAddress(String address) {
        mAddresses.add(address);
    }
    
    public static synchronized String takeAddress() {
        if (!mAddresses.isEmpty()) {
            return mAddresses.remove(0);
        } else {
            return null;
        }
    }
    
    public static synchronized boolean hasAddress() {
        return mAddresses.size() > 0;
    }

    void execute() {

        while (JBattleShipServer.running) {
            try {
                new JBattleShipServerThread(mServer.accept()).run();
            } catch (IOException e) {
                System.out.println("Error creating server thread on new connection.");
                JBattleShipServer.running = false;
            }
        }

    }

    public static void main(String[] args) {

        JBattleShipServer app = new JBattleShipServer();

        while (JBattleShipServer.running) {
            app.execute();
        }

    }
    ServerSocket mServer;
    Socket mConnection;
    static private ArrayList<String> mAddresses;
    static boolean running = true;
}
