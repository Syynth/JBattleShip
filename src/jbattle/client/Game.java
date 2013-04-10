/**
 *
 * @author syynth
 */

package jbattle.client;

public class Game {

    public Game(Net jnet) {
        mNet = jnet;
        mNet.connect();
        
        mPlayerBoard = new PlayerBoard(mNet.isServer());
        mOppBoard = new OpponentBoard(mNet.isServer());
        mPlayerBoard.setOpponent(mOppBoard);
        
        if (mNet.isClient()) {
            mTurn = new Turn();
            mTurn.addMove(new Shoot(Shoot.Type.MOVE, Action.getGUID(), 0, 0));
            mTurn.addResult(new Shoot(Shoot.Type.RESULT, -1, 0, 0));
            mNet.sendTurn(mTurn);
        } else {
            mTurn = mNet.getTurn();
        }
    }

    public void execute() {
        
        int c = 0;
        
        while (mPlayerBoard.isAlive() && mOppBoard.isAlive()) {
            
            if (mNet.isClient() || c > 0) { // Server shouldn't ask for a turn
                mTurn = mNet.getTurn(); // 
            }
            
            // Compute effects of enemy turn
            mOppBoard.getMoves(mTurn.getResults());
            mOppBoard.update();
            
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
    private PlayerBoard mPlayerBoard;
    private OpponentBoard mOppBoard;
}
