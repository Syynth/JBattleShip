/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbattle.client;

import javax.swing.*;
import jbattle.Config;

/**
 *
 * @author Ben
 */
public class Renderer {
    
    public Renderer() {
        mWidth = Config.getInt("windowWidth");
        mHeight = Config.getInt("windowHeight");
        mWide = Config.getInt("game", "boardWidth");
        mHigh = Config.getInt("game", "boardHeight");
        
        mWindow = new JFrame();
        mWindow.setSize(mWidth, mHeight);
        mWindow.setResizable(false);
        mWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mWindow.setVisible(true);
    }
    
    public Renderer setTitle(String title) {
        if (mWindow != null) {
            mWindow.setTitle(title);
        }
        return this;
    }
    
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
        mWindow.getContentPane().setLayout(new java.awt.GridLayout(mWide, mHigh));
        for (int i = 0; i < mWide; ++i) {
            for (int j = 0; j < mHigh; ++j) {
                mWindow.getContentPane().add(cells[i][j]);
            }
        }
        mWindow.getContentPane().validate();
        return this;
    }
    
    public Renderer draw() {
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
    
    private Cell[][] mCells;
    private JFrame mWindow;
    private int mWidth, mHeight;
    private int mWide, mHigh;
    
}
