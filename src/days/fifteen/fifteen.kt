package days.fifteen

import column
import row

val robot = '@'
val box = 'O'
val wall = '#'
val empty = '.'

private typealias Point = Pair<Int,Int>
private typealias Grid = List<List<Char>>
private typealias MutableGrid = List<MutableList<Char>>
private typealias Row = Int
private typealias Column = Int
private typealias Direction = Pair<Row, Column>

val up = -1 to 0
val right = 0 to 1
val down = 1 to 0
val left = 0 to -1

val diections = mapOf<Char,Direction>(
    '<' to left,
    'v' to down,
    '>' to right,
    '^' to up
)

private operator fun Pair<Int,Int>.plus(dir: Direction): Point {
    return Point(this.row + dir.row, this.column + dir.column)
}

private fun <T> include(grid: List<List<T>>, point: Point): Boolean = point.row in grid.indices && point.column in grid[0].indices
private fun include(grid: Array<BooleanArray>, point: Point): Boolean = grid[point.row][point.column] ?: false

private operator fun <T> List<List<T>>.contains(point: Point) = include(this, point)
private operator fun Array<BooleanArray>.contains(point: Point) = include(this, point)


fun solveFifteenDayFirstStar() {
    tests()
}


fun tests() {
    horizontalShiftTest("#..O@..O.#",1,'<')
    horizontalShiftTest("#.OO@..O.#",2,'<')
    horizontalShiftTest("#.OOO@.O.#",3,'<')
    horizontalShiftTest("#.#OO@.O.#",4,'<')
    horizontalShiftTest("#.#OO#@.O.#",5,'<')


    verticalShiftTest("#..O@..O.#",1,'^')
    verticalShiftTest("#.OO@..O.#",2,'^')
    verticalShiftTest("#.OOO@.O.#",3,'^')
    verticalShiftTest("#.#OO@.O.#",4,'^')
    verticalShiftTest("#.#OO#@.O.#",5,'^')
}

//sealed  class TestDirection {
//    object Vertical: TestDirection()
//    object Horizontal: TestDirection()
//}

fun translateXY(testGrid: String): List<MutableList<Char>> {
    return testGrid.toList().map { mutableListOf(it) }
}

fun horizontalShiftTest(testGrid: String,n: Int, dir: Char) {
    println("\n")
    println("CASE $n)")
    val list = testGrid.toMutableList()
    val robot = 0 to list.indexOf('@')
    println(list)
    move(listOf(list),robot,dir)
    println(list)
}

fun verticalShiftTest(testGrid: String, n: Int, dir: Char) {
    println("\n")
    println("CASE $n)")

    val column = translateXY(testGrid)
    var row = -1

    for(index in column.indices) {
        if(column[index][0] == '@'){
            row = index
        }
    }

    val columnOriginal = translateXY(testGrid)
    move(column,row to 0,dir)
    column.forEachIndexed { index,list -> println(" ${columnOriginal[index][0]}  ${list[0]}") }
}

fun move(grid: MutableGrid, position: Point, move: Char,parent: Char = '.'): Point? {
    val dir = diections[move]!!
    val nextPoint = position + dir

    if(nextPoint !in grid) {
        return null
    }

    val nextChar = grid[nextPoint.row][nextPoint.column]

    if(nextChar == wall) {
        return null
    }

    val currentChar = grid[position.row][position.column]
    if(nextChar == box) {
        val (row,column) = move(grid,nextPoint,move,currentChar) ?: return null

        if(currentChar == robot) {
            grid[position.row][position.column] = '.'
            grid[row][column]='@'
        }
        return position
    }


    grid[position.row][position.column] = parent
    grid[nextPoint.row][nextPoint.column] = currentChar
    return position
}



fun findRobotPosition(grid: Grid): Point? {
    for(row in grid.indices) {
        for(column in grid[row].indices) {
            if(grid[row][column] == robot) {
                return row to column
            }
        }
    }
    return null
}

