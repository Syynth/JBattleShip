/**
 *
 * @author Ben Cochrane
 */

package jbattle.client;

import java.util.LinkedList;
import java.awt.Dimension;
import jbattle.Config;

public class AutomatedInput extends Input {

    public AutomatedInput() {
        mScheduledShots = new LinkedList<>();
        mSpacing = Integer.parseInt(Config.getProperty("game", "lengthOfShips"));
        mOffset = 0;
        w = Integer.parseInt(Config.getProperty("game", "boardWidth"));
        h = Integer.parseInt(Config.getProperty("game", "boardHeight"));
        
        this.fillShots();
    }
    
    /**
     *
     * @param grid
     * @return A Move object representing the next move to take
     */
    @Override
    public Move getNextMove(Cell[][] grid) {
        Dimension d = findNextTarget(grid);
        if (d == null) {
            if (mScheduledShots.size() <= 0) {
                mScheduledShots = new LinkedList<>();
                mOffset++;
                mOffset %= mSpacing;
                this.fillShots();
            }
            d = mScheduledShots.pop();
        }
        return new Shoot(Shoot.Type.MOVE, Action.getGUID(), d.width, d.height);
    }
    
    private Dimension findNextTarget(Cell[][] g) {
        for (int x = 0; x < g.length; ++x) {
            for (int y = 0; y < g[0].length; ++y) {
                if (g[x][y] instanceof BattleShip) {
                    if (x < g.length - 1) {
                        if (!g[x + 1][y].isSunk()) {
                            return new Dimension(x + 1, y);
                        }
                    }
                    if (y < g[0].length - 1) {
                        if (!g[x][y + 1].isSunk()) {
                            return new Dimension(x, y + 1);
                        }
                    }
                    if (x > 1) {
                        if (!g[x - 1][y].isSunk()) {
                            return new Dimension(x - 1, y);
                        }
                    }
                    if (y > 1) {
                        if (!g[x][y - 1].isSunk()) {
                            return new Dimension(x, y - 1);
                        }
                    }
                }
            }
        }
        return null;
    }
    
    private void fillShots() {
        for (int i = mOffset; i < w * h; i += mSpacing) {
            mScheduledShots.add(t2d(i));
        }
    }
    
    private Dimension t2d(int n) {
        return new Dimension(n % w, n / w);
    }
    
    private int t1d(int x, int y) {
        return w * (Math.min(y - 1, 0)) + x;
    }
    
    private LinkedList<Dimension> mScheduledShots;
    private int mSpacing;
    private int mOffset;
    private int w;
    private int h;
    
}
