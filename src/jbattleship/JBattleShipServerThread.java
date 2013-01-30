/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jbattleship;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @date Jan 30, 2013
 * @author Ben Cochrane
 */
public class JBattleShipServerThread implements Runnable {
    
    public JBattleShipServerThread(Socket connection) {
        
        mConnection = connection;
        JBattleShipServer.addAddress(mConnection.getInetAddress().getHostAddress());
        
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
            System.out.println("Can't flush");
        }
        
    }
    
    @Override
    public void run() {
        
        if (mOutput == null || mInput == null) {
            System.out.println("Error: Running Server thread that was not properly initialized");
            return;
        }
        
        String msg = "";
        
        do {    // service loop
            try {
                msg = mInput.readObject().toString();
                if ("send".equals(msg.toLowerCase())) {
                    if (JBattleShipServer.hasAddress()) {
                        this.writeMessage("meet" + JBattleShipServer.takeAddress());
                    } else {
                        this.writeMessage("wait");
                    }
                }
            } catch (ClassNotFoundException | IOException e) {
                System.out.println("Unable to read object from mInput.");
            }
        } while (!"quit".equals(msg.toLowerCase()));
        
        try {   // close everything down after being quit
            mInput.close();
            mOutput.close();
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
