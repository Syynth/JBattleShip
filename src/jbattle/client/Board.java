/**
 * @date Feb 8, 2013
 * @author Ben Cochrane
 */

package jbattle.client;

import java.util.ArrayList;
import jbattle.Config;

public class Board {
    
    public Board(int w, int h) {
        
        mInput = Boolean.parseBoolean(Config.getProperty("game", "aiControlled"))
                ? new AutomatedInput() : new UserInput();
        mRender = new Renderer();
        
        grid = new Cell[w][h];
        entityRegistry = new ArrayList<>();
        
        this.fillGrid();
        
        mRender.initGrid(grid);
    }
    
    private void fillGrid() {
        int numShips = Integer.parseInt(Config.getProperty("game", "numberOfShips"));
        int lenShips = Integer.parseInt(Config.getProperty("game", "lengthOfShips"));
        
        for (int x = 0; x < grid.length; ++x) {
            for (int y = 0; y < grid[0].length; ++y) {
                grid[x][y] = new Water();
            }
        }
        
        for (int i = 0; i < numShips; ++i) {
            int isVertical = (int)Math.round(Math.random());
            int rx = 0, ry = 0;
            if (isVertical == 1) { // ship IS vertical
                rx = (int)(Math.random() * grid.length);
                ry = (int)(Math.random() * grid[0].length - lenShips);
            } else { // ship is not vertical
                rx = (int)(Math.random() * grid.length - lenShips);
                ry = (int)(Math.random() * grid[0].length);
            }
            for (int j = 0; j < lenShips; ++j) {
                grid[rx + j][ry + j] = new BattleShip();
            }
        }
    }
    
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
            }
        }
        return r;
    }
    
    public Move[] getMoves(Result[] r) {
        Move[] m = new Move[1];
        m[0] = new Shoot(Shoot.Type.MOVE, Action.getGUID(), 0, 0);
        return m;
    }

    private Input mInput;
    private Renderer mRender;
    private ArrayList<Cell> entityRegistry;
    private Cell[][] grid;
}
