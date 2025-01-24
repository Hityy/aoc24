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
//val previousChar = (end .. start).zipWithNext().toMap()

fun solveTenthDayFirstStar() {
    val result =
        File("src/days/ten/testR2.txt")
            .readLines()
            .map { formatInputToGrid(it) }

    println(result)
    println(nextChar)
//    println(previousChar)
    val startPoints = findStarts(result)
    println(startPoints)


    if(startPoint != null) {
        val test = findPathsInGrid(result,startPoint.row,startPoint.column, null)
        println(test)
    }

}

fun formatInputToGrid(input: String):  List<Char> =
    input.split("").filter { it != "" }.map { it.first()}

fun findPathsInGrid(grid: Grid,row: Row, column: Column,dir: Dir?): Int {

    // czy wyjdziemy poza grid
    if(row !in 0 until grid.size || column !in 0 until grid[0].size) {
        return 0
    }

    val currentVal = grid[row][column]
    // czy wchodzimy na kropke
    if(currentVal == '.') {
        return 0
    }

    // znalezlismy sciezke
    if(currentVal == end) {
        return 1
    }

    val nextValue = nextChar[currentVal]
    if(nextValue != null) {
//        println("$nextValue")
//        println("$nextChar")
        var counter = 0
        val dirs = if(dir != null) directions.filter { directionsOpposite[dir] != it } else directions

//            println("$dir ${directionsOpposite[dir]}, ${directions.filter { directionsOpposite[dir] != it }}")
        for(nextDir in dirs) {
            val (dr,dc) = nextDir
            if(findPathsInGrid(grid,row+dr,column+dc, nextDir) != 0)
                counter++

        }
        return counter
    }

//    val nextVal = nextChar[currentVal]
//    println(nextVal)
//    if(currentVal {
//    }

    return 0
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