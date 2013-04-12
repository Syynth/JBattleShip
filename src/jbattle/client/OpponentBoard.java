package jbattle.client;
/**
 * @date Apr 8, 2013
 * @author Ben Cochrane
 */
public final class OpponentBoard extends Board {

    public OpponentBoard(boolean isServer) {
        super();

        this.fillGrid();
        mRender.initGrid(mGrid).setTitle("Opponent");
        mInput = null;
        mRender.show().initGrid(mGrid).setTitle(isServer ? "Server" : "Client");
        mDead = false;
    }

    @Override
    public Move[] getMoves(Result[] rs) {
        for (Result r : rs) {
            if (r instanceof Shoot) {
                if (r.getResult()) {
                    mGrid[((Action) r).x][((Action) r).y] = (new BattleShip()).sink();
                    mRender.replaceGrid(mGrid);
                } else {
                    ((Water) mGrid[((Action) r).x][((Action) r).y]).sink();
                }
            } else if (r instanceof Loss) {
                mDead = true;
            }
        }
        return super.getMoves(rs);
    }
    
    public boolean isAlive() {
        return !mRender.isCloseRequested() && !mDead;
    }

    @Override
    protected void fillGrid() {
        for (int x = 0; x < mGrid.length; ++x) {
            for (int y = 0; y < mGrid[0].length; ++y) {
                mGrid[x][y] = new Water();
            }
        }
    }
    
    private boolean mDead;
}
