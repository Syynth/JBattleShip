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
    
    public Shoot(Type type, int id, int x, int y) {
        this(type, id, x, y, false);
    }
    
    public Shoot(Type type, int id, int x, int y, boolean hit) {
        mType = type;
        mID = id;
        this.x = x;
        this.y = y;
        mResult = hit;
    }
    
    @Override
    public String toString() {
        if (isMove()) {
            return "<move type=\"shoot\" id=\"" + mID + "\" x=\"" + x + "\" y=\"" + y + "\" />";
        } else {
            return "<result type=\"shoot\" id=\"" + mID + "\" value=\"" + mResult + "\" />";
        }
    }

    @Override
    public boolean getResult() {
        return mResult;
    }
    
}
