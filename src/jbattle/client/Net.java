/**
 * @date Feb 6, 2013
 * @author Ben Cochrane
 */
package jbattle.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Net {

    public Net(int mode, String address) {

        mMode = mode;

        try {
            String[] temp = address.split(":");
            if (mMode == ClientProtocol.CLIENT) {
                mSocket = new Socket(InetAddress.getByName(temp[0]), Integer.parseInt(temp[1]));
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
                mSocket = mServer.accept();
            }
            
            mInput = new ObjectInputStream(mSocket.getInputStream());
            mOutput = new ObjectOutputStream(mSocket.getOutputStream());
            
            if (mMode == ClientProtocol.CLIENT) {
                sendAttack("conn, 0, 0");
            }
        } catch (IOException e) {
            System.out.println("Unable to create input/output streams for client-to-client connection.");
        }

    }

    public void sendAttack(String msg) {
        
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
                String rmsg, s[]; // remote message, local message
                rmsg = mInput.readObject().toString();
                s = rmsg.split(",");
                if ("miss".equals(s[0]) || "hitt".equals(s[0])) {
                    atk = rmsg;
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Net.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return atk;
    }
    
    private int mMode;
    private Socket mSocket;
    private ServerSocket mServer;
    private ObjectInputStream mInput;
    private ObjectOutputStream mOutput;
    private ClientProtocol mProtocol;
}
