/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattle.client;

/**
 *
 * @author Ben
 */
public abstract class Action extends Entity {
    
    public boolean isMove() {
        return mType == Type.MOVE;
    }
    public boolean isResult() {
        return mType == Type.RESULT;
    }
    
    public int getID() {
        return mGUID;
    }
    
    protected static int GUIDcounter;
    protected int mGUID;
    protected int mTurn;
    protected Type mType;
    
    public enum Type {
        MOVE,
        RESULT
    }
}
