/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattle.client;

/**
 *
 * @author Ben
 */
public class BattleShip extends Entity {
    
    public BattleShip(int x, int y, int length) {
        this(x, y, length, true);
    }
    
    public BattleShip(int x, int y, int length, boolean horizontal) {
        this.x = x;
        this.y = y;
        mLength = length;
        mHP = length;
        mHorizontal = horizontal;
    }
    
    public void takeDamage(int damage) {
        mHP -= damage;
    }
    
    public boolean isAlive() {
        return mHP > 0;
    }
    
    public int length() {
        return mLength;
    }
    
    public boolean isHorizontal() {
        return mHorizontal;
    }
    
    public boolean isVertical() {
        return !mHorizontal;
    }
    
    private boolean mHorizontal;
    private int mHP;
    private int mLength;
    
}
