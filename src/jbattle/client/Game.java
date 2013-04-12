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
        
        Turn t = new Turn();
        t.addMove(new Shoot(Shoot.Type.MOVE, 0, 0, 0));
        t.addResult(new Loss());
        mNet.sendTurn(t);
        
        Renderer r1 = mPlayerBoard.getRender(), 
                r2 = mOppBoard.getRender();
        if (!mOppBoard.isAlive()) {
            r1.setTitle("Victory!");
            r2.setTitle("Victory!");
        } else {
            r1.setTitle("Defeat!");
            r2.setTitle("Defeat!");
        }
        while (!r1.isCloseRequested() && !r2.isCloseRequested()) {
            mPlayerBoard.update();
            mOppBoard.update();
        }
        
    }
    
    private Net mNet;
    private Turn mTurn;
    private PlayerBoard mPlayerBoard;
    private OpponentBoard mOppBoard;
}
