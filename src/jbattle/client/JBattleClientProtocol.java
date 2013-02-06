package jbattle.client;

/**
 *
 * @author Ben Cochrane
 */
public class JBattleClientProtocol {
    enum ProtocolState {
        S_WAIT_FOR_CONNECTION,
        C_ATTEMPT_CONNECTION,
    }
    
    public JBattleClientProtocol(int mode) {
        mMode = mode;
        if (mode == SERVER) {
            mState = ProtocolState.S_WAIT_FOR_CONNECTION;
        } else if (mode == CLIENT) {
            mState = ProtocolState.C_ATTEMPT_CONNECTION;
        }
    }
    
    private int mMode;
    private ProtocolState mState;
    
    public static final int CLIENT = 0;
    public static final int SERVER = 1;
}
