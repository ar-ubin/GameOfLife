package ar_ubin.gameoflife

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameOfLifeTestKotlin {

    @Test fun aSingleCellShouldDieInNextGeneration() {
        //given
        val world = GameWorldKotlin()
        world.addCell(CellKotlin(0, 0))
        val game = GameOfLifeKotlin(world)

        //when
        val nextWorld = game.nextGeneration()

        //then
        assertFalse { nextWorld.isAlive(CellKotlin(0, 0)) }
    }

    @Test fun aCellWithTwoNeighboursLiveInNextGeneration() {
        //given
        val world = GameWorldKotlin()
        world.addCell(CellKotlin(0, 1))
        world.addCell(CellKotlin(1, 1))
        world.addCell(CellKotlin(1, 0))
        val game = GameOfLifeKotlin(world)

        //when
        val nextWorld = game.nextGeneration()

        //then
        assertTrue { nextWorld.isAlive(CellKotlin(1, 1)) }
    }

    @Test fun aCellWithThreeNeighboursShouldLiveInNextGeneration() {
        //given
        val world = GameWorldKotlin()
        world.addCell(CellKotlin(0, 1))
        world.addCell(CellKotlin(1, 1))
        world.addCell(CellKotlin(1, 0))
        world.addCell(CellKotlin(0, 0))
        val game = GameOfLifeKotlin(world)

        //when
        val nextWorld = game.nextGeneration()

        //then
        assertTrue { nextWorld.isAlive(CellKotlin(1, 1)) }
    }

    @Test fun aCellWithMoreThanThreeNeightboursShouldDieInNextGeneration() {
        //given
        val game = GameOfLifeKotlin(GameWorldTestKotlin().createWorld(3, 3))

        //when
        val nextWorld = game.nextGeneration()

        //then
        assertFalse { nextWorld.isAlive(CellKotlin(1, 1)) }
    }

    @Test fun aDeadCellWithThreeNeightboursShouldBeRebornInNextGeneration() {
        //given
        val world = GameWorldKotlin()
        world.addCell(CellKotlin(1, 0))
        world.addCell(CellKotlin(1, 1))
        world.addCell(CellKotlin(0, 1))
        val game = GameOfLifeKotlin(world)

        //when
        val nextWorld = game.nextGeneration()

        //then
        assertTrue { nextWorld.isAlive(CellKotlin(0, 0)) }
    }
}