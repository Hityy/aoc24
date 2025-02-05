package days.fifteen2

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

    val (grid, moves) = getSource("src/days/fifteen/src1.txt")
    var robot = findRobotPosition(grid) ?: throw Error("No initial position robot found")
    for (dir in moves) {
        move(grid, robot, dir)?.let { robot = it }
    }

    val res = findAllBoxesPoints(grid,box).sumOf { (row, column) -> 100 * row + column }
    println(res)
}

fun getSource(path: String): Pair<List<MutableList<Char>>, List<Char>> {
    val lines = File(path).readLines()
    val emptyline = lines.indexOf("")
    val grid = lines.subList(0, emptyline).map { it.toCharArray().toMutableList() }
    val moves = lines.subList(emptyline + 1, lines.size).flatMap { it.toCharArray().toList() }
    return grid to moves
}

fun translateXY(testGrid: String): List<MutableList<Char>> {
    return testGrid.toList().map { mutableListOf(it) }
}

fun horizontalShiftTest(testGrid: String, expected: String, n: Int, dir: Char) {
    println("\n")
    println("CASE $n)")
    val list = testGrid.toMutableList()
    val robotPoint = 0 to list.indexOf('@')
    println(list.joinToString(""))
    move(listOf(list), robotPoint, dir)
    println(list.joinToString(""))

    if (expected != list.joinToString("")) {
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
    if (currentChar == robot) {
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

fun findAllBoxesPoints(grid: Grid, boxChar: Char): List<Point> {
    val points = mutableListOf<Point>()
    for (row in grid.indices) {
        for (column in grid[row].indices) {
            if (grid[row][column] == boxChar) {
                points.add(row to column)
            }
        }
    }
    return points
}

fun tests() {
    horizontalShiftTest("#..O@..O.#", "#.O@...O.#", 1, '<')
    horizontalShiftTest("#.OO@..O.#", "#OO@...O.#", 2, '<')
    horizontalShiftTest("#.OOO@.O.#", "#OOO@..O.#", 3, '<')
    horizontalShiftTest("#.#OO@.O.#", "#.#OO@.O.#", 4, '<')
    horizontalShiftTest("#.#OO#@.O.#", "#.#OO#@.O.#", 5, '<')
    horizontalShiftTest("#.#OO.@OO.#", "#.#OO..@OO#", 6, '>')

    verticalShiftTest("#..O@..O.#", 1, '^')
    verticalShiftTest("#.OO@..O.#", 2, '^')
    verticalShiftTest("#.OOO@.O.#", 3, '^')
    verticalShiftTest("#.#OO@.O.#", 4, '^')
    verticalShiftTest("#.#OO#@.O.#", 5, '^')
}


// **


fun solveFifteenDaySecondStar() {
//    tests2()

    val (smallerGrid, moves) = getSource("src/days/fifteen/src1.txt")
    val grid = transformGrid(smallerGrid)
    var robot= findRobotPosition(grid) ?: throw Error("No initial position robot found")

    var currentPoint = robot
    for(move in moves) {
        currentPoint = moveRobot(grid,currentPoint,move)
    }
    val res = findAllBoxesPoints(grid,'[').sumOf { (row, column) -> 100 * row + column }

    println(res)

}

fun transformGrid(grid: List<List<Char>>) = grid.map { row ->
    row.flatMap {
        when (it) {
            wall -> listOf('#', '#')
            box -> listOf('[', ']')
            empty -> listOf('.', '.')
            robot -> listOf('@', '.')
            else -> listOf()
        }
    }.toMutableList()
}


fun robotCanShiftVertical(
    grid: Grid,
    currentPoint: Point,
    dir: Direction,
): Boolean {
    val (row, column) = currentPoint
    val currentChar = grid[row][column]

    if (currentPoint !in grid) {
        return false
    }

    if (currentChar == empty) {
        return true
    }

    if (currentChar == wall) {
        return false
    }

    return when(currentChar) {
        '[' -> {
            val r1 = robotCanShiftVertical(grid, currentPoint + dir, dir)
            val r2 = robotCanShiftVertical(grid, currentPoint + dir + right, dir)
            r1 && r2
        }
        ']' -> {
            val r1 = robotCanShiftVertical(grid, currentPoint + dir, dir)
            val r2 = robotCanShiftVertical(grid, currentPoint +dir + left, dir)
            r1 && r2
        }
        '@' -> robotCanShiftVertical(grid, currentPoint+dir, dir)
        else -> throw Error("Invalid current Char $currentChar")
    }

}


fun robotCanShiftHorizontal(
    grid: Grid,
    currentPoint: Point,
    dir: Direction,
): Boolean {
    val (row, column) = currentPoint
    val currentChar = grid[row][column]

    if (currentPoint !in grid) {
        return false
    }

    if (currentChar == empty) {
        return true
    }

    if (currentChar == wall) {
        return false
    }

    return robotCanShiftHorizontal(grid, currentPoint + dir, dir)
}


fun moveHorizontal(grid: MutableGrid, currentPoint: Point, dir: Direction): Point {
    val (crow, ccolumn) = currentPoint
    val currentChar = grid[crow][ccolumn]
    val newPoint = currentPoint + dir

    val (nrow, ncolumn) = newPoint
    val nextChar = grid[nrow][ncolumn]

    if(nextChar == '[' || nextChar == ']' ) {
        moveHorizontal(grid, newPoint, dir)
    }

    grid[crow][ccolumn] = '.'
    grid[nrow][ncolumn] = currentChar
    return newPoint
}


fun moveVertical(grid: MutableGrid, currentPoint: Point, dir: Direction): Point {
    val newPoint = currentPoint + dir

    val (crow, ccolumn) = currentPoint
    val currentChar = grid[crow][ccolumn]

    val (nrow, ncolumn) = newPoint
    val nextChar = grid[nrow][ncolumn]


    when(nextChar) {
        ']' -> {
            moveVertical(grid, newPoint, dir)
            moveVertical(grid, newPoint + left, dir)
        }
        '[' ->  {
            moveVertical(grid, newPoint, dir)
            moveVertical(grid, newPoint + right, dir)
        }
    }

    grid[crow][ccolumn] = '.'
    grid[nrow][ncolumn] = currentChar
    return newPoint
}

fun moveRobot(grid: MutableGrid, currentPoint: Point, move: Char): Point {
    val dir = directions[move] ?: throw Exception("Cant find direction $move")

    return when(move) {
        '^','v' -> if(robotCanShiftVertical(grid, currentPoint,dir)) moveVertical(grid, currentPoint, dir)
        else currentPoint

        '<','>' -> if(robotCanShiftHorizontal(grid, currentPoint,dir)) moveHorizontal(grid, currentPoint, dir)
        else currentPoint

        else -> throw Error("No move for $move")
    }

}

fun tests2() {

    println("TEST HORIZNTAL")

    val (grid4, _) = getSource("src/days/fifteen/test4.txt")
    var robot = findRobotPosition(grid4) ?: throw Error("NO ROBOT FOUND")
    grid4.forEach { println(it) }
    moveHorizontal(grid4,robot, directions['<']!!)
    grid4.forEach { println(it) }

    println("TEST VERTICAL")

    val (grid9, _) = getSource("src/days/fifteen/test9.txt")
    var robot9 = findRobotPosition(grid9) ?: throw Error("NO ROBOT FOUND")
    grid9.forEach { println(it) }
    moveVertical(grid9,robot9, directions['^']!!)
    grid9.forEach { println(it) }

    val (grid7, _) = getSource("src/days/fifteen/test7.txt")
    var robot7 = findRobotPosition(grid7) ?: throw Error("NO ROBOT FOUND")
    grid7.forEach { println(it) }
    moveVertical(grid7,robot7, directions['^']!!)
    grid7.forEach { println(it) }

    val (grid8, _) = getSource("src/days/fifteen/test8.txt")
    var robot8 = findRobotPosition(grid8) ?: throw Error("NO ROBOT FOUND")

    val can8 = robotCanShiftVertical(grid8, robot8, directions['^']!!)
    println("false $can8")

    val (grid72, _) = getSource("src/days/fifteen/test7.txt")
    var robot72 = findRobotPosition(grid72) ?: throw Error("NO ROBOT FOUND")

    grid72.forEach { println(it) }
    val can7 = robotCanShiftVertical(grid72, robot72, directions['^']!!)
    println("true $can7")

}