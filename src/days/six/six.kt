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
            .map { it.split("").filter { it != "" }.map { it.first() } }
            .let { countPositions(it) }

    println("Result: $result")
}

fun solveSixDaySecondStar() {
    val grid = File("src/days/six/test.txt")
        .readLines()
        .map { it.split("").filter { it != "" }.map { it.first() } }

    val res = countPossibleLoops(grid)
    println(res)
//    val guardInitialPosition = findGuardPosition(grid)!!
//    val path = getPath(
//        grid,guardInitialPosition.row,
//        guardInitialPosition.column,
//        listOf(guardInitialPosition),
//        up,
//        (mutableListOf<Pair<Int,Int>>() to mutableListOf())
//    )
//    println(path)

//            .let { countPositions(it) }

//    println("Result: $result")
//    val l1 = listOf(1,2,3)
//    val l2 = listOf(1,2,3)
//    val l3 = listOf(3,2,1)
//
//    val r1 = l1 == l2
//    val r2 = l1.containsAll(l2)
//    val r3 = l3 == l2
//    val r4 = l2.containsAll(l3)
//
//    println("$r1 $r2 $r3 $r4")
}

fun countPositions(grid: List<List<Char>>): Int {
    val guardInitialPosition = findGuardPosition(grid)
    if (guardInitialPosition != null) {
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

fun traverseInGridByDir(
    grid: List<List<Char>>,
    row: Int,
    column: Int,
    positions: List<Pair<Int, Int>>,
    dir: Pair<Int, Int>
): List<Pair<Int, Int>> {
    if (row !in 0..<grid.size || column !in 0..<grid[0].size)
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

fun findGuardPosition(grid: List<List<Char>>): Pair<Int, Int>? {
    for (row in grid.indices) {
        for (column in grid[0].indices) {
            if (guard == grid[row][column]) {
                return row to column
            }
        }
    }
    return null
}

val <T, A> Pair<T, A>.row: T
    get() = this.first

val <T, A>Pair<T, A>.column: A
    get() = this.second

// --- **
//val loopCache = Pair(mutableListOf<Pair<Int,Int>>(),mutableListOf<Pair<Int,Int>>())

fun countPossibleLoops(grid: List<List<Char>>): Int {
    val guardInitialPosition = findGuardPosition(grid)
//    println(guardInitialPosition)
    if (guardInitialPosition != null) {
        return getLoopPaths(
            grid,
            guardInitialPosition.row,
            guardInitialPosition.column,
            listOf(guardInitialPosition),
            up,
            0
        )
    }
    return 0
}

fun getLoopPaths(
    grid: List<List<Char>>,
//    gridRef: List<List<Char>>,
    row: Int, column: Int,
    positions: List<Pair<Int, Int>>,
    dir: Pair<Int, Int>,
    counter: Int,
): Int {
    if (row !in 0..<grid.size || column !in 0..<grid[0].size)
        return counter

    val newGrid = grid.map { it.toMutableList() }.toMutableList()
    if (obstacle == grid[row][column]) {
        val nextDir = nextDirs.get(dir)!!
        val nextPosition = (row + nextDir.row - dir.row) to (column + nextDir.column - dir.column)

        newGrid[nextPosition.row][nextPosition.column] = '#'
        val isNextInLoop = isInLoopPath(newGrid, row, column, positions, dir, getEmptyLoopCache())

        return getLoopPaths(
            grid,
            nextPosition.row,
            nextPosition.column,
            positions.dropLast(1) + nextPosition,
            nextDir,
            if (isNextInLoop) counter + 1 else counter
        )
    }

    val nextPosition = (row + dir.row) to (column + dir.column)
//    val isNextInLoop = isInLoopPath(grid, nextPosition.row, nextPosition.column, positions, dir, getEmptyLoopCache())
//
    if (nextPosition.row !in 0..<grid.size || nextPosition.column !in 0..<grid[0].size) {
        return getLoopPaths(
            grid,
            nextPosition.row,
            nextPosition.column,
            positions + nextPosition,
            dir,
            counter
        )
    }

    newGrid[nextPosition.row][nextPosition.column] = '#'
    val isNextInLoop = isInLoopPath(newGrid, row, column, positions, dir, getEmptyLoopCache())

    return getLoopPaths(
        grid,
        nextPosition.row,
        nextPosition.column,
        positions + nextPosition,
        dir,
        if (isNextInLoop) counter + 1 else counter
    )
}



fun isLoop(
    currentPoint: Pair<Int, Int>,
    positions: List<Pair<Int, Int>>,
    loopCache: Pair<MutableList<Pair<Int, Int>>, MutableList<Pair<Int, Int>>>
): Boolean {

    val firstLoop = loopCache.first
    val secondLoop = loopCache.second

        if (firstLoop.contains(currentPoint)) {
            if (secondLoop.contains(currentPoint)) {
                if (firstLoop.size == secondLoop.size)
                    if (secondLoop.containsAll(firstLoop))
                        return true
            } else {
                secondLoop.add(currentPoint)
            }
        } else {
            firstLoop.add(currentPoint)
        }

    return false
}


fun isInLoopPath(
    grid: List<List<Char>>,
    row: Int, column: Int,
    positions: List<Pair<Int, Int>>,
    dir: Pair<Int, Int>, loopCache: Pair<MutableList<Pair<Int, Int>>,
            MutableList<Pair<Int, Int>>>
): Boolean {
    if (row !in 0..<grid.size || column !in 0..<grid[0].size)
        return false

    val currentPoint = Pair(row, column)
    if(positions.contains(currentPoint)) {
        if (isLoop(currentPoint, positions, loopCache)) {
            println(grid)
            return true
        }
    }

    if (obstacle == grid[row][column]) {
        val nextDir = nextDirs.get(dir)!!
        val nextPosition = (row + nextDir.row - dir.row) to (column + nextDir.column - dir.column)
        return isInLoopPath(
            grid,
            nextPosition.row,
            nextPosition.column,
            positions.dropLast(1) + nextPosition,
            nextDir,
            loopCache
        )
    }

    val nextPosition = (row + dir.row) to (column + dir.column)
    return isInLoopPath(grid, nextPosition.row, nextPosition.column, positions + nextPosition, dir, loopCache)
}

fun getEmptyLoopCache() = (mutableListOf<Pair<Int, Int>>() to mutableListOf<Pair<Int, Int>>())
