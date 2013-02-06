/**
 * @date Jan 30, 2013
 * @author Ben Cochrane
 */

package jbattle.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable {
    
    public ServerThread(Socket connection) {
        
        System.out.println("Server thread started.");
        
        mConnection = connection;
        
        try {
            mOutput = new ObjectOutputStream(mConnection.getOutputStream());
            mInput = new ObjectInputStream(mConnection.getInputStream());
        } catch (IOException e) {
            System.out.print("Error creating input/output streams with connection: ");
            System.out.println(mConnection.getInetAddress().getHostAddress());
            mOutput = null;
            mInput = null;
        }
        
        try {
            if (mOutput != null) {
                mOutput.flush();
            }
        } catch (IOException e) {
            System.out.println("Unable to flush output.");
        }
        
    }
    
    @Override
    public void run() {
        
        if (mOutput == null || mInput == null) {
            System.out.println("Error: Running Server thread that was not properly initialized");
            return;
        }
        
        ServerProtocol protocol = new ServerProtocol(ServerProtocol.SERVER);
        
        do {    // service loop
            try {
                String msg = protocol.handleMessage(mInput.readObject().toString());
                if (msg.contains("wait accepted")) {
                    Server.addAddress(mConnection.getInetAddress()
                                     .getHostAddress() + msg.substring(13));
                } else {
                    System.out.println("server> " + msg);
                    writeMessage(msg);
                }
            } catch (ClassNotFoundException | IOException e) {
            }
        } while (!protocol.quit());
        
        try {   // close everything down after being quit
            mInput.close();
            mOutput.close();
            System.out.println("Closing server thread.");
        } catch (IOException e) {
            System.out.print("Error closing input/output streams with connection: ");
            System.out.println(mConnection.getInetAddress().getHostAddress());
        }
        
    }
    
    private boolean writeMessage(String message) {
        
        try {
            mOutput.writeObject(message);
            mOutput.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Error writing message: " + message);
            return false;
        }
        
    }
    
    private Socket mConnection;
    private ObjectOutputStream mOutput;
    private ObjectInputStream mInput;
}
