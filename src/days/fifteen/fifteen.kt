package days.fifteen

import column
import row
import java.io.File

val robot = '@'
val box = 'O'
val wall = '#'
val empty = '.'

private typealias Point = Pair<Int, Int>
private typealias Grid = List<List<Char>>
private typealias MutableGrid = List<MutableList<Char>>
private typealias Row = Int
private typealias Column = Int
private typealias Direction = Pair<Row, Column>

val up = -1 to 0
val right = 0 to 1
val down = 1 to 0
val left = 0 to -1

val directions = mapOf<Char, Direction>(
    '<' to left,
    'v' to down,
    '>' to right,
    '^' to up
)

private operator fun Pair<Int, Int>.plus(dir: Direction): Point {
    return Point(this.row + dir.row, this.column + dir.column)
}

private fun <T> include(grid: List<List<T>>, point: Point): Boolean =
    point.row in grid.indices && point.column in grid[0].indices

private fun include(grid: Array<BooleanArray>, point: Point): Boolean = grid[point.row][point.column] ?: false

private operator fun <T> List<List<T>>.contains(point: Point) = include(this, point)
private operator fun Array<BooleanArray>.contains(point: Point) = include(this, point)


fun solveFifteenDayFirstStar() {
//    tests()

    val (grid, moves ) = getSource("src/days/fifteen/src1.txt")

    var robot = findRobotPosition(grid)!!
//    grid.forEach{ println(it) }
    for(m in moves) {
        val n = move(grid,robot,m)
        if(n != null) {
            robot = n
        }
//        println("\n")
//        println("move: $m")
//        println("ACTUAL: $n")
//        println("new robot position $robot")
//        grid.forEach{ println(it) }
    }

    val res = findAllBoxesPoints(grid).sumOf { (row,column) -> 100*row + column }
    println(res)
}

fun getSource(path: String): Pair<List<MutableList<Char>>, List<Char>> {
    val lines = File(path).readLines()
    val emptyline = lines.indexOf("")
    val grid = lines.subList(0, emptyline).map { it.toCharArray().toMutableList() }
    val moves = lines.subList(emptyline + 1, lines.size).flatMap { it.toCharArray().toList() }
    return grid to moves
}

fun tests() {
    horizontalShiftTest("#..O@..O.#", "#.O@...O.#",1, '<')
    horizontalShiftTest("#.OO@..O.#","#OO@...O.#", 2, '<')
    horizontalShiftTest("#.OOO@.O.#", "#OOO@..O.#",3, '<')
    horizontalShiftTest("#.#OO@.O.#","#.#OO@.O.#" ,4, '<')
    horizontalShiftTest("#.#OO#@.O.#","#.#OO#@.O.#", 5, '<')
    horizontalShiftTest("#.#OO.@OO.#","#.#OO..@OO#", 6, '>')


    verticalShiftTest("#..O@..O.#", 1, '^')
    verticalShiftTest("#.OO@..O.#", 2, '^')
    verticalShiftTest("#.OOO@.O.#", 3, '^')
    verticalShiftTest("#.#OO@.O.#", 4, '^')
    verticalShiftTest("#.#OO#@.O.#", 5, '^')
}

fun translateXY(testGrid: String): List<MutableList<Char>> {
    return testGrid.toList().map { mutableListOf(it) }
}

fun horizontalShiftTest(testGrid: String,expected: String, n: Int, dir: Char) {
    println("\n")
    println("CASE $n)")
    val list = testGrid.toMutableList()
    val robotPoint = 0 to list.indexOf('@')
    println(list.joinToString(""))
    move(listOf(list), robotPoint, dir)
    println(list.joinToString(""))

    if(expected != list.joinToString("")) {
        throw Error("CASE $n FAILED")
    }

}

fun verticalShiftTest(testGrid: String, n: Int, dir: Char) {
    println("\n")
    println("CASE $n)")

    val column = translateXY(testGrid)
    var row = -1

    for (index in column.indices) {
        if (column[index][0] == '@') {
            row = index
        }
    }

    val columnOriginal = translateXY(testGrid)
    move(column, row to 0, dir)
    column.forEachIndexed { index, list -> println(" ${columnOriginal[index][0]}  ${list[0]}") }
}

fun move(grid: MutableGrid, position: Point, move: Char, parent: Char = '.'): Point? {
    val dir = directions[move]!!
    val nextPoint = position + dir

    if (nextPoint !in grid) {
        return position
    }

    val nextChar = grid[nextPoint.row][nextPoint.column]
    val currentChar = grid[position.row][position.column]


    if (nextChar == wall) {
        return null
    }

    if (nextChar == box) {

        val (row, column) = move(grid, nextPoint, move, currentChar) ?: return null

        if (currentChar == robot) {
            grid[position.row][position.column] = '.'
            grid[row][column] = '@'
            return nextPoint
        }
        return position
    }


    grid[position.row][position.column] = parent
    grid[nextPoint.row][nextPoint.column] = currentChar
    if(currentChar == robot) {
        return nextPoint
    }
    return position
}


fun findRobotPosition(grid: Grid): Point? {
    for (row in grid.indices) {
        for (column in grid[row].indices) {
            if (grid[row][column] == robot) {
                return row to column
            }
        }
    }
    return null
}

fun findAllBoxesPoints(grid: Grid): List<Point> {
    val points = mutableListOf<Point>()
    for (row in grid.indices) {
        for (column in grid[row].indices) {
            if (grid[row][column] == box) {
                points.add(row to column)
            }
        }
    }
    return points
}
