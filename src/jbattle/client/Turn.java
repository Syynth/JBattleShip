/**
 * Turn class represents all Actions of a given turn,
 * Both Move(s) and Result(s)and serializes/deserializes them to/from XML.
 * @author Ben
 */

package jbattle.client;

import java.util.ArrayList;

public class Turn {
    
    public Turn(String turnXML) {
        
    }
    
    public Move[] getMoves() {
        
    }
    
    public Result[] getResults() {
        
    }
    
    public void addMove(Action a) {
        
    }
    
    public void addResult(Action a) {
        
    }
    
    public Move deleteMove() {
        
    }
    
    public Result deleteResult() {
        
    }
    
    private boolean parseTurnXML(String xml) {
        
    }
    
    private String renderTurnXML() {
        
    }
    
    public String toString() {
        return renderTurnXML();
    }
    
    public int getID() {
        return mID;
    }
    
    private String turn;
    private ArrayList<Move> mMoves;
    private ArrayList<Result> mResults;
    private int mID;
    
    
}
