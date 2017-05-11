package ar_ubin.gameoflife;


import java.util.HashSet;
import java.util.Set;

class GameWorld
{
    private Set<Cell> mAliveCells = new HashSet<>();

    public GameWorld() {}

    public void addCell( Cell cell ) {
        mAliveCells.add( cell );
    }

    public Set<Cell> getNeighbours( Cell cell ) {
        Set<Cell> neighbours = new HashSet<>();
        Set<Cell> neighbourhood = getNeighbourhood( cell );

        for( Cell c : neighbourhood ) {
            if( isAlive( c ) ) {
                neighbours.add( c );
            }
        }

        neighbours.remove( cell );

        return neighbours;
    }

    private Set<Cell> getNeighbourhood( Cell cell ) {
        Set<Cell> neighbourhood = new HashSet<>();

        for( int dx = -1; dx <= 1; dx++ ) {
            for( int dy = -1; dy <= 1; dy++ ) {
                Cell c = new Cell( cell.mXCoordinate + dx, cell.mYCoordinate + dy );
                neighbourhood.add( c );
            }
        }

        return neighbourhood;
    }

    public boolean isAlive( Cell cell ) {
        return mAliveCells.contains( cell );
    }

    public GameWorld nextIteration() {
        GameWorld world = new GameWorld();

        Set<Cell> potentialCellsToReborn = new HashSet<>();

        for( Cell cell : mAliveCells ) {
            if( shouldBeAliveInNextIteration( cell ) ) {
                world.addCell( cell );
            }

            potentialCellsToReborn.addAll( getNeighbourhood( cell ) );
        }

        for( Cell cell : potentialCellsToReborn ) {
            if( shouldBornInNextIteration( cell ) ) {
                world.addCell( cell );
            }
        }

        return world;
    }

    private boolean shouldBeAliveInNextIteration( Cell cell ) {
        return getNeighbours( cell ).size() == 2 || getNeighbours( cell ).size() == 3;
    }

    private boolean shouldBornInNextIteration( Cell cell ) {
        return !isAlive( cell ) && getNeighbours( cell ).size() == 3;
    }
}