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
    
    public JBattleShipProtocol() {
        mState = ProtocolState.SERV_WAITING_FOR_CONNECTION;
    }
    
    public String handleMessage(String msg) {
        System.out.println("Handling message: " + msg);
        switch (mState) {
            case SERV_WAITING_FOR_CONNECTION:
                if ("send".equals(msg.toLowerCase())) {
                    String s = JBattleShipServer.takeAddress();
                    if (!s.isEmpty()) {
                        mState = ProtocolState.SERV_SENT_ADDRESS_RESPONSE;
                        return "meet " + s;
                    } else {
                        mState = ProtocolState.SERV_SENT_WAIT_RESPONSE;
                        return s;
                    }
                }
                mState = ProtocolState.QUIT;
                return "invalid request";
            case SERV_SENT_WAIT_RESPONSE:
                mState = ProtocolState.QUIT;
                return "wait accepted";
            case SERV_SENT_ADDRESS_RESPONSE:
                mState = ProtocolState.QUIT;
                return "";
            case QUIT:
                return "";
            case CLI_BEGIN:
                mState = ProtocolState.CLI_WAIT_INSTRUCTION;
                return "send";
            case CLI_WAIT_INSTRUCTION:
                return "";
        }
        return "";
    }
    
    public boolean quit() {
        return mState == ProtocolState.QUIT;
    }
    
    private ProtocolState mState;
    public static final int SERVER = 0;
    public static final int CLIENT = 1;
    
}
