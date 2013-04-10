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
    }
    
    @Override
    public Renderer show() {
        try {
            Display.setDisplayMode(new DisplayMode(mWidth, mHeight));
            Display.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(GLRenderer.class.getName()).log(Level.SEVERE, null, ex);
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
        return Display.isCloseRequested();
    }
    
    @Override
    public void close() {
        Display.destroy();
    }
    
}
