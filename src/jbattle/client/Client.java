/**
 * @date Jan 30, 2013
 * @author Ben Cochrane
 */

package jbattle.client;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import jbattle.server.ServerProtocol;

public class Client {
    
    public Client() {
        
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
        
        ServerProtocol protocol = new ServerProtocol(ServerProtocol.CLIENT);
        writeMessage(protocol.handleMessage("begin"));
        
        int mode = -1;
        String address = "";
        
        do {    // service loop
            try {
                String smsg = mInput.readObject().toString();
                String cmsg = protocol.handleMessage(smsg);
                if ("accept wait".equals(cmsg)) {
                    cmsg += ":" + mPort;
                    mode = ClientProtocol.SERVER;
                } else if ("accept meet".equals(cmsg)) {
                    address = smsg.substring(4);
                    mode = ClientProtocol.CLIENT;
                }
                writeMessage(cmsg);
            } catch (ClassNotFoundException | IOException e) {}
        } while (!protocol.quit());
        
        
        Net jnet = new Net(mode, address);
        /*
        JBattleTalker jtalk = mode == SERVER ? ServerTalker(port) : ClientTalker(port);
        JBattleGame game = new JBattleGame(jtalk);
        game.execute();*/
        
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
        Client client = new Client();
        client.execute();
        
    }
    
    private Socket mConnection;
    private ObjectOutputStream mOutput;
    private ObjectInputStream mInput;
    private final int mPort = 13001;
}
