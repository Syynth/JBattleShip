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
            
        }

    }

    private void writeMessage(String msg) {
    }

    private String readMessage() {
        return "";
    }
    private int mMode;
    private Socket mSocket;
    private ServerSocket mServer;
    private ObjectInputStream mInput;
    private ObjectOutputStream mOutput;
    private ClientProtocol mProtocol;
}
