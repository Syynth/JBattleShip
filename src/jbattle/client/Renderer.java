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
        mWidth = Integer.parseInt(Config.getProperty("windowWidth"));
        mHeight = Integer.parseInt(Config.getProperty("windowHeight"));
        mWide = Integer.parseInt(Config.getProperty("game", "boardWidth"));
        mHigh = Integer.parseInt(Config.getProperty("game", "boardHeight"));
        
        mWindow = new JFrame();
        mWindow.setSize(mWidth, mHeight);
        mWindow.setResizable(false);
        mWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mWindow.setVisible(true);
    }
    
    /**
     * Replaces the grid, and updates the UI
     * @param grid The array of Cells to makeup the new UI
     */
    public void replaceGrid(Cell[][] grid) {
        mWindow.removeAll();
        this.initGrid(grid);
    }
    
    public void initGrid(Cell[][] cells) {
        mCells = cells;
        mWindow.getContentPane().setLayout(new java.awt.GridLayout(mWide, mHigh));
        for (int i = 0; i < mWide; ++i) {
            for (int j = 0; j < mHigh; ++j) {
                mWindow.getContentPane().add(cells[i][j]);
            }
        }
        mWindow.getContentPane().validate();
    }
    
    public void draw() {
        for (int i = 0; i < mWide; ++i) {
            for (int j = 0; j < mHigh; ++j) {
                if (mCells[i][j] != null) {
                    mCells[i][j].update();
                    mCells[i][j].repaint();
                }
            }
        }
        mWindow.getContentPane().validate();
    }
    
    private Cell[][] mCells;
    private JFrame mWindow;
    private int mWidth, mHeight;
    private int mWide, mHigh;
    
}
