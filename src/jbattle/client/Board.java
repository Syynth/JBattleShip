/**
 * @date Feb 8, 2013
 * @author Ben Cochrane
 */

package jbattle.client;

public class Board {
    
    public Board(Input i, int w, int h) {
        grid = new Entity[w][h];
        
    }
    
    public Result[] getResults(Move[] m) {
        return new Result[10];
    }
    
    public Move[] getMoves(Result[] r) {
        return new Move[10];
    }
    
    public void Update() {
        
    }
    
    public void Render() {
        
    }
                            
    
    private Entity[][] grid;
}
