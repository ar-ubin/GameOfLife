package ar_ubin.gameoflife;


import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class GameWorldTest
{
    protected static GameWorld createWorld( int width, int height ) {
        GameWorld world = new GameWorld();
        for( int i = 0; i < width; i++ ) {
            for( int j = 0; j < height; j++ ) {
                world.addCell( new Cell( i, j ) );
            }
        }

        return world;
    }

    @Test
    public void aSingleCellShouldHaveZeroNeighbours() throws Exception {
        //given
        GameWorld world = new GameWorld();
        world.addCell( new Cell( 0, 0 ) );

        //when
        Set<Cell> neighbours = world.getNeighbours( new Cell( 0, 0 ) );

        //then
        assertEquals( 0, neighbours.size() );
    }

    @Test
    public void twoCellsNextToEachOtherShouldBeNeighbours() throws Exception {
        //given
        GameWorld world = new GameWorld();
        world.addCell( new Cell( 0, 0 ) );
        world.addCell( new Cell( 0, 1 ) );

        //when
        Set<Cell> neighboursOfFirstCell = world.getNeighbours( new Cell( 0, 0 ) );
        Set<Cell> neighboursOfSecondCell = world.getNeighbours( new Cell( 0, 1 ) );

        //then
        assertArrayEquals( new Cell[]{ new Cell( 0, 1 ) }, neighboursOfFirstCell.toArray() );
        assertArrayEquals( new Cell[]{ new Cell( 0, 0 ) }, neighboursOfSecondCell.toArray() );
    }

    @Test
    public void twoCellsFarFromEachOtherShouldHaveZeroNeighbours() throws Exception {
        //given
        GameWorld world = new GameWorld();
        world.addCell( new Cell( 0, 0 ) );
        world.addCell( new Cell( 0, 60 ) );

        //when
        Set<Cell> neighbours = world.getNeighbours( new Cell( 0, 0 ) );

        //then
        assertEquals( 0, neighbours.size() );
    }

    @Test
    public void middleCellsOf3x3BlockShouldHaveEightNeighbours() throws Exception {
        //given
        GameWorld world = createWorld( 3, 3 );

        //when
        Set<Cell> neighbours = world.getNeighbours( new Cell( 1, 1 ) );

        //then
        assertEquals( 8, neighbours.size() );
    }

    @Test
    public void middleCellsOf9x9BlockShouldHaveEightNeighbours() throws Exception {
        //given
        GameWorld world = createWorld( 9, 9 );

        //when
        Set<Cell> neighbours = world.getNeighbours( new Cell( 1, 1 ) );

        //then
        assertEquals( 8, neighbours.size() );
    }
}
