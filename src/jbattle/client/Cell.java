/**
 * @date Feb 8, 2013
 * @author Ben Cochrane
 */

package jbattle.client;

import javax.swing.JComponent;

public abstract class Cell extends JComponent {
    
    public int x;
    public int y;
    
    public boolean isSunk() {
        return false;
    }
    
    public void update() {
        
    }
    
}
