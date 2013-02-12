/**
 *
 * @author syynth
 */

package jbattle.client;

import jbattle.Config;

public class Game {

    public Game(Net jnet) {
        mNet = jnet;
        mNet.connect();
        
        Input i = Boolean.parseBoolean(Config.getProperty("game", "aiControlled")) ? new AutomatedInput() : new UserInput();
        
        mPlayerBoard = new Board(i, Integer.parseInt(Config.getProperty("game", "boardWidth")),
                                 Integer.parseInt(Config.getProperty("game", "boardHeight")));
        mEnemyBoard = new Board(i, Integer.parseInt(Config.getProperty("game", "boardWidth")),
                                 Integer.parseInt(Config.getProperty("game", "boardHeight")));
        
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
            
            if (mNet.isClient() || c > 0) {
                mTurn = mNet.getTurn();
            }
            mNet.sendTurn(mTurn);
            
            c++;
        }
        
    }
    
    private Net mNet;
    private Turn mTurn;
    private Board mPlayerBoard;
    private Board mEnemyBoard;
}
