/**
 * @date Jan 30, 2013
 * @author Ben Cochrane
 */

package jbattleclient;

import java.net.Socket;
import java.io.IOException;

public class JBattleShipClient {
    
    public JBattleShipClient() {
        
        try {
            mConnection = new Socket("localhost", 13000);
        } catch (IOException e) {
            
        }
        
    }

    public void execute() {
        
        while (JBattleShipClient.running) {
            
        }
        
    }
    
    public static void main(String args[])
    {
        
        JBattleShipClient client = new JBattleShipClient();
        client.execute();
        
    }
    
    Socket mConnection;
    static boolean running = false;
}
