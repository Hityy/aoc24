package days.ten
import java.io.File

private typealias Grid = List<List<Char>>
private typealias Point = Pair<Row, Column>
private typealias Row = Int
private typealias Column = Int
private typealias Dir = Pair<Row, Column>

val up = -1 to 0
val right = 0 to 1
val down = 1 to 0
val left = 0 to -1


val directions = listOf(up, right, down, left)
val directionsOpposite = mapOf(
    up to down,
    down to up,
    left to right,
    right to left
)
const val start = '0'
const val end = '9'

val nextChar = (start..end).zipWithNext().toMap()
// 517 *

fun solveTenthDayFirstStar() {
    val test = getGrid().let { grid ->
        findStarts(grid).sumOf { (row, column) ->
            val visited = Array(grid.size) { BooleanArray(grid[0].size) { false } }
            findPathsInGrid(
                grid,
                row,
                column,
                visited,
                null,
                null
            ).distinct().size
        }
    }

    println(test)
}

fun solveTenthDaySecondStar() {
    val res = getGrid().let { grid ->
        findStarts(grid).sumOf { (row, column) ->
            findPathsInGrid2(
                grid,
                row,
                column,
                null,
                null
            ).size
        }
    }

    println(res)
}

fun getGrid() = File("src/days/ten/src1.txt")
    .readLines()
    .map { formatInputToGrid(it) }


fun formatInputToGrid(input: String): List<Char> =
    input.split("").filter { it != "" }.map { it.first() }

fun findPathsInGrid(
    grid: Grid,
    row: Row,
    column: Column,
    visited: Array<BooleanArray>,
    lastValue: Char?,
    dir: Dir?
): List<Point> {
    // czy wyjdziemy poza grid
    if (row !in 0 until grid.size || column !in 0 until grid[0].size) {
        return emptyList()
    }

    val currentVal = grid[row][column]
    // czy wchodzimy na kropke

    if (currentVal == '.') {
        return emptyList()
    }

    if (lastValue != null && currentVal != nextChar[lastValue]) {
        return emptyList()
    }

    // znalezlismy sciezke
    if (currentVal == end) {
        return listOf(row to column)
    }

    if (visited[row][column]) {
        return emptyList()
    }

    visited[row][column] = true
    var points = listOf<Point>()
    val dirs = if (dir != null) directions.filter { directionsOpposite[dir] != it } else directions
    for (nextDir in dirs) {
        val (dr, dc) = nextDir
        points += findPathsInGrid(grid, row + dr, column + dc, visited, currentVal, nextDir)
    }
    return points
}

fun findStarts(grid: Grid): List<Point> {
    val points = mutableListOf<Point>()
    for (row in grid.indices) {
        for (column in grid[0].indices) {
            if (grid[row][column] == start) {
                points += row to column
            }
        }
    }

    return points
}

// ---

fun findPathsInGrid2(
    grid: Grid,
    row: Row,
    column: Column,
    lastValue: Char?,
    dir: Dir?
): List<Point> {
    // czy wyjdziemy poza grid
    if (row !in 0 until grid.size || column !in 0 until grid[0].size) {
        return emptyList()
    }

    val currentVal = grid[row][column]
    // czy wchodzimy na kropke

    if (currentVal == '.') {
        return emptyList()
    }

    if (lastValue != null && currentVal != nextChar[lastValue]) {
        return emptyList()
    }

    // znalezlismy sciezke
    if (currentVal == end) {
        return listOf(row to column)
    }


    var points = listOf<Point>()
    val dirs = if (dir != null) directions.filter { directionsOpposite[dir] != it } else directions
    for (nextDir in dirs) {
        val (dr, dc) = nextDir
        points += (findPathsInGrid2(grid, row + dr, column + dc, currentVal, nextDir))
    }
    return points
}