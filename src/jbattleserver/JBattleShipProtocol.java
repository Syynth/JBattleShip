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
        CLI_WAIT_INSTRUCTION
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
                    if (!"".equals(s)) {
                        mState = ProtocolState.SERV_SENT_ADDRESS_RESPONSE;
                        return "meet " + s;
                    } else {
                        mState = ProtocolState.SERV_SENT_WAIT_RESPONSE;
                        return "wait";
                    }
                }
                mState = ProtocolState.QUIT;
                return "invalid request";
            case SERV_SENT_WAIT_RESPONSE:
                mState = ProtocolState.QUIT;
                if ("accept wait".equals(msg.toLowerCase())) {
                    return "wait accepted";
                } else {
                    return "wait failed";
                }
            case SERV_SENT_ADDRESS_RESPONSE:
                mState = ProtocolState.QUIT;
                return "";
            case QUIT:
                return "";
            case CLI_BEGIN:
                mState = ProtocolState.CLI_WAIT_INSTRUCTION;
                return "send";
            case CLI_WAIT_INSTRUCTION:
                if ("wait".equals(msg)) {
                    mState = ProtocolState.QUIT;
                    return "accept wait";
                }
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
