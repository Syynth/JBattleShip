/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattle.client;

/**
 *
 * @author Ben
 */
public class Radar extends Action implements Move, Result {

    public Radar(Type type, int id, int x, int y) {
        this(type, id, x, y, false);
    }
    
    public Radar(Type type, int id, int x, int y, boolean see) {
        mType = type;
        mID = id;
        this.x = x;
        this.y = y;
        mResult = see;
    }
    
}
