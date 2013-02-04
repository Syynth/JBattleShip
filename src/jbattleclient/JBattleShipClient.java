/**
 * @date Jan 30, 2013
 * @author Ben Cochrane
 */

package jbattleclient;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import jbattleserver.JBattleShipProtocol;

public class JBattleShipClient {
    
    public JBattleShipClient() {
        
        try {
            mConnection = new Socket("localhost", 13000);
            mInput = new ObjectInputStream(mConnection.getInputStream());
            mOutput = new ObjectOutputStream(mConnection.getOutputStream());
        } catch (IOException e) {
            
        }
        
    }

    public void execute() {
        
        JBattleShipProtocol protocol = new JBattleShipProtocol(JBattleShipProtocol.CLIENT);
        writeMessage(protocol.handleMessage("begin"));
        do {    // service loop
            try {
                String msg = protocol.handleMessage(mInput.readObject().toString());
                writeMessage(msg);
            } catch (ClassNotFoundException | IOException e) {
            }
        } while (!protocol.quit());
        
    }
    
    private boolean writeMessage(String message) {
        
        try {
            mOutput.writeObject(message);
            mOutput.flush();
            System.out.println("client> " + message);
            return true;
        } catch (IOException e) {
            System.out.println("Error writing message: " + message);
            return false;
        }
        
    }
    
    public static void main(String args[])
    {
        
        System.out.println("Client started.");
        JBattleShipClient client = new JBattleShipClient();
        client.execute();
        
    }
    
    Socket mConnection;
    ObjectOutputStream mOutput;
    ObjectInputStream mInput;
    static boolean running = true;
}
