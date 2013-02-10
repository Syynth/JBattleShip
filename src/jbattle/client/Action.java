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
    
    protected int mGUID;
    protected int mTurn;
    
    public static final int MOVE = 0;
    public static final int RESULT = 1;
}
