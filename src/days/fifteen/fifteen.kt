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

val horizontalDirections = Pair(left, right)
val directionsList = listOf(up, right, down, left)

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
    var robot = findRobotPosition(grid) ?: throw Error("NO ROBOT FOUND")
    for (dir in moves) {
        move(grid, robot, dir)?.let { robot = it }
    }

    val res = findAllBoxesPoints(grid).sumOf { (row, column) -> 100 * row + column }
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

// **


fun tests2() {

}

fun solveFifteenDaySecondStar() {
//    tests()

//    val (grid, moves) = getSource("src/days/fifteen/test3.txt")
//    val biggergrid = transformGrid(grid.toMutableList())
//    biggergrid.forEach{ println(it.joinToString("")) }


//    var robot = findRobotPosition(grid)!!
////    grid.forEach{ println(it) }
//    for(m in moves) {
//        move(grid,robot,m)?.let { robot = it }
////        if(n != null) {
////            robot = n
////        }
////        println("\n")
////        println("move: $m")
////        println("ACTUAL: $n")
////        println("new robot position $robot")
////        grid.forEach{ println(it) }
//    }
//
//    val (grid4, moves4) = getSource("src/days/fifteen/test4.txt")
//    var robot4 = findRobotPosition(grid4)!!
//    grid4.forEach { println(it) }
//    val boxes4 = identifyBoxesHorizontal(grid4, robot4 + left, left, emptyList(), getEmptyVisitedArray(grid4))
//    println(boxes4)
//
//    val (grid6, moves6) = getSource("src/days/fifteen/test7.txt")
//    var robot6 = findRobotPosition(grid6)!!
//    grid6.forEach { println(it) }
//    val boxes6 = identifyBoxesVertical(grid6, robot6 + up, up, emptyList(), getEmptyVisitedArray(grid6))
//    println(boxes6)


//    val (grid7, moves8) = getSource("src/days/fifteen/test7.txt")
//    var robot7 = findRobotPosition(grid7)!!
////    val can = robotCanShiftVertical(grid7,robot7+up,up)
////    println(can)
//
//    grid7.forEach{ println(it) }
//    move5(grid7,robot7,'^','.')
//    grid7.forEach{ println(it) }


    val (grid, moves) = getSource("src/days/fifteen/test3.txt")
    val biggergrid = transformGrid(grid.toMutableList()).map { it.toMutableList() }
    var robot = findRobotPosition(biggergrid) ?: throw Error("NO ROBOT FOUND")
        println("\n")
        println("BEFORE")
        biggergrid.forEach { println(it) }
    for (dir in moves) {
        println("MOVE $dir")
        move5(biggergrid, robot, dir)?.let { robot = it }
        println("\n")
        println("AFTER")
        biggergrid.forEach { println(it) }
    }

//    val res = findAllBoxesPoints(grid).sumOf { (row, column) -> 100 * row + column }
//    println(res)
}

fun getEmptyVisitedArray(grid: Grid) = Array(grid.size) { BooleanArray(grid[0].size) { false } }

fun transformGrid(grid: List<List<Char>>) = grid.map { row ->
    row.flatMap {
        when (it) {
            wall -> listOf('#', '#')
            box -> listOf('[', ']')
            empty -> listOf('.', '.')
            robot -> listOf('@', '.')
            else -> listOf()
        }
    }
}


