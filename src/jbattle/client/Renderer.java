/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattle.client;

import javax.swing.*;
import jbattle.Config;

/**
 * Provides a default implementation of providing a graphical view
 * of the game world.
 * @author Ben
 */
public class Renderer {
    
    public Renderer() {
        mWidth = Config.getInt("windowWidth");
        mHeight = Config.getInt("windowHeight");
        mWide = Config.getInt("game", "boardWidth");
        mHigh = Config.getInt("game", "boardHeight");
        mShown = false;
    }
    
    public Renderer show() {
        if (mShown) {
            return this;
        }
        mWindow = new JFrame();
        mWindow.setSize(mWidth, mHeight);
        mWindow.setResizable(false);
        mWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mWindow.setVisible(true);
        mShown = true;
        return this;
    }
    
    public Renderer setTitle(String title) {
        if (mWindow != null) {
            mWindow.setTitle(title);
        }
        return this;
    }
    
    public boolean isCloseRequested() {
        return false;
    }
    
    public void close() { }
    
    /**
     * Replaces the grid, and updates the UI
     * @param grid The array of Cells to makeup the new UI
     */
    public Renderer replaceGrid(Cell[][] grid) {
        mWindow.getContentPane().removeAll();
        this.initGrid(grid);
        return this;
    }
    
    public Renderer initGrid(Cell[][] cells) {
        mCells = cells;
        if (!mShown)
            show();
        mWindow.getContentPane().setLayout(new java.awt.GridLayout(mWide, mHigh));
        for (int i = 0; i < mWide; ++i) {
            for (int j = 0; j < mHigh; ++j) {
                mWindow.getContentPane().add(cells[i][j]);
            }
        }
        mWindow.getContentPane().validate();
        return this;
    }
    
    public Renderer draw(Cell[][] pGrid) {
        for (int i = 0; i < mWide; ++i) {
            for (int j = 0; j < mHigh; ++j) {
                if (mCells[i][j] != null) {
                    mCells[i][j].update();
                    mCells[i][j].repaint();
                }
            }
        }
        mWindow.getContentPane().validate();
        return this;
    }
    
    protected Cell[][] mCells;
    private JFrame mWindow;
    protected int mWidth, mHeight;
    protected int mWide, mHigh;
    protected boolean mShown;
}
