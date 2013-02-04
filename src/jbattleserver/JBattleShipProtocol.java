/**
 * @date Jan 30, 2013
 * @author Ben Cochrane
 */

package jbattleserver;

public class JBattleShipProtocol {

    enum ProtocolState {
        SERV_WAITING_FOR_CONNECTION,
        SERV_SENT_WAIT_RESPONSE,
        SERV_SENT_ADDRESS_RESPONSE,
        QUIT,
        CLI_BEGIN,
        CLI_RESPONSE_INSTRUCTION
    }
    
    public JBattleShipProtocol(int mode) {
        this.mode = mode;
        if (mode == SERVER) {
            mState = ProtocolState.SERV_WAITING_FOR_CONNECTION;
        } else if (mode == CLIENT) {
            mState = ProtocolState.CLI_BEGIN;
        }
    }
    
    public String handleMessage(String msg) {
        System.out.println("Handling message: " + msg);
        switch (mState) {
            case SERV_WAITING_FOR_CONNECTION:
                if ("send".equals(msg.toLowerCase())) {
                    String s = JBattleShipServer.takeAddress();
                    System.out.println(s);
                    if (!"".equals(s)) {
                        mState = ProtocolState.SERV_SENT_ADDRESS_RESPONSE;
                        return "meet " + s;
                    } else {
                        mState = ProtocolState.SERV_SENT_WAIT_RESPONSE;
                        return "wait";
                    }
                }
                System.out.println("Oops!");
                mState = ProtocolState.QUIT;
                return "invalid request";
            case SERV_SENT_WAIT_RESPONSE:
                mState = ProtocolState.QUIT;
                if ("accept wait".equals(msg.toLowerCase().substring(0, 11))) {
                    return "wait accepted" + msg.substring(11);
                } else {
                    return "wait failed";
                }
            case SERV_SENT_ADDRESS_RESPONSE:
                mState = ProtocolState.QUIT;
                if ("accept meet".equals(msg.toLowerCase())) {
                    return "meet accepted";
                } else {
                    return "meet failed";
                }            
            case CLI_BEGIN:
                mState = ProtocolState.CLI_RESPONSE_INSTRUCTION;
                return "send";
            case CLI_RESPONSE_INSTRUCTION:
                mState = ProtocolState.QUIT;
                if ("wait".equals(msg)) {
                    return "accept wait";
                }
                if ("meet".equals(msg.substring(0, 4))) {
                    return "accept meet";
                }
                return "qwerasdf";
            case QUIT:
                return "";
        }
        return "";
    }
    
    public boolean quit() {
        return mState == ProtocolState.QUIT;
    }
    
    private ProtocolState mState;
    private int mode;
    public static final int SERVER = 0;
    public static final int CLIENT = 1;
    
}
