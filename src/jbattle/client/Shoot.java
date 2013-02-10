/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattle.client;

/**
 *
 * @author Ben
 */
public class Shoot extends Action implements Move, Result {
    
    public Shoot(Type type, int x, int y) {
        this(type, x, y, false);
    }
    
    public Shoot(Type type, int x, int y, boolean hit) {
        
    }
    
}
