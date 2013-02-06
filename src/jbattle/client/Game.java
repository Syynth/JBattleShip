/**
 *
 * @author syynth
 */

package jbattle.client;

public class Game {

    public Game(Net jnet) {
        
        mNet = jnet;
        mNet.connect();
        
    }

    public void execute() {
        
        /*
        mNet.getAttack();
        getAttackResult();
        getAttackPosition();
        mNet.sendAttack();
        */
    }
    
    private Net mNet;
}
