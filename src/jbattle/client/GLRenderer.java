/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jbattle.client;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * @date Apr 10, 2013
 * @author Ben Cochrane
 */
public class GLRenderer extends Renderer {
    
    public GLRenderer() {
        super();
        mShown = false;
    }
    
    @Override
    public Renderer show() {
        if (mCells != null) {
            try {
                Display.setDisplayMode(new DisplayMode(mWidth, mHeight));
                Display.create();
                mShown = true;
            } catch (LWJGLException ex) {
                Logger.getLogger(GLRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this;
    }
    
    @Override
    public Renderer setTitle(String title) {
        Display.setTitle(title);
        return this;
    }
    
    @Override
    public Renderer draw() {
        Display.update();
        return this;
    }
    
    @Override
    public Renderer initGrid(Cell[][] grid) {
        mCells = grid;
        return this;
    }
    
    @Override
    public Renderer replaceGrid(Cell[][] grid) {
        return initGrid(grid);
    }
    
    @Override
    public boolean isCloseRequested() {
        return mShown ? Display.isCloseRequested() : false;
    }
    
    @Override
    public void close() {
        if (mShown) {
            Display.destroy();
        }
    }
    
    private boolean mShown;
}
