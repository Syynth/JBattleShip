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
        Dimension d;
        if (mScheduledShots.size() <= 0) {
            mScheduledShots = new LinkedList<>();
            mOffset++;
            mOffset %= mSpacing;
            this.fillShots();
        }
        d = mScheduledShots.pop();
        return new Shoot(Shoot.Type.MOVE, Action.getGUID(), d.width, d.height);
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
