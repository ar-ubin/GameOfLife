package ar_ubin.gameoflife;


class Cell
{
    public final int mXCoordinate;
    public final int mYCoordinate;

    public Cell( int xCoordinate, int yCoordinate ) {
        mXCoordinate = xCoordinate;
        mYCoordinate = yCoordinate;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;

        Cell cell = (Cell) o;

        if( mXCoordinate != cell.mXCoordinate ) return false;
        return mYCoordinate == cell.mYCoordinate;
    }

    @Override
    public int hashCode() {
        int result = mXCoordinate;
        result = 31 * result + mYCoordinate;
        return result;
    }
}
