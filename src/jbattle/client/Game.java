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
            mTurn = new Turn();
            mTurn.addMove(new Shoot(Shoot.Type.MOVE, Action.getGUID(), 0, 0));
            mTurn.addMove(new Radar(Radar.Type.MOVE, Action.getGUID(), 0, 0, Radar.Direction.EAST));
            mNet.sendTurn(mTurn);
        } else {
            mTurn = mNet.getTurn();
        }
    }

    public void execute() {
        
        int c = 0;
        
        while (c <30 ) {
            
            if (mNet.isClient() || c > 0)
                mTurn = mNet.getTurn();
            mNet.sendTurn(mTurn);
            
            c++;
        }
        
    }
    
    private Net mNet;
    private Turn mTurn;
}
