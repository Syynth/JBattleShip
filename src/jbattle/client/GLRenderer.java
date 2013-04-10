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
        try {
            Display.setDisplayMode(new DisplayMode(mWidth, mHeight));
            Display.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(GLRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public boolean isCloseRequested() {
        return Display.isCloseRequested();
    }
    
}
