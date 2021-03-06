package jbattle.client;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

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
        if (mCells != null) {
            try {
                Display.setDisplayMode(new DisplayMode(mWidth * 2 + 4, mHeight + 2));
                Display.create();
                
                GL11.glMatrixMode(GL11.GL_PROJECTION);
                GL11.glLoadIdentity();
                GL11.glOrtho(0, mWidth, 0, mHeight, 1, -1);
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                
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
    public Renderer draw(Cell[][] oGrid) {
        if (mShown) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            float w = (float)Math.ceil(mWidth / mWide);
            float h = (float)Math.ceil(mHeight / mHigh);
            for (int x = 0; x < mCells.length; ++x) {
                for (int y = 0; y < mCells[0].length; ++y) {
                    if (mCells[x][y].isSunk()) {
                        if (mCells[x][y] instanceof Water) {
                            GL11.glColor3f(.18f + r() * .02f,
                                    .27f + r() * .3f,
                                    (float)Math.abs(Math.sin(System.currentTimeMillis() / 300 + Math.sin(x + y) + y)) * .2f + .8f);
                        } else {
                            GL11.glColor3f(.8f, .1f, .3f);
                        }
                    } else {
                        if (mCells[x][y] instanceof Water) {
                            GL11.glColor3f(.1f, r() * .15f, r() * .005f + .495f);
                        } else {
                            GL11.glColor3f(.4f, .05f, .15f);
                        }
                    }
                    float wx = w * x / 2, wy = w * y;
                    GL11.glBegin(GL11.GL_QUADS);
                        GL11.glVertex2f(1 + wx, 1 + wy);
                        GL11.glVertex2f(1 + wx, 1 + wy + h);
                        GL11.glVertex2f(1 + wx + w, 1 + wy + h);
                        GL11.glVertex2f(1 + wx + w, 1 + wy);
                    GL11.glEnd();
                }
            }
            
            for (int x = 0; x < oGrid.length; ++x) {
                for (int y = 0; y < oGrid[0].length; ++y) {
                    if (!oGrid[x][y].isSunk()) {
                        if (oGrid[x][y] instanceof Water) {
                            GL11.glColor3f(r() * .2f,
                                    r() * .3f,
                                    (float)Math.abs(Math.sin(System.currentTimeMillis() / 300 + Math.sin(x + y) + y)) * .2f + .8f);
                        } else {
                            GL11.glColor3f(.8f, .1f, .3f);
                        }
                    } else {
                        if (oGrid[x][y] instanceof Water) {
                            GL11.glColor3f(r() * .1f, r() * .15f, r() * .1f + .4f);
                        } else {
                            GL11.glColor3f(.4f, .05f, .15f);
                        }
                    }
                    float wx = w * x / 2 + mWidth / 2, wy = w * y;
                    GL11.glBegin(GL11.GL_QUADS);
                        GL11.glVertex2f(wx, wy);
                        GL11.glVertex2f(wx, wy + h);
                        GL11.glVertex2f(wx + w, wy + h);
                        GL11.glVertex2f(wx + w, wy);
                    GL11.glEnd();
                }
            }
            
            Display.update();
        }
        return this;
    }
    
    private float r() {
        return (float)Math.random();
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
}
