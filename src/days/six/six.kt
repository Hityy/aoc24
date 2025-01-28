package days.six

import days.ten.start
import java.io.File

typealias Point = Pair<Int,Int>
typealias Direction = Pair<Int,Int>

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

val dirsToChar = mapOf(
    up to '^',
    down to 'v',
    left to '<',
    right to '>',
)

val charToNumber = mapOf(
    '^' to 1,
    '>' to 1,
    '<' to 1,
    'v' to 1,
    '.' to 0,
    '#' to 0,
)

val <T, A> Pair<T, A>.row: T
    get() = this.first

val <T, A>Pair<T, A>.column: A
    get() = this.second


// 4382 too high
// 4374 good !

fun solveSixDayFirstStar() {
    val result =
        File("src/days/six/test26.txt")
            .readLines()
            .map { it.split("").filter { it != "" }.map { it.first() } }
            .let { countPositions(it) }

    println("Result: $result")
}


fun countPositions(grid: List<List<Char>>): Int {
    val guardPoint = findGuardPosition(grid)
    if (guardPoint != null) {
        val positions = traverseInGridByDir(
            grid,
            guardPoint.row,
            guardPoint.column,
            listOf(guardPoint),
            up
        )
        return positions.distinct().size
    }
    return 0
}

fun traverseInGridByDir(
    grid: List<List<Char>>,
    row: Int,
    column: Int,
    positions: List<Point>,
    dir: Direction
): List<Point> {
    if (row !in grid.indices || column !in grid[0].indices)
        return positions.dropLast(1)

    if (obstacle == grid[row][column]) {
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

fun findGuardPosition(grid: List<List<Char>>): Point? {
    for (row in grid.indices) {
        for (column in grid[0].indices) {
            if (guard == grid[row][column]) {
                return row to column
            }
        }
    }
    return null
}


fun solveSixDaySecondStar() {
    val grid =
        File("src/days/six/src1.txt")
            .readLines()
            .map { it.split("").filter { it != "" }.map { it.first() } }

    val guardInitialPosition = findGuardPosition(grid)
    if (guardInitialPosition != null) {
        val visited = Array(grid.size) { CharArray(grid[0].size) { '.' } }

        visited[guardInitialPosition.row][guardInitialPosition.column] = start

        dfsIterative(
            grid,
            guardInitialPosition,
            visited,
            up,
        )

//        visited.forEach { println(it.map { it.toString() }) }
//        val distinct = visited.sumOf { it.toList().mapNotNull { charToNumber[it] }.sum() }
//        println(distinct)

        val pathPoints = visited.flatMapIndexed { rowIndex, row ->
            row.toList().mapIndexedNotNull { colIndex, v ->
                if (v != '.') rowIndex to colIndex
                else null
            }
        }

        val loops = pathPoints.filter { obstacle -> willBeLoop(grid,guardInitialPosition,obstacle, up) }
        println(loops.size)


    }

}


// -- **
// 1853 too high
// 1836 too high
// 1832 too high

// 1690 too low

fun pointInGrid(grid: List<List<Char>>, point: Point): Boolean {
    return point.row in grid.indices && point.column in grid[0].indices
}

fun straightOrRight(
    grid: List<List<Char>>,
    point: Point,
    dir: Direction
): Pair<Point, Direction>? {
    var currentDir = dir

    for (r in 0 until 4) {
        val testPoint = (point.row + currentDir.row) to (point.column + currentDir.column)
        if (pointInGrid(grid, testPoint)) {
            if (grid[testPoint.row][testPoint.column] != obstacle) {
                return testPoint to currentDir
            }
        } else {
            return null
        }
        currentDir = nextDirs[currentDir]!!
    }
    return null
}

fun dfsIterative(
    grid: List<List<Char>>,
    startingPoint: Point,
    visited: Array<CharArray>,
    dir: Direction,
): Array<CharArray> {
    val stack = ArrayDeque<Point>().also { it.add(startingPoint.row to startingPoint.column) }
    var currentDir = dir

    while (stack.isNotEmpty()) {
        val (row, column) = stack.removeLast()

        if (row !in grid.indices || column !in grid[0].indices)
            continue

        visited[row][column] = dirsToChar[currentDir]!!

        val turn = straightOrRight(grid, row to column, currentDir)
        if (turn != null) {
            val (nextPoint, nextDir) = turn
            currentDir = nextDir
            stack.add(nextPoint)
        }
    }

    return visited
}

fun willBeLoop(
    legacyGrid: List<List<Char>>,
    startingPoint: Point,
    obstaclePoint: Point,
    dir: Direction,
    ): Boolean {

    if (obstaclePoint.row !in legacyGrid.indices || obstaclePoint.row !in legacyGrid[0].indices)
        return false

    if (legacyGrid[obstaclePoint.row][obstaclePoint.column] != '.') {
        return false
    }

//    val visitedPaths = Array(legacyGrid.size) { Array(legacyGrid[0].size) { mutableListOf<Char>() } }
    val visited = mutableSetOf<Pair<Point, Direction>>()
    val stack = ArrayDeque<Pair<Int, Int>>().also { it.add(startingPoint.row to startingPoint.column) }
    val grid = legacyGrid.map { it.toMutableList() }
    var currentDir = dir

    grid[obstaclePoint.row][obstaclePoint.column] = '#'

    while (stack.isNotEmpty()) {
        val (row, column) = stack.removeLast()
        if (row !in grid.indices || column !in grid[0].indices) {
            continue
        }

//        if (visitedPaths[row][column].contains(dirsToChar[currentDir])) {
//            return true
//        }
        if(((row to column) to currentDir) in visited) {
            return true
        }

        val turn = straightOrRight(grid, row to column, currentDir)
        if (turn != null) {
            val (nextPoint, nextDir) = turn
            currentDir = nextDir
//            visitedPaths[row][column].add(dirsToChar[currentDir]!!)
            visited.add((row to column) to nextDir)

            stack.add(nextPoint)

//            if(currentDir == dir && nextDir == startingPoint) {
//                return true
//            }
        }

    }

    return false
}


fun dfsRecursive(
    grid: List<List<Char>>,
    row: Int,
    column: Int,
    lastRow: Int,
    lastColumn: Int,
    visited: Array<CharArray>,
    dir: Direction
): Array<CharArray> {
    if (row !in grid.indices || column !in grid[0].indices || visited[row][column] == 'X')
        return visited

    visited[row][column] = 'X'

    if (obstacle == grid[row][column]) {
        visited[row][column] = '#'
        val nextDir = nextDirs.get(dir)!!
        val nextPosition = (lastRow + nextDir.row) to (lastColumn + nextDir.column)
        return dfsRecursive(
            grid,
            nextPosition.row,
            nextPosition.column,
            row,
            column,
            visited,
            nextDir
        )
    }

    val nextPosition = (row + dir.row) to (column + dir.column)
    return dfsRecursive(grid, nextPosition.row, nextPosition.column, row, column, visited, dir)

}
