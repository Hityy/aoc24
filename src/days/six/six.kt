package days.six
import java.io.File

val guard = '^'
val obstacle = '#'
        //row to column
val up = -1 to 0
val right = 0 to 1
val down = 1 to 0
val left = 0 to -1
val nextDirs = mapOf(
    up to right,
    right to down,
    down to left,
    left to up
)

// 4382 too high
// 4374 good !

fun solveSixDayFirstStar() {
    val result =
        File("src/days/six/src1.txt")
            .readLines()
            .map { it.split("").filter { it != "" }.map { it.first()} }
            .let { countPositions(it) }

    println("Result: $result")
}


fun countPositions(grid: List<List<Char>>): Int {
    val guardInitialPosition = findGuardPosition(grid)
    if(guardInitialPosition != null) {
        val positions = traverseInGridByDir(
            grid,
            guardInitialPosition.row,
            guardInitialPosition.column,
            listOf(guardInitialPosition),
            up
        )
        return positions.distinct().size
    }
    return 0
}

fun traverseInGridByDir(grid: List<List<Char>>, row: Int, column: Int, positions: List<Pair<Int,Int>>, dir: Pair<Int,Int>): List<Pair<Int,Int>> {
    if (row !in 0..<grid.size || column !in 0..<grid[0].size)
        return positions.dropLast(1)

    if(obstacle == grid[row][column]) {
        val nextDir = nextDirs.get(dir)!!
        val nextPosition = (row + nextDir.row - dir.row) to (column + nextDir.column - dir.column)
        return traverseInGridByDir(
            grid,
            nextPosition.row,
            nextPosition.column,
            positions.dropLast(1) + nextPosition,
            nextDir
        )
    }

    val nextPosition = (row + dir.row) to (column + dir.column)
    return traverseInGridByDir(grid, nextPosition.row, nextPosition.column, positions + nextPosition, dir)
}

fun findGuardPosition(grid: List<List<Char>>): Pair<Int,Int>? {
    for(row in grid.indices) {
        for(column in grid[0].indices) {
            if(guard == grid[row][column]) {
                return row to column
            }
        }
    }
    return null
}

val <T, A> Pair<T, A>.row: T
    get() = this.first

val <T,A>Pair<T,A>.column: A
    get() = this.second
