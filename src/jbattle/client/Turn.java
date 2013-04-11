/**
 * Turn class represents all Actions of a given turn,
 * Both Move(s) and Result(s)and serializes/deserializes them to/from XML.
 * @author Ben
 */

package jbattle.client;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Turn {
    
    public Turn() {
        mID = Turn.turnCount++;
        Turn.masterTurnList.add(this);
        mMoves = new ArrayList<>();
        mResults = new ArrayList<>();
    }
    
    public Turn(String turnXML) {
        mTurnSource = turnXML;
        mMoves = new ArrayList<>();
        mResults = new ArrayList<>();
        parseTurnXML(mTurnSource);
    }
    
    public Turn(String[] turnRXY) {
        mTurnSource = null;
        mID = Turn.turnCount++;
        mMoves = new ArrayList<>();
        mResults = new ArrayList<>();
        switch (turnRXY[0].toLowerCase().charAt(0)) {
            case 'h':
                mResults.add(new Shoot(Shoot.Type.RESULT, -1, getLastX(), getLastY(), true));
                break;
            case 'm':
                mResults.add(new Shoot(Shoot.Type.RESULT, -1, getLastX(), getLastY(), false));
                break;
            case 'l':
                mResults.add(new Loss());
                break;
        }
    }
    
    private int getLastX() {
        Turn t = Turn.masterTurnList.get(Turn.masterTurnList.size());
        return ((Action)t.mMoves.get(0)).x;
    }
    
    private int getLastY() {
        Turn t = Turn.masterTurnList.get(Turn.masterTurnList.size());
        return ((Action)t.mMoves.get(0)).y;
    }
    
    public Move[] getMoves() {
        Move[] m = new Move[mMoves.size()];
        for (int i = 0; i < mMoves.size(); ++i) {
            m[i] = mMoves.get(i);
        }
        return m;
    }
    
    public Result[] getResults() {
        Result[] r = new Result[mMoves.size()];
        for (int i = 0; i < mResults.size(); ++i) {
            r[i] = mResults.get(i);
        }
        return r;
    }
    
    public void addMove(Move m) {
        mMoves.add(m);
    }
    
    public void addMoves(Move[] ms) {
        mMoves.addAll(Arrays.asList(ms));
    }
    
    public void addResult(Result r) {
        mResults.add(r);
    }
    
    public void addResults(Result[] a) {
        mResults.addAll(Arrays.asList(a));
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
                                parseInt(e.attributeValue("y")),
                                parseDir(e.attributeValue("direction"))));
                        break;
                }
            }
            List resultNodes = mTurn.selectNodes("/turn/results/result");
            for (Iterator i = resultNodes.iterator(); i.hasNext();) {
                Element e = (Element)i.next();
                switch (e.attributeValue("type")) {
                    case "shoot":
                        addResult(new Shoot(Shoot.Type.RESULT,
                                parseInt(e.attributeValue("id")),
                                parseInt(e.attributeValue("x")),
                                parseInt(e.attributeValue("y")),
                                parseBool(e.attributeValue("value"))));
                        break;
                    case "radar":
                        addResult(new Radar(Radar.Type.RESULT,
                                parseInt(e.attributeValue("id")),
                                parseInt(e.attributeValue("x")),
                                parseInt(e.attributeValue("y")),
                                parseBool(e.attributeValue("value"))));
                        break;
                }
            }
            return true;
        } catch (DocumentException ex) {
            System.out.println("Couldn't read incoming XML!\n" + ex.getMessage());
            return false;
        }
    }
    
    private String renderTurnXML() {
        String xml = xmlHeader;
        String in = "";
        xml += "<turn count=\"" + Turn.turnCount + "\">\n";
        xml += in + "<moves>\n";
        for (int i = 0; i < mMoves.size(); ++i) {
        xml += in + in + mMoves.get(i).toString() + "\n";
        }
        xml += in + "</moves>\n";
        xml += in + "<results>\n";
        for (int i = 0; i < mResults.size(); ++i) {
        xml += in + in + mResults.get(i).toString() + "\n";
        }
        xml += in + "</results>\n";
        xml += "</turn>\n";
        return xml.replace("\n", "");
    }
    
    public String renderTurnString() {
        String turn = "";
        if (mResults.size() > 0) {
            if (mResults.get(0) instanceof Loss) {
                turn += "loss";
            } else {
                if (mResults.get(0).getResult()) {
                    turn += "hitt";
                } else {
                    turn += "miss";
                }
            }
        } else {
            turn += "miss";
        }
        if (mMoves.size() > 0) {
            turn += "," + ((Action)mMoves.get(0)).x + "," +
                    ((Action)mMoves.get(0)).y;
        } else {
            turn += ",0,0";
        }
        return turn;
    }
    
    @Override
    public String toString() {
        return renderTurnXML();
    }
    
    public int getID() {
        return mID;
    }
    
    public int getCount() {
        return turnCount;
    }
    
    private int parseInt(String s) {
        return Integer.parseInt(s);
    }
    
    private boolean parseBool(String s) {
        return Boolean.parseBoolean(s);
    }
    
    private Radar.Direction parseDir(String s) {
        switch (s) {
            case "north":
                return Radar.Direction.NORTH;
            case "east":
                return Radar.Direction.EAST;
            case "south":
                return Radar.Direction.SOUTH;
            case "west":
                return Radar.Direction.WEST;
            default:
                return Radar.Direction.NORTH;
        }
    }
    
    private final String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    private String mTurnSource;
    private ArrayList<Move> mMoves;
    private ArrayList<Result> mResults;
    private static int turnCount;
    private int mID;
    private Document mTurn;
    
    private static ArrayList<Turn> masterTurnList = new ArrayList<>();
    
}
