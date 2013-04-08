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
    
    public PlayerBoard() {
        super();
        this.fillGrid();
        mRender.setTitle("Player").initGrid(grid);
    }
    
    /**
     *
     * @param mOpp Sets the opponent reference to help determine which moves to make.
     */
    public void setOpponent(OpponentBoard mOpp) {
        mOppBoard = mOpp;
    }
    
    @Override
    public Move[] getMoves(Result[] rs) {
        Move[] m = new Move[1];
        if (mOppBoard != null) {
            Cell[][] cells = mOppBoard.getGrid();
            for (int i = 0; i < cells.length; ++i) {
                for (int j = 0; j < cells[0].length; ++j) {
                    if (!cells[i][j].isSunk()) {
                        m[0] = new Shoot(Shoot.Type.MOVE, Action.getGUID(), i, j);
                        return m;
                    }
                }
            }
        }
        m[0] = new Shoot(Shoot.Type.MOVE, Action.getGUID(), 0, 0);
        return m;
    }
    
    @Override
    protected void fillGrid() {
        int numShips = Integer.parseInt(Config.getProperty("game", "numberOfShips"));
        int lenShips = Integer.parseInt(Config.getProperty("game", "lengthOfShips"));
        
        for (int x = 0; x < grid.length; ++x) {
            for (int y = 0; y < grid[0].length; ++y) {
                grid[x][y] = new Water();
            }
        }
        
        for (int i = 0; i < numShips; ++i) {
            int isVertical = (int)Math.round(Math.random());
            int rx, ry;
            if (isVertical == 1) { // ship IS vertical
                rx = (int)(Math.random() * grid.length);
                ry = Math.max((int)(Math.random() * grid[0].length - lenShips), 0);
            } else { // ship is not vertical
                rx = Math.max((int)(Math.random() * grid.length - lenShips), 0);
                ry = (int)(Math.random() * grid[0].length);
            }
            System.out.println("[" + rx + ", " + ry + "]");
            for (int j = 0; j < lenShips; ++j) {
                grid[rx + j * (1 - isVertical)][ry + j * isVertical] = new BattleShip();
            }
        }
    }
    
    private OpponentBoard mOppBoard;
}
