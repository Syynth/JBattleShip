/**
 * @date Jan 30, 2013
 * @author Ben Cochrane
 */

package jbattle.client;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import jbattle.Config;
import jbattle.server.ServerProtocol;

public class Client {
    
    public Client() {
        Config.LoadConfig("./resources/config.xml");
        if (Config.getBoolean("useLocationBroker")) {
            try {
                mConnection = new Socket(Config.getProperty("serverAddress"), Config.getInt("serverPort"));
                mInput = new ObjectInputStream(mConnection.getInputStream());
                mOutput = new ObjectOutputStream(mConnection.getOutputStream());
            } catch (IOException e) {
                System.out.println("Unable to create connection to server.");
            }
        }
    }

    public void execute() {
        int mode = -1;
        String address = "127.0.0.1:" + Config.getProperty("clientPort");
        if (Config.getBoolean("useLocationBroker")) {
            if (mInput == null || mOutput == null) {
                System.out.println("Unable to create input/output streams.");
                return;
            }

            ServerProtocol protocol = new ServerProtocol(ServerProtocol.CLIENT);
            protocol.setClientPort(Config.getInt("clientPort"));
            writeMessage(protocol.handleMessage("begin"));

            

            do {    // service loop
                try {

                    String smsg = mInput.readObject().toString();
                    String cmsg = protocol.handleMessage(smsg);

                    if (cmsg.equals("accept wait:" + Config.getProperty("clientPort"))) {
                        mode = ClientProtocol.SERVER;
                    } else if ("accept meet".equals(cmsg)) {
                        System.out.println("server> " + smsg);
                        address = smsg.substring(4);
                        mode = ClientProtocol.CLIENT;
                    }

                    writeMessage(cmsg);
                } catch (ClassNotFoundException | IOException e) {}
            } while (!protocol.quit());

            if (mode == ClientProtocol.SERVER) {
                System.out.println("***Server mode");
            } else {
                System.out.println("***Client mode");
            }
        } else {
            address = Config.getProperty("serverAddress") + ":" + Config.getProperty("serverPort");
            if (Config.getBoolean("defaultToClient")) {
                mode = ClientProtocol.CLIENT;
            } else {
                mode = ClientProtocol.SERVER;
            }
        }
        Net jnet = new Net(mode, address);
        Game game = new Game(jnet);
        game.execute();
        
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
}
