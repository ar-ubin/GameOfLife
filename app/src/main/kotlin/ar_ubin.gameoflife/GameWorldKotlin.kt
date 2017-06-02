package ar_ubin.gameoflife

import java.util.*


class GameWorldKotlin(val aliveCells: HashSet<CellKotlin> = hashSetOf()) {

    fun addCell(cell: CellKotlin) = aliveCells.add(cell)

    fun getNeighbours(cell: CellKotlin): Set<CellKotlin> {
        val neighbours: HashSet<CellKotlin> = hashSetOf()

        getNeighbourhood(cell).filter { isAlive(it) }.forEach { neighbours.add(it) }
        neighbours.remove(cell)
        return neighbours
    }

    fun getNeighbourhood(cell: CellKotlin): Set<CellKotlin> {
        val neighbourhood: HashSet<CellKotlin> = hashSetOf()

        for (dx in -1..1) {
            for (dy in -1..1) {
                neighbourhood.add(CellKotlin(cell.xCoordinate + dx, cell.yCoordinate + dy))
            }
        }
        return neighbourhood
    }

    fun isAlive(cell: CellKotlin): Boolean {
        var isAlive = false

        for ((xCoordinate, yCoordinate) in aliveCells) {
            if (xCoordinate == cell.xCoordinate && yCoordinate == cell.yCoordinate) {
                isAlive = true
                break
            }
        }
        return isAlive
    }

    fun nextGeneration(): GameWorldKotlin {
        val world = GameWorldKotlin()
        val potentialCellsToReborn: HashSet<CellKotlin> = hashSetOf()

        for (cell in aliveCells) {
            if (shouldBeAliveInNextGeneration(cell)) {
                world.addCell(cell)
            }

            potentialCellsToReborn.addAll(getNeighbourhood(cell))
        }

        potentialCellsToReborn.filter { shouldBornInNextGeneration(it) }.forEach { world.addCell(it) }

        return world
    }

    private fun shouldBeAliveInNextGeneration(cell: CellKotlin) = getNeighbours(cell).size == 2 || getNeighbours(
            cell).size == 3


    private fun shouldBornInNextGeneration(cell: CellKotlin) = !isAlive(cell) && getNeighbours(cell).size == 3
}