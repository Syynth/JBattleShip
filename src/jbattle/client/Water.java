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
    
    @Override
    public void paintComponent(java.awt.Graphics gfx) {
        java.util.Random r = new java.util.Random();
        gfx.setColor(new java.awt.Color(r.nextFloat() * .2f,
                                        r.nextFloat() * .3f,
                                        r.nextFloat() * .2f + .8f));
        gfx.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
    
}
