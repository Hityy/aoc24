package days.ten
import days.six.column
import days.six.row
import java.io.File

typealias Grid = List<List<Char>>
typealias Point = Pair<Row,Column>
typealias Row = Int
typealias Column = Int
typealias Dir = Pair<Row,Column>

val up = -1 to 0
val right = 0 to 1
val down = 1 to 0
val left = 0 to -1


val directions = listOf(up, right,down,left)
val directionsOpposite = mapOf(
    up to down,
    down to up,
    left to right,
    right to left
)
val start = '9'
val end = '0'

val nextChar = (start downTo end).zipWithNext().toMap()
val previousChar = (end .. start).zipWithNext().toMap()

fun solveTenthDayFirstStar() {
    val grid =
        File("src/days/ten/testR12.txt")
            .readLines()
            .map { formatInputToGrid(it) }

    println(grid)
    println(nextChar)
//    println(previousChar)
    val startPoints = findStarts(grid)
    println(startPoints)


    val test = startPoints.filter {
        findPathsInGrid(
            grid,
            it.row,
            it.column,
            Array(grid.size) { BooleanArray(grid[0].size) { false } },
            '8',
            null
        )
    }
    println(test)
//    val first9 = startPoints[0]
//    println(first9)
//    val testfirst9 = findPathsInGrid(grid,first9.row,first9.column,Array(grid.size) { BooleanArray(grid[0].size) { false } },'8',null)
//    println(testfirst9)
//    val s9 = startPoints[1]
//
//    val testS9 = findPathsInGrid(grid,s9.row,s9.column, Array(grid.size) { BooleanArray(grid[0].size) { false } },null)
//    println(testS9)
}

fun formatInputToGrid(input: String):  List<Char> =
    input.split("").filter { it != "" }.map { it.first()}

fun findPathsInGrid(grid: Grid,row: Row, column: Column,visited: Array<BooleanArray>,lastValue: Char, dir :Dir?): Boolean {

    // czy wyjdziemy poza grid
    if(row !in 0 until grid.size || column !in 0 until grid[0].size) {
        return false
    }

    val currentVal = grid[row][column]
    // czy wchodzimy na kropke
    if(currentVal == '.') {
        return false
    }

    println("$currentVal, $currentVal == ${nextChar[lastValue]}")
    if(currentVal != start && currentVal != nextChar[lastValue]) {
        return false
    }

    // znalezlismy sciezke
    if(currentVal == end ) {
        return true
    }

    if(visited[row][column]) {
        return false
    }

    visited[row][column] = true

    val nextValue = nextChar[currentVal]

    println("$nextValue")
    if(nextValue != null) {
            println("$currentVal row $row column $column")
//        println("$nextChar")
//        var counter = 0
        val dirs = if(dir != null) directions.filter { directionsOpposite[dir] != it } else directions

//            println("$dir ${directionsOpposite[dir]}, ${directions.filter { directionsOpposite[dir] != it }}")
        for(nextDir in dirs) {
            val (dr,dc) = nextDir
            if(findPathsInGrid(grid,row+dr,column+dc,visited,currentVal, nextDir))
                return true
        }

    }

//    val nextVal = nextChar[currentVal]
//    println(nextVal)
//    if(currentVal {
//    }

    return false
}

fun findStarts(grid: Grid): List<Point> {
    val points = mutableListOf<Point>()
    for(row in grid.indices) {
        for(column in grid[0].indices) {
            if(grid[row][column] == start) {
               points += row to column
            }
        }
    }

    return points
}