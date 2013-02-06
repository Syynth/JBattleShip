/**
 *
 * @author Ben Cochrane
 */

package jbattle.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;

public class Server {

    Server() {

        try {
            mServer = new ServerSocket(13000, 10);
            mAddresses = new ArrayList<>();
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
            return "";
        }
        
    }

    void execute() {

        while (Server.running) {
            try {
                new ServerThread(mServer.accept()).run();
            } catch (IOException e) {
                System.out.println("Error creating server thread on new connection.");
                Server.running = false;
            }
        }

    }

    public static void main(String[] args) {

        Server app = new Server();
        app.execute();

    }
    
    private ServerSocket mServer;
    private Socket mConnection;
    private static ArrayList<String> mAddresses;
    private static boolean running = true;
}
