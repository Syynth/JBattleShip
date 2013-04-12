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
        mSpacing = Config.getInt("game", "lengthOfShips");
        mOffset = 0;
        w = Config.getInt("game", "boardWidth");
        h = Config.getInt("game", "boardHeight");
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
                    if (x < g.length) {
                        if (!g[x + 1][y].isSunk()) {
                            return new Dimension(x + 1, y);
                        }
                    }
                    if (y < g[0].length) {
                        if (!g[x][y + 1].isSunk()) {
                            return new Dimension(x, y + 1);
                        }
                    }
                    if (x > 0) {
                        if (!g[x - 1][y].isSunk()) {
                            return new Dimension(x - 1, y);
                        }
                    }
                    if (y > 0) {
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
        for (int y = 0; y < Config.getInt("game", "boardHeight"); ++y) {
            for (int x = y % mSpacing; x < Config.getInt("game", "boardWidth"); x += mSpacing) {
                mScheduledShots.add(new Dimension(x, y));
            }
        }
    }
    
    private LinkedList<Dimension> mScheduledShots;
    private int mSpacing;
    private int mOffset;
    private int w;
    private int h;
    
}