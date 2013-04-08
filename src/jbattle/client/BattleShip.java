/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattle.client;

/**
 *
 * @author Ben
 */
public class BattleShip extends Cell {
    
    public BattleShip() {
        mSunk = false;
    }
    
    public BattleShip sink() {
        mSunk = true;
        return this;
    }
    
    @Override
    public boolean isSunk() {
        return mSunk;
    }
    
    @Override
    public void paintComponent(java.awt.Graphics gfx) {
        gfx.setColor(new java.awt.Color(0.8f, 0.1f, 0.3f));
        if (mSunk) {
            gfx.setColor(new java.awt.Color(0.4f, 0.1f, 0.3f));
        }
        gfx.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
    
    private boolean mSunk;
}
