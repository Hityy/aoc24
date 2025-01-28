package days.six

import java.io.File

typealias Point = Pair<Int, Int>
typealias Direction = Pair<Int, Int>
typealias Grid = List<List<Int>>
typealias CharGrid = List<List<Char>>

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


fun countPositions(grid: CharGrid): Int {
    val guardPoint = findGuardPosition(grid)
    if (guardPoint != null) {
        val positions = traverseInGridByDir(
            grid,
            guardPoint,
            listOf(guardPoint),
            up
        )
        return positions.distinct().size
    }
    return 0
}

fun traverseInGridByDir(
    grid: List<List<Char>>,
    currentPoint: Point,
    positions: List<Point>,
    dir: Direction
): List<Point> {
    if (currentPoint !in grid) {
        return positions.dropLast(1)
    }

    val (row, column) = currentPoint
    if (obstacle == grid[row][column]) {
        val nextDir = nextDirs.get(dir)!!
        val nextPosition = (row + nextDir.row - dir.row) to (column + nextDir.column - dir.column)

        return traverseInGridByDir(
            grid,
            nextPosition,
            positions.dropLast(1) + nextPosition,
            nextDir
        )
    }

    val nextPosition = (row + dir.row) to (column + dir.column)
    return traverseInGridByDir(grid, nextPosition, positions + nextPosition, dir)
}

fun findGuardPosition(grid: CharGrid): Point? {
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

    val guardPoint = findGuardPosition(grid)!!
    val visited = dfsIterative(grid, guardPoint, up)

//        visited.forEach { println(it.map { it.toString() }) }
//        val distinct = visited.sumOf { it.toList().mapNotNull { charToNumber[it] }.sum() }
//        println(distinct)

    val pathPoints = visited.flatMapIndexed { rowIndex, row ->
        row.toList().mapIndexedNotNull { colIndex, v ->
            if (v != '.') rowIndex to colIndex
            else null
        }
    }

    val loops = pathPoints.filter { obstacle -> willBeLoop(grid, guardPoint, obstacle, up) }
    println(loops.size)

}


// -- **
// 1853 too high
// 1836 too high
// 1832 too high
// 1705 good!
// 1690 too low

fun <T> include(grid: List<List<T>>, point: Point): Boolean =
    point.row in grid.indices && point.column in grid[0].indices

private operator fun <T> List<List<T>>.contains(point: Point) = include(this, point)


fun straightOrRight(grid: CharGrid, point: Point, dir: Direction): Pair<Point, Direction>? {
    var currentDir = dir

    repeat(4, {
        val testPoint = (point.row + currentDir.row) to (point.column + currentDir.column)
        if (testPoint in grid) {
            if (grid[testPoint.row][testPoint.column] != obstacle) {
                return testPoint to currentDir
            }
        } else {
            return null
        }
        currentDir = nextDirs[currentDir]!!
    })
    return null

//    for (r in 0 until 4) {
//        val testPoint = (point.row + currentDir.row) to (point.column + currentDir.column)
//        if (pointInGrid(grid, testPoint)) {
//            if (grid[testPoint.row][testPoint.column] != obstacle) {
//                return testPoint to currentDir
//            }
//        } else {
//            return null
//        }
//        currentDir = nextDirs[currentDir]!!
//    }
}

fun dfsIterative(
    grid: CharGrid,
    startingPoint: Point,
    dir: Direction,
): Array<CharArray> {
    val visited = Array(grid.size) { CharArray(grid[0].size) { '.' } }
//            visited[guardInitialPosition.row][guardInitialPosition.column] = start
    val stack = ArrayDeque<Point>().also { it.add(startingPoint) }
    var currentDir = dir

    while (stack.isNotEmpty()) {
        val currentPoint = stack.removeLast()

        if (currentPoint !in grid)
            continue

        visited[currentPoint.row][currentPoint.column] = dirsToChar[currentDir]!!

        val turn = straightOrRight(grid, currentPoint, currentDir)
        if (turn != null) {
            val (nextPoint, nextDir) = turn
            currentDir = nextDir
            stack.add(nextPoint)
        }
    }

    return visited
}

fun willBeLoop(
    legacyGrid: CharGrid,
    startingPoint: Point,
    obstaclePoint: Point,
    dir: Direction,
): Boolean {

    if (obstaclePoint !in legacyGrid)
        return false

    if (legacyGrid[obstaclePoint.row][obstaclePoint.column] != '.') {
        return false
    }

    val visited = mutableSetOf<Pair<Point, Direction>>()
    val stack = ArrayDeque<Point>().also { it.add(startingPoint) }
    val grid = legacyGrid.map { it.toMutableList() }
    var currentDir = dir

    grid[obstaclePoint.row][obstaclePoint.column] = '#'

    while (stack.isNotEmpty()) {
        val currentPoint = stack.removeLast()
        val currentKey = currentPoint to currentDir
        if (currentPoint !in grid) {
            continue
        }

        if (currentKey in visited) {
            return true
        }

        val turn = straightOrRight(grid, currentPoint, currentDir)
        if (turn != null) {
            val (nextPoint, nextDir) = turn
            currentDir = nextDir
            visited.add(currentPoint to nextDir)
            stack.add(nextPoint)
        }
    }
    return false
}

//
//fun dfsRecursive(
//    grid: List<List<Char>>,
//    row: Int,
//    column: Int,
//    lastRow: Int,
//    lastColumn: Int,
//    visited: Array<CharArray>,
//    dir: Direction
//): Array<CharArray> {
//    if (row !in grid.indices || column !in grid[0].indices || visited[row][column] == 'X')
//        return visited
//
//    visited[row][column] = 'X'
//
//    if (obstacle == grid[row][column]) {
//        visited[row][column] = '#'
//        val nextDir = nextDirs.get(dir)!!
//        val nextPosition = (lastRow + nextDir.row) to (lastColumn + nextDir.column)
//        return dfsRecursive(
//            grid,
//            nextPosition.row,
//            nextPosition.column,
//            row,
//            column,
//            visited,
//            nextDir
//        )
//    }
//
//    val nextPosition = (row + dir.row) to (column + dir.column)
//    return dfsRecursive(grid, nextPosition.row, nextPosition.column, row, column, visited, dir)
//
//}
