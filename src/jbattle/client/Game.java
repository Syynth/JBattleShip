/**
 *
 * @author syynth
 */

package jbattle.client;

public class Game {

    public Game(Net jnet) {
        mNet = jnet;
        mNet.connect();
        
        mPlayerBoard = new Board(true);
        mEnemyBoard = new Board(false);
        
        if (mNet.isClient()) {
            mTurn = new Turn();
            mTurn.addMove(new Shoot(Shoot.Type.MOVE, Action.getGUID(), 0, 0));
            mNet.sendTurn(mTurn);
        } else {
            mTurn = mNet.getTurn();
        }
    }

    public void execute() {
        
        int c = 0;
        
        while (c <3000 /* This should ask both boards if they're dead.*/) {
            
            if (mNet.isClient() || c > 0) { // Server shouldn't ask for a turn
                mTurn = mNet.getTurn(); // 
            }
            
            // Compute effects of enemy turn
            mEnemyBoard.update();
            // Compute player turn
            Turn nextTurn = new Turn();
            
            nextTurn.addResults(mPlayerBoard.getResults(mTurn.getMoves()));
            nextTurn.addMoves(mPlayerBoard.getMoves(mTurn.getResults()));
            
            mPlayerBoard.update();
            
            mNet.sendTurn(nextTurn);
            
            c++;
        }
        
    }
    
    private Net mNet;
    private Turn mTurn;
    private Board mPlayerBoard;
    private Board mEnemyBoard;
}
