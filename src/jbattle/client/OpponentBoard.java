package jbattle.client;

/**
 * @date Apr 8, 2013
 * @author Ben Cochrane
 */
public final class OpponentBoard extends Board {

    public OpponentBoard() {
        super();

        this.fillGrid();
        mRender.initGrid(grid);
        mInput = null;
    }

    @Override
    public Result[] getResults(Move[] m) {
        Result[] rs = super.getResults(m);
        for (Result r : rs) {
            if (r instanceof Shoot) {
                if (r.getResult()) {
                    grid[((Action) r).x][((Action) r).y] = (new BattleShip()).sink();
                    mRender.replaceGrid(grid);
                } else {
                    ((Water) grid[((Action) r).x][((Action) r).y]).sink();
                }
            }
        }
        return rs;
    }

    @Override
    protected void fillGrid() {
        for (int x = 0; x < grid.length; ++x) {
            for (int y = 0; y < grid[0].length; ++y) {
                grid[x][y] = new Water();
            }
        }
    }
}
