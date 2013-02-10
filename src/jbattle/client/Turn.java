/**
 * Turn class represents all Actions of a given turn,
 * Both Move(s) and Result(s)and serializes/deserializes them to/from XML.
 * @author Ben
 */

package jbattle.client;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Turn {
    
    public Turn() {
        mID = Turn.turnCount;
        Turn.turnCount++;
        mMoves = new ArrayList<>();
        mResults = new ArrayList<>();
    }
    
    public Turn(String turnXML) {
        mTurnSource = turnXML;
        mMoves = new ArrayList<>();
        mResults = new ArrayList<>();
        parseTurnXML(mTurnSource);
    }
    
    public Move[] getMoves() {
        Move[] m = null;
        m = mMoves.toArray(m);
        return m;
    }
    
    public Result[] getResults() {
        Result[] r = null;
        r = mResults.toArray(r);
        return r;
    }
    
    public void addMove(Action a) {
        if (a.isMove()) {
            mMoves.add((Move)a);
        } else {
            System.out.println("Tried to add Result (" + a + ") to Move list in Turn " + mID);
        }
    }
    
    public void addResult(Action a) {
        if (a.isResult()) {
            mResults.add((Result)a);
        } else {
            System.out.println("Tried to add Move (" + a + ") to Result list in Turn " + mID);
        }
    }
    
    public Move deleteMove(int id) {
        for (int i = 0; i < mMoves.size(); ++i) {
            if (((Action)mMoves.get(i)).getID() == id) {
                return mMoves.remove(i);
            }
        }
        System.out.println("Couldn't find Move " + id + " to remove!");
        return null;
    }
    
    public Result deleteResult(int id) {
        for (int i = 0; i < mResults.size(); ++i) {
            if (((Action)mResults.get(i)).getID() == id) {
                return mResults.remove(i);
            }
        }
        System.out.println("Couldn't find Result " + id + " to remove!");
        return null;
    }
    
    private boolean parseTurnXML(String xml) {
        try {
            SAXReader r = new SAXReader();
            mTurn = r.read(new StringReader(xml));
            mID = parseInt(mTurn.getRootElement().attributeValue("count"));
            Turn.turnCount = mID + 1;
            List moveNodes = mTurn.selectNodes("/turn/moves/move");
            for (Iterator i = moveNodes.iterator(); i.hasNext();) {
                Element e = (Element)i.next();
                switch (e.attributeValue("type")) {
                    case "shoot":
                        addMove(new Shoot(Shoot.Type.MOVE,
                                parseInt(e.attributeValue("id")),
                                parseInt(e.attributeValue("x")),
                                parseInt(e.attributeValue("y"))));
                        break;
                    case "radar":
                        addMove(new Radar(Radar.Type.MOVE,
                                parseInt(e.attributeValue("id")),
                                parseInt(e.attributeValue("x")),
                                parseInt(e.attributeValue("y"))));
                        break;
                }
            }
            List resultNodes = mTurn.selectNodes("/turn/moves/move");
            for (Iterator i = resultNodes.iterator(); i.hasNext();) {
                Element e = (Element)i.next();
                switch (e.attributeValue("type")) {
                    case "shoot":
                        addResult(new Shoot(Shoot.Type.RESULT,
                                parseInt(e.attributeValue("id")),
                                parseInt(e.attributeValue("x")),
                                parseInt(e.attributeValue("y")),
                                e.attributeValue("value").equals("hit")));
                        break;
                    case "radar":
                        addResult(new Radar(Radar.Type.RESULT,
                                parseInt(e.attributeValue("id")),
                                parseInt(e.attributeValue("x")),
                                parseInt(e.attributeValue("y")),
                                e.attributeValue("value").equals("ship")));
                        break;
                }
            }
            return true;
        } catch (DocumentException ex) {
            System.out.println("Couldn't read incoming XML!");
            return false;
        }
    }
    
    private String renderTurnXML() {
        return "";
    }
    
    @Override
    public String toString() {
        return renderTurnXML();
    }
    
    public int getID() {
        return mID;
    }
    
    private int parseInt(String s) {
        return Integer.parseInt(s);
    }
    
    private String mTurnSource;
    private ArrayList<Move> mMoves;
    private ArrayList<Result> mResults;
    private static int turnCount;
    private int mID;
    private Document mTurn;
    
    
}
