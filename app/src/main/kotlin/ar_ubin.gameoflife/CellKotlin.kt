package ar_ubin.gameoflife


data class CellKotlin(val xCoordinate: Int, val yCoordinate: Int) {

    override fun equals(other: Any?): Boolean {
        val cell: CellKotlin = other as CellKotlin

        if (xCoordinate != cell.xCoordinate) return false
        return yCoordinate == cell.yCoordinate
    }
}