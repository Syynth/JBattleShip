/**
 * @date Jan 30, 2013
 * @author Ben Cochrane
 */

package jbattleserver;

public class JBattleShipServerProtocol {

    enum ProtocolState {
        WAITING_FOR_CONNECTION,
        SENT_WAIT_RESPONSE,
        SENT_ADDRESS_RESPONSE,
        QUIT
    }
    
    public JBattleShipServerProtocol() {
        mState = ProtocolState.WAITING_FOR_CONNECTION;
    }
    
    public String handleMessage(String msg) {
        switch (mState) {
            case WAITING_FOR_CONNECTION:
                if ("send".equals(msg.toLowerCase())) {
                    String s = JBattleShipServer.takeAddress();
                    if (!s.isEmpty()) {
                        mState = ProtocolState.SENT_ADDRESS_RESPONSE;
                        return "meet " + s;
                    } else {
                        mState = ProtocolState.SENT_WAIT_RESPONSE;
                        return s;
                    }
                }
                mState = ProtocolState.QUIT;
                return "invalid request";
            case SENT_WAIT_RESPONSE:
                mState = ProtocolState.QUIT;
                return "wait accepted";
            case SENT_ADDRESS_RESPONSE:
                mState = ProtocolState.QUIT;
                return "";
            case QUIT:
                return "";
        }
        return "";
    }
    
    public boolean quit() {
        return mState == ProtocolState.QUIT;
    }
    
    private ProtocolState mState;
    
}
