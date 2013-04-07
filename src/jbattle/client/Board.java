/**
 * @date Feb 8, 2013
 * @author Ben Cochrane
 */

package jbattle.client;

import java.util.ArrayList;

public class Board {
    
    public Board(Input i, Renderer r, int w, int h) {
        
        mInput = i;
        mRender = r;
        
        grid = new Entity[w][h];
        entityRegistry = new ArrayList<>();
        
        for (int x = 0; x < w; ++x) {
            for (int y = 0; y < h; ++y) {
                grid[x][y] = null;
            }
        }
    }
    
    public void addEntity(Entity e) {
        if (grid[e.x][e.y] != null)  {
            entityRegistry.remove(grid[e.x][e.y]);
            System.out.println("Entity placed on top of another in the board!");
        }
        entityRegistry.add(e);
        grid[e.x][e.y] = e;    
    }
    
    public void removeEntity(Entity e) {
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
                b.takeDamage(1);
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
    
    public void Render() {
        
    }

    private Input mInput;
    private Renderer mRender;
    private ArrayList<Entity> entityRegistry;
    private Entity[][] grid;
}
