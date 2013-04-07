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
        Renderer r = new Renderer();
        
        mPlayerBoard = new Board(i, r, Integer.parseInt(Config.getProperty("game", "boardWidth")),
                                 Integer.parseInt(Config.getProperty("game", "boardHeight")));
        mEnemyBoard = new Board(i, r, Integer.parseInt(Config.getProperty("game", "boardWidth")),
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
        
        while (c <30 /* This should ask both boards if they're dead.*/) {
            
            if (mNet.isClient() || c > 0) { // Server shouldn't ask for a turn
                mTurn = mNet.getTurn(); // 
            }
            
            // Compute effects of enemy turn
            
            // Compute player turn
            Turn nextTurn = new Turn();
            
            nextTurn.addResults(mPlayerBoard.getResults(mTurn.getMoves()));
            nextTurn.addMoves(mPlayerBoard.getMoves(mTurn.getResults()));
            
            mNet.sendTurn(nextTurn);
            
            c++;
        }
        
    }
    
    private Net mNet;
    private Turn mTurn;
    private Board mPlayerBoard;
    private Board mEnemyBoard;
}