fun move2(grid: MutableGrid, position: Point, dir: Direction, parent: Char = '.'): Point? {
    val nextPoint = position + dir


    if (nextPoint !in grid) {
        return position
    }

    val nextChar = grid[nextPoint.row][nextPoint.column]
    val currentChar = grid[position.row][position.column]

    if (nextChar == wall) {
        return null
    }


    val secondPartBoxPoint = when (nextChar) {
        '[' -> position + right
        ']' -> position + left
        else -> null
    }

    if ( secondPartBoxPoint != null) {

        val (row, column) = move2(grid, nextPoint, dir, currentChar) ?: return null
        val (row2, column2) = move2(grid, secondPartBoxPoint, dir, currentChar) ?: return null

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

// []
private typealias Box = Pair<Point, Point>
// ([],[],[])
//
private typealias HorizontalGroup = List<Box>
private typealias VerticalGroup = List<List<Box>>

fun identifyBoxesHorizontal(
    grid: Grid,
    currentPoint: Point,
    dir: Direction,
    points: List<Point>,
    visited: Array<BooleanArray>
): List<Point> {
    val (row, column) = currentPoint
    val currentChar = grid[row][column]

    if (currentPoint !in grid) {
        return points
    }

    if (currentChar == empty) {
        return points
    }

    if (currentChar == wall) {
        return points
    }

    if (currentChar == robot) {
        return points
    }

    visited[row][column] = true

    return identifyBoxesHorizontal(grid, currentPoint + dir, dir, points + currentPoint, visited)
}

//fun getHorizontal

fun identifyBoxesVertical(
    grid: Grid,
    currentPoint: Point,
    dir: Direction,
    points: List<Point>,
    visited: Array<BooleanArray>
): List<Point> {
    val (row, column) = currentPoint
    val currentChar = grid[row][column]

    if (currentPoint !in grid) {
        return points
    }

    if (currentChar == empty) {
        return points
    }

    if (currentChar == wall) {
        return points
    }

    if (currentChar == robot) {
        return points
    }

    if (visited[row][column]) {
        return points
    }

    val pointsBuffer = mutableListOf<Point>()
    val secondPartBoxPoint = if (currentChar == '[') currentPoint + right else currentPoint + left
    visited[row][column] = true

    if (visited[secondPartBoxPoint.row][secondPartBoxPoint.column]) {
        return points
    }
    visited[secondPartBoxPoint.row][secondPartBoxPoint.second] = true


    pointsBuffer += identifyBoxesVertical(grid, currentPoint + dir, dir, points + currentPoint, visited)
    pointsBuffer += identifyBoxesVertical(grid, secondPartBoxPoint + dir, dir, points + secondPartBoxPoint, visited)
    return pointsBuffer.distinct()
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

    if (currentChar == robot) {
        return false
    }

    val secondPartBoxPoint = if (currentChar == '[') currentPoint + right else currentPoint + left

    val can1 = robotCanShiftVertical(grid, currentPoint + dir, dir)
    val can2 =  robotCanShiftVertical(grid, secondPartBoxPoint + dir, dir)
    return can1 && can2
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

    if (currentChar == robot) {
        return false
    }

    return robotCanShiftVertical(grid, currentPoint + dir, dir)
}

//fun move4(grid: Grid, currentPoint: Point, move: Char): Point? {
//    val dir = directions[move]!!
//    val (row,column) = currentPoint
//    val firstSideBoxChar = grid[row][column]
//    val secondSideBoxPoint = if(firstSideBoxChar == ']') currentPoint + left else currentPoint + right
//    val secondSideBoxChar = grid[secondSideBoxPoint.row][secondSideBoxPoint.column]
//
//    if(currentPoint !in grid || secondSideBoxPoint !in grid) {
//        return null
//    }
//
//    if(firstSideBoxChar == wall || secondSideBoxChar == wall) {
//        return null
//    }
//
//    if(firstSideBoxChar == robot) {
//        // jestem robotem
//    }
//
//    if(firstSideBoxChar == empty) {
//        // przesuwamy
//    }
//
//
//}

//fun robotShiftVertical(
//    grid: MutableGrid,
//    currentPoint: Point,
//    dir: Direction,
//    visited: Array<BooleanArray>
//): Point? {
//    val (row, column) = currentPoint
//    val currentChar = grid[row][column]
//    val nextPoint = currentPoint + dir
//    val nextChar = grid[nextPoint.row][nextPoint.column]
//
//
//    if (nextPoint !in grid) {
//        return null
//    }
//
//    if (nextChar == wall) {
//        return null
//    }
//
//    if (currentChar == robot) {
//        visited[row][column] = true
//        val point = robotShiftVertical(grid,currentPoint+dir,dir,visited)
//        if(point != null) {
//            grid[point.row][point.column] = '@'
//            grid[row][column] = '.'
//        }
//
//        return point
//    }
//
//    if(currentChar != '[' || currentChar != ']') {
//        return null
//    }
//
//
//    if (nextChar == box) {
//        val secondPartBoxPoint = if(currentChar == ']') currentPoint + left else currentPoint + right
//        val secondPartBoxChar = grid[secondPartBoxPoint.row][secondPartBoxPoint.column]
//
//        val newPoint1 = robotShiftVertical(grid,currentPoint + dir,dir,visited)
//        val newPoint2 = robotShiftVertical(grid,secondPartBoxPoint + dir,dir,visited)
//
//        if(newPoint1 != null) {
//            grid[newPoint1.row][newPoint1.column] = currentChar
//            grid[newPoint1.row][newPoint1.column] = '.'
//        }
//
//        if(newPoint2 != null) {
//            grid[newPoint2.row][newPoint2.column] = secondPartBoxChar
//            grid[newPoint2.row][newPoint2.column] = '.'
//        }
//
//        return nextPoint
//
//    }
//
//
//    println(currentChar)
//
//    return null
//
//}
//


fun move5(grid: MutableGrid, currentPoint: Point, move: Char, parent: Char = '.'): Point? {
    val dir = directions[move]!!
    val nextPoint = currentPoint + dir

    if (nextPoint !in grid) {
        return currentPoint
    }

    val currentChar = grid[currentPoint.row][currentPoint.column]
    val nextChar = grid[nextPoint.row][nextPoint.column]

    if (nextChar == wall) {
        return null
    }

    if (nextChar == '[' || nextChar == ']') {

//        val (row, column) = move(grid, nextPoint, move, currentChar) ?: return null

        val secondPartBoxPoint = if(nextChar == ']') nextPoint + left else nextPoint + right
        val secondPartBoxChar = grid[secondPartBoxPoint.row][secondPartBoxPoint.column]

        val (row,column) = move5(grid,nextPoint,move,currentChar) ?: return null
        if(move == '^') {
            val p = move5(grid,secondPartBoxPoint,move,secondPartBoxChar) ?: return null
            println(p)
        }

        if (currentChar == robot) {
            grid[currentPoint.row][currentPoint.column] = '.'
            grid[row][column] = '@'
            if(move == '^') {
                grid[secondPartBoxPoint.row][secondPartBoxPoint.column] = '.'
            }
            return nextPoint
        }
        return currentPoint
    }


    grid[currentPoint.row][currentPoint.column] = parent
    grid[nextPoint.row][nextPoint.column] = currentChar

    if (currentChar == robot) {
        return nextPoint
    }
    return currentPoint
}



