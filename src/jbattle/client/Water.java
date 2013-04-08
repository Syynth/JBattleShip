/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattle.client;

/**
 *
 * @author Ben
 */
public class Water extends Cell {
    
    public Water() {
        mSunk = false;
    }
    
    @Override
    public boolean isSunk() {
        return mSunk;
    }
    
    @Override
    public void paintComponent(java.awt.Graphics gfx) {
        java.util.Random r = new java.util.Random();
        gfx.setColor(new java.awt.Color(r.nextFloat() * .2f,
                                        r.nextFloat() * .3f,
                                        r.nextFloat() * .2f + .8f));
        if (mSunk) {
            gfx.setColor(new java.awt.Color(r.nextFloat() * .1f,
                                        r.nextFloat() * .15f,
                                        r.nextFloat() * .1f + .4f));
        }
        gfx.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
    
    public void sink() {
        mSunk = true;
    }
    
    private boolean mSunk;
    
}
