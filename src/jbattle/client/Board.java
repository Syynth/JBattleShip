/**
 * @date Feb 8, 2013
 * @author Ben Cochrane
 */

package jbattle.client;

import java.util.ArrayList;
import jbattle.Config;

public abstract class Board {
    
    public Board() {
        
        int w = Config.getInt("game", "boardWidth");
        int h = Config.getInt("game", "boardHeight");
        mInput = Config.getBoolean("game", "aiControlled")
                ? new AutomatedInput() : new UserInput();
        
        mRender = (!Config.getBoolean("fancyGraphics") ?
                new Renderer() : new GLRenderer());
        
        mGrid = new Cell[w][h];
        entityRegistry = new ArrayList<>();
        
    }
    
    public Renderer getRender() {
        return mRender;
    }
    
    protected abstract void fillGrid();
    
    public void update() {
        mRender.draw(mGrid);
    }
    
    public void addEntity(Cell e) {
        if (mGrid[e.x][e.y] != null)  {
            entityRegistry.remove(mGrid[e.x][e.y]);
            System.out.println("Entity placed on top of another in the board!");
        }
        entityRegistry.add(e);
        mGrid[e.x][e.y] = e;    
    }
    
    public void removeEntity(Cell e) {
        if (entityRegistry.remove(e)) {
            for (int x = 0; x < mGrid.length; ++x) {
                for (int y = 0; y < mGrid[0].length; ++y) {
                    if (mGrid[x][y] == e) {
                        mGrid[x][y] = null;
                        x = mGrid.length;
                        y = mGrid[0].length;
                    }
                }
            }
        }
    }
    
    public void removeEntity(int x, int y) {
        entityRegistry.remove(mGrid[x][y]);
        mGrid[x][y] = null;
    }
    
    public Result[] getResults(Move[] m) {
        Result[] r = new Result[m.length];
        for (int i = 0; i < m.length; ++i) {
            Action a = (Action)m[i];
            if (mGrid[a.x][a.y] instanceof BattleShip) {
                BattleShip b = (BattleShip)mGrid[a.x][a.y];
                b.sink();
                r[i] = (Result)(new Shoot(Shoot.Type.RESULT, a.getID(), a.x, a.y, true));
            } else {
                r[i] = (Result)(new Shoot(Shoot.Type.RESULT, a.getID(), a.x, a.y, false));
                ((Water)mGrid[a.x][a.y]).sink();
            }
        }
        return r;
    }
    
    public Move[] getMoves(Result[] r) {
        Move[] m = new Move[1];
        m[0] = new Shoot(Shoot.Type.MOVE, Action.getGUID(), (int)(Math.random() * mGrid.length), (int)(Math.random() * mGrid[0].length));
        return m;
    }
    
    /**
     *
     * @param mOpp Sets the opponent reference to help determine which moves to make.
     */
    public void setOpponent(Board mOpp) {
        mOppBoard = mOpp;
    }
    
    public Cell[][] getGrid() {
        return mGrid;
    }

    protected Input mInput;
    protected Renderer mRender;
    protected ArrayList<Cell> entityRegistry;
    protected Cell[][] mGrid;
    protected Board mOppBoard;
}
