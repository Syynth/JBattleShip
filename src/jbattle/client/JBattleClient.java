/**
 * @date Jan 30, 2013
 * @author Ben Cochrane
 */

package jbattle.client;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import jbattle.server.JBattleServerProtocol;

public class JBattleClient {
    
    public JBattleClient() {
        
        try {
            mConnection = new Socket("localhost", 13000);
            mInput = new ObjectInputStream(mConnection.getInputStream());
            mOutput = new ObjectOutputStream(mConnection.getOutputStream());
        } catch (IOException e) {
            System.out.println("Unable to create connection to server.");
        }
        
    }

    public void execute() {
        
        if (mInput == null || mOutput == null) {
            System.out.println("Unable to create input/output streams.");
            return;
        }
        
        JBattleServerProtocol protocol = new JBattleServerProtocol(JBattleServerProtocol.CLIENT);
        writeMessage(protocol.handleMessage("begin"));
        int mode = -1;
        do {    // service loop
            try {
                String msg = protocol.handleMessage(mInput.readObject().toString());
                if ("accept wait".equals(msg)) {
                    msg += ":" + mPort;
                    mode = JBattleClientProtocol.SERVER;
                } else if ("accept meet".equals(msg)) {
                    mode = JBattleClientProtocol.CLIENT;
                }
                writeMessage(msg);
            } catch (ClassNotFoundException | IOException e) {}
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
        JBattleClient client = new JBattleClient();
        client.execute();
        
    }
    
    private Socket mConnection;
    private ObjectOutputStream mOutput;
    private ObjectInputStream mInput;
    private final int mPort = 13001;
}
