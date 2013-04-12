/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jbattle.client;

import jbattle.Config;

/**
 * @date Apr 8, 2013
 * @author Ben Cochrane
 */
public final class PlayerBoard extends Board {
    
    public PlayerBoard(boolean isServer) {
        super();
        this.fillGrid();
        mRender.initGrid(mGrid).setTitle(isServer ? "Server" : "Client");
        if (!Config.getBoolean("fancyGraphics")) {
            mRender.show();
        }
    }
        
    public boolean isAlive() {
        if (mRender.isCloseRequested()) {
            return false;
        }
        for (int x = 0; x < mGrid.length; ++x) {
            for (int y = 0; y < mGrid[0].length; ++y) {
                if (mGrid[x][y] instanceof BattleShip && !mGrid[x][y].isSunk()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public Move[] getMoves(Result[] rs) {
        Move[] m = new Move[1];
        m[0] = mInput.getNextMove(mOppBoard.getGrid());
        return m;
    }
    
    @Override
    protected void fillGrid() {
        int numShips = Config.getInt("game", "numberOfShips");
        int lenShips = Config.getInt("game", "lengthOfShips");
        
        for (int x = 0; x < mGrid.length; ++x) {
            for (int y = 0; y < mGrid[0].length; ++y) {
                mGrid[x][y] = new Water();
            }
        }
        
        for (int i = 0; i < numShips; ++i) {
            int isVertical = (int)Math.round(Math.random());
            int rx, ry;
            if (isVertical == 1) { // ship IS vertical
                rx = (int)(Math.random() * mGrid.length);
                ry = Math.max((int)(Math.random() * mGrid[0].length - lenShips), 0);
            } else { // ship is not vertical
                rx = Math.max((int)(Math.random() * mGrid.length - lenShips), 0);
                ry = (int)(Math.random() * mGrid[0].length);
            }
            System.out.println("[" + rx + ", " + ry + "]");
            for (int j = 0; j < lenShips; ++j) {
                mGrid[rx + j * (1 - isVertical)][ry + j * isVertical] = new BattleShip();
            }
        }
    }
}
