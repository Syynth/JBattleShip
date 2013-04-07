/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattle.client;

/**
 *
 * @author Ben
 */
public abstract class Action extends Cell {
    
    public static int getGUID() {
        return ++GUIDcounter;
    }
    
    public boolean isMove() {
        return mType == Type.MOVE;
    }
    public boolean isResult() {
        return mType == Type.RESULT;
    }
    
    public int getID() {
        return mID;
    }
    
    private static int GUIDcounter = 0;
    protected int mID;
    protected int mTurn;
    protected Type mType;
    
    protected boolean mResult;
    
    public enum Type {
        MOVE,
        RESULT
    }
}
