package ar_ubin.gameoflife;


import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class GameOfLifeTest
{
    @Test
    public void aSingleCellShouldDieInNextIteration() throws Exception {
        //given
        GameWorld world = new GameWorld();
        world.addCell( new Cell( 0, 0 ) );
        GameOfLife game = new GameOfLife( world );

        //when
        GameWorld nextWorld = game.nextGeneration();

        //then
        assertFalse( nextWorld.isAlive( new Cell( 0, 0 ) ) );
    }

    @Test
    public void aCellWithTwoNeighboursShouldLiveInNextIteration() {
        //given
        GameWorld world = new GameWorld();
        world.addCell( new Cell( 0, 1 ) );
        world.addCell( new Cell( 1, 1 ) );
        world.addCell( new Cell( 1, 0 ) );
        GameOfLife game = new GameOfLife( world );

        //when
        GameWorld nextWorld = game.nextGeneration();

        //then
        assertTrue( nextWorld.isAlive( new Cell( 1, 1 ) ) );
    }

    @Test
    public void aCellWithThreeNeighboursShouldLiveInNextIteration() {
        //given
        GameWorld world = new GameWorld();
        world.addCell( new Cell( 0, 1 ) );
        world.addCell( new Cell( 1, 1 ) );
        world.addCell( new Cell( 1, 0 ) );
        world.addCell( new Cell( 0, 0 ) );
        GameOfLife game = new GameOfLife( world );

        //when
        GameWorld nextWorld = game.nextGeneration();

        //then
        assertTrue( nextWorld.isAlive( new Cell( 1, 1 ) ) );
    }

    @Test
    public void aCellWithMoreThanThreeNeighboursShouldDieInNextIteration() {
        //given
        GameOfLife game = new GameOfLife( GameWorldTest.createWorld( 3, 3 ) );

        //when
        GameWorld nextWorld = game.nextGeneration();

        //then
        assertFalse( nextWorld.isAlive( new Cell( 1, 1 ) ) );
    }

    @Test
    public void aDeadCellWithThreeNeighboursShouldBeRebornInNextIteration() {
        //given
        GameWorld world = new GameWorld();
        world.addCell( new Cell( 0, 1 ) );
        world.addCell( new Cell( 1, 1 ) );
        world.addCell( new Cell( 1, 0 ) );
        GameOfLife game = new GameOfLife( world );

        //when
        GameWorld nextWorld = game.nextGeneration();

        //then
        assertTrue( nextWorld.isAlive( new Cell( 0, 0 ) ) );
    }
}
