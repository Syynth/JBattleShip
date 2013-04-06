/**
 * @date Feb 6, 2013
 * @author Ben Cochrane
 */
package jbattle.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Net {

    public Net(int mode, String address) {

        mMode = mode;

        try {
            String[] temp = address.trim().split(":");
            if (mMode == ClientProtocol.CLIENT) {
                mSocket = new Socket(temp[0], Integer.parseInt(temp[1]));
                mProtocol = new ClientProtocol(ClientProtocol.CLIENT);
            } else {
                mServer = new ServerSocket(Integer.parseInt(temp[1]));
                mProtocol = new ClientProtocol(ClientProtocol.SERVER);
            }
        } catch (IOException e) {
            System.out.println("Unable to initialize socket for client-to-client connection.");
        }

    }

    public void connect() {

        try {
            if (mMode == ClientProtocol.SERVER) {
                System.out.println("Server mode engaged.");
                mSocket = mServer.accept();
            } else {
                System.out.println("Client mode engaged.");
            }
            if (mSocket == null) {
                System.out.println("Socket is null!");
                return;
            }
            
            mOutput = new ObjectOutputStream(mSocket.getOutputStream());
            mInput = new ObjectInputStream(mSocket.getInputStream());
            
            System.out.println("Input/Output streams initalized.");
        } catch (IOException e) {
            System.out.println("Unable to create input/output streams for client-to-client connection.");
        }
        
        try {
            mOutput.flush();
        } catch (IOException e) {
            System.out.println("Error flushing output.");
        }

    }

    public void sendAttack(String msg) {
        System.out.println("Writing message: " + msg);
        try {
            mOutput.writeObject(msg);
            mOutput.flush();
        } catch (IOException e) {
            System.out.println("Error writing message: " + msg);
        }
        
    }

    public String getAttack() {
        String atk = "";
        
        while (!"".equals(atk)) {
            try {
                String rmsg, s[]; // remote message, pieces of rmsg
                rmsg = mInput.readObject().toString();
                s = rmsg.split(",");
                if ('m' == s[0].toLowerCase().charAt(0) ||
                    'h' == s[0].toLowerCase().charAt(0) ||
                    'l' == s[0].toLowerCase().charAt(0)) {
                    atk = rmsg;
                }
            } catch (IOException | ClassNotFoundException ex) {}
        }
        return atk;
    }
    
    public void sendTurn(Turn t) {
        System.out.println("Writing message: " + t);
        try {
            mOutput.writeObject(new String(t.toString().getBytes(), "UTF-8"));
            mOutput.flush();
        } catch (IOException e) {
            System.out.println("Error writing message: " + t);
        }
    }
    
    public Turn getTurn() {
        Turn t = null;
        while (true) {
            try {
                String rmsg, s[]; // remote message, pieces of rmsg
                rmsg = mInput.readObject().toString();
                return new Turn(rmsg);
            } catch (IOException | ClassNotFoundException ex) {
            }
        }
    }
    
    public boolean isClient() {
        return mMode == ClientProtocol.CLIENT;
    }
    
    public boolean isServer() {
        return mMode == ClientProtocol.SERVER;
    }
    
    private int mMode;
    private Socket mSocket;
    private ServerSocket mServer;
    private ObjectInputStream mInput;
    private ObjectOutputStream mOutput;
    private ClientProtocol mProtocol;
}
