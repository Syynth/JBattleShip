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

    public Radar(Type type, int id, int x, int y, Direction d) {
        this(type, id, x, y, false, d);
    }
    
    public Radar(Type type, int id, int x, int y, boolean see) {
        this(type, id, x, y, see, Direction.NORTH);
    }
    
    public Radar(Type type, int id, int x, int y, boolean see, Direction d) {
        mType = type;
        mID = id;
        this.x = x;
        this.y = y;
        mResult = see;
    }

    @Override
    public boolean getResult() {
        return mResult;
    }
    
    public enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }
    
    @Override
    public String toString() {
        if (isMove()) {
            String s = mDir == Direction.NORTH ? "north" : mDir == Direction.SOUTH ? "south" : mDir == Direction.EAST ? "east" : "west";
            return "<move type=\"radar\" id=\"" + mID + "\" x=\"" + x +
                    "\" y=\"" + y + "\" direction=\"" + s + "\" />";
        } else {
            return "<result type=\"radar\" id=\"" + mID + "\" value=\"" + mResult + "\" />";
        }
    }
    
    private Direction mDir;
    
}
