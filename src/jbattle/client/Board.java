/**
 * @date Feb 8, 2013
 * @author Ben Cochrane
 */

package jbattle.client;

public class Board {
    
    public Board(int w, int h) {
        grid = new Entity[w][h];
        
    }
    
    private Entity[][] grid;
}
