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
        
        mRender = (Config.getBoolean("fancyGraphics") ?
                new Renderer() : new GLRenderer()).show();
        
        grid = new Cell[w][h];
        entityRegistry = new ArrayList<>();
        
    }
    
    protected abstract void fillGrid();
    
    public void update() {
        mRender.draw();
    }
    
    public void addEntity(Cell e) {
        if (grid[e.x][e.y] != null)  {
            entityRegistry.remove(grid[e.x][e.y]);
            System.out.println("Entity placed on top of another in the board!");
        }
        entityRegistry.add(e);
        grid[e.x][e.y] = e;    
    }
    
    public void removeEntity(Cell e) {
        if (entityRegistry.remove(e)) {
            for (int x = 0; x < grid.length; ++x) {
                for (int y = 0; y < grid[0].length; ++y) {
                    if (grid[x][y] == e) {
                        grid[x][y] = null;
                        x = grid.length;
                        y = grid[0].length;
                    }
                }
            }
        }
    }
    
    public void removeEntity(int x, int y) {
        entityRegistry.remove(grid[x][y]);
        grid[x][y] = null;
    }
    
    public Result[] getResults(Move[] m) {
        Result[] r = new Result[m.length];
        for (int i = 0; i < m.length; ++i) {
            Action a = (Action)m[i];
            if (grid[a.x][a.y] instanceof BattleShip) {
                BattleShip b = (BattleShip)grid[a.x][a.y];
                b.sink();
                r[i] = (Result)(new Shoot(Shoot.Type.RESULT, a.getID(), a.x, a.y, true));
            } else {
                r[i] = (Result)(new Shoot(Shoot.Type.RESULT, a.getID(), a.x, a.y, false));
                ((Water)grid[a.x][a.y]).sink();
            }
        }
        return r;
    }
    
    public Move[] getMoves(Result[] r) {
        Move[] m = new Move[1];
        m[0] = new Shoot(Shoot.Type.MOVE, Action.getGUID(), (int)(Math.random() * grid.length), (int)(Math.random() * grid[0].length));
        return m;
    }

    protected Input mInput;
    protected Renderer mRender;
    protected ArrayList<Cell> entityRegistry;
    protected Cell[][] grid;
}
