package ar_ubin.gameoflife

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class GameWorldTestKotlin {

    @Test fun aSingleCellShouldHaveZeroNeighbours() {
        //given
        val world = GameWorldKotlin()
        world.addCell(CellKotlin(0, 0))

        //when
        val neighbours = world.getNeighbours(CellKotlin(0, 0))

        //then
        assertEquals(0, neighbours.size)
    }

    @Test fun twoCellsNextToEachOtherShouldBeNeighbours() {
        val firstCell = CellKotlin(0, 0)
        val secondCell = CellKotlin(0, 1)

        //given
        val world = GameWorldKotlin()
        world.addCell(firstCell)
        world.addCell(secondCell)

        //when
        val neighboursOfFirstCell = world.getNeighbours(firstCell)
        val neighboursOfSeconCell = world.getNeighbours(secondCell)

        //then
        assertCellSetEquals(hashSetOf(CellKotlin(0, 1)), neighboursOfFirstCell)
        assertCellSetEquals(hashSetOf(CellKotlin(0, 0)), neighboursOfSeconCell)
    }

    @Test fun twoCellsFarFromEachOtherShouldHaveZeroNeighbours() {
        //given
        val world = GameWorldKotlin()
        world.addCell(CellKotlin(0, 0))
        world.addCell(CellKotlin(0, 60))

        //when
        val neighbours = world.getNeighbours(CellKotlin(0, 0))

        //then
        assertEquals(0, neighbours.size)
    }

    @Test fun middleCellOf3x3BlockShouldHaveEightNeighbours() {
        //given
        val world = createWorld(3, 3)

        //when
        val neighbours = world.getNeighbours(CellKotlin(1, 1))

        //then
        assertEquals(8, neighbours.size)
    }

    fun createWorld(width: Int, height: Int): GameWorldKotlin {
        val world = GameWorldKotlin()
        for (i in 0..width - 1) {
            for (j in 0..height - 1) {
                world.addCell(CellKotlin(i, j))
            }
        }

        return world
    }

    fun assertCellSetEquals(expected: Set<CellKotlin>, actual: Set<CellKotlin>): Unit {

        if (expected.size != actual.size) {
            fail("Expected size ${expected.size} but found ${actual.size}")
        }

        val actualIterator = actual.iterator()
        val expectedIterator = expected.iterator()
        while (actualIterator.hasNext()) {
            val expectedCell = expectedIterator.next()
            val actualCell = actualIterator.next()

            assertTrue { expectedCell.equals(actualCell) }
        }
    }
}