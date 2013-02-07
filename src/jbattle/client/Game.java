/**
 *
 * @author syynth
 */

package jbattle.client;

public class Game {

    public Game(Net jnet) {
        mNet = jnet;
        mNet.connect();
        if (mNet.isClient()) {
            mNet.sendAttack("miss, 0, 0");
        } else {
            mNet.getAttack();
        }
    }

    public void execute() {
        System.out.println(mNet.getAttack());
        //getAttackResult();
        //getAttackPosition();
        mNet.sendAttack("miss, 0, 0");
    }
    
    private Net mNet;
}
