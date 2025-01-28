package days.six

import days.ten.start
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
// 4382 too high
// 4374 good !

fun solveSixDayFirstStar() {
    val result =
        File("src/days/six/test.txt")
            .readLines()
            .map { it.split("").filter { it != "" }.map { it.first() } }
            .let { countPositions(it) }

    println("Result: $result")
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


fun solveSixDaySecondStar() {
    val grid =
        File("src/days/six/src1.txt")
            .readLines()
            .map { it.split("").filter { it != "" }.map { it.first() } }

    val guardInitialPosition = findGuardPosition(grid)
    if (guardInitialPosition != null) {
        val visited = Array(grid.size) { CharArray(grid[0].size) { '.' } }
        val chars = Array(grid.size) { Array(grid[0].size) { mutableListOf<Char>() } }
        visited[guardInitialPosition.row][guardInitialPosition.column] = start

//         dfsRecursive(
//            grid,
//            guardInitialPosition.row,
//            guardInitialPosition.column,
//             guardInitialPosition.row,
//             guardInitialPosition.column,
//            visited,
//            up
//        )
        dfsIterative(
            grid,
            guardInitialPosition.row,
            guardInitialPosition.column,
            visited,
            up,
            chars
        )
//        println("Result: ${visited.toString()}")
        visited.forEach { println(it.map { it.toString() }) }
//        val distinct = visited.map { it.filter { it == 'X'}.size }.sum()
        val distinct = visited.sumOf { it.toList().mapNotNull { charToNumber[it] }.sum() }
        println(distinct)

//        val grid1 =
//            File("src/days/six/test21.txt")
//                .readLines()
//                .map { it.split("").filter { it != "" }.map { it.first()} }
//
//        val testObstacle1 = willBeLoop(grid1,6,4,6,3,6,5, left,visited)
//        println(testObstacle1)

//        val grid1 =
//            File("src/days/six/test22.txt")
//                .readLines()
//                .map { it.split("").filter { it != "" }.map { it.first()} }
//
//        val testObstacle1 = willBeLoop(grid1,6,7,7,7,5,7, left,visited)
//        println(testObstacle1)
    }


}


// -- **
// 1832 too high
// 1690 too low
fun dfsRecursive(
    grid: List<List<Char>>,
    row: Int,
    column: Int,
    lastRow: Int,
    lastColumn: Int,
    visited: Array<CharArray>,
    dir: Pair<Int, Int>
): Array<CharArray> {
    println("$row $column")
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

fun dfsIterative(
    grid: List<List<Char>>,
    initalrow: Int,
    initalcolumn: Int,
    visited: Array<CharArray>,
    dir: Pair<Int, Int>,
    visitedPaths: Array<Array<MutableList<Char>>>
): Array<CharArray> {
    val stack = ArrayDeque<Pair<Int, Int>>().also { it.add(initalrow to initalcolumn) }
    var nextDir = dir
    var beforeLast = 0 to 0
    var lastPosition = initalrow to initalcolumn
    var counter = 0
    while (stack.isNotEmpty()) {
        val (row, column) = stack.removeLast()
        println(row to column)

        if (row !in grid.indices || column !in grid[0].indices)
            continue


//        if(visitedPaths[row][column].contains(dirsToChar[nextDir])) {
//            println("LOOP")
//            break
//        }

        if (obstacle == grid[row][column]) {
            visited[row][column] = '#'
            nextDir = nextDirs.get(nextDir)!!
            val nextPosition = (lastPosition.row + nextDir.row) to (lastPosition.column + nextDir.column)
            stack.add(nextPosition)
            beforeLast = lastPosition
            lastPosition = nextPosition

            if (visitedPaths[row][column].contains(dirsToChar[nextDir])) {
                if (willBeLoop(
                        grid,
                        lastPosition.row,
                        lastPosition.column,
                        nextPosition.row,
                        nextPosition.column,
                        beforeLast.row,
                        beforeLast.column,
                        nextDir,
                        visited
                    )
                ) {
                    counter++
                }

            }
        } else {
            visited[row][column] = dirsToChar[nextDir]!!
//            visitedPaths[row][column].add(dirsToChar[nextDir]!!)
            visitedPaths[row][column].add(dirsToChar[nextDir]!!)
            beforeLast = lastPosition
            lastPosition = row to column
            val nextPosition = (row + nextDir.row) to (column + nextDir.column)
            stack.add(nextPosition)

            if (visitedPaths[row][column].contains(dirsToChar[nextDir])) {
                if (willBeLoop(
                        grid,
                        lastPosition.row,
                        lastPosition.column,
                        nextPosition.row,
                        nextPosition.column,
                        beforeLast.row,
                        beforeLast.column,
                        nextDir,
                        visited
                    )
                ) {
                    counter++
                }

            }
//            if(visited[row][column] != '.' && visited[row][column] != '^') {
//            if(willBeLoop(grid,row,column,nextPosition.row,nextPosition.column,nextDir,visited)) {
//                counter++
//            }
//            }
        }
    }

    println(counter)
    return visited
}

// visitedPaths: Array<Array<MutableList<Char>>>
fun willBeLoop(
    _grid: List<List<Char>>,
    currentRow: Int,
    currentColumn: Int,
    obstacleRow: Int,
    obstacleColumn: Int,
    lastRow: Int,
    lastColumn: Int,
    dir: Pair<Int, Int>,
    visited: Array<CharArray>,
): Boolean {
    if (obstacleRow !in _grid.indices || obstacleColumn !in _grid[0].indices)
        return false

//    if(_grid[obstacleRow][obstacleColumn] != '.') {
//        return false
//    }

//    val chars = Array(grid.size) { Array(grid[0].size) { mutableListOf<Char>() } }
//    val visitedPaths = visited.map{ row -> row.map { mutableListOf(it) }.toTypedArray() }.toTypedArray()
    val visitedPaths = Array(_grid.size) { Array(_grid[0].size) { mutableListOf<Char>() } }
    val stack = ArrayDeque<Pair<Int, Int>>().also { it.add(currentRow to currentColumn) }
    var nextDir = dir
    var lastPosition = lastRow to lastColumn
    val grid = _grid.map { it.toMutableList() }
//    println(grid)
    grid[obstacleRow][obstacleColumn] = '#'

    while (stack.isNotEmpty()) {
        val (row, column) = stack.removeLast()
        println(row to column)
        if (row !in grid.indices || column !in grid[0].indices)
            continue

        if (visitedPaths[row][column].contains(dirsToChar[nextDir])) {
            println("LOOP $row $column")
//            visitedPaths.forEach { println(it.joinToString(",")) }
            return true
        }


        if (obstacle == grid[row][column]) {
//            visited[row][column] = '#'
            if(visitedPaths[lastPosition.row][lastPosition.column].size > 0) {
                visitedPaths[lastPosition.row][lastPosition.column].removeLast()
            }

            visitedPaths[lastPosition.row][lastPosition.column].add(dirsToChar[nextDir]!!)
//            nextDir = nextDirs.get(nextDir)!!ter
//            val nextPosition = (lastPosition.row + nextDir.row) to (lastPosition.column + nextDir.column)
//            stack.add(nextPosition)
//                visited[row][column] = '#'

            nextDir = nextDirs.get(nextDir)!!
            val nextPosition = (lastPosition.row + nextDir.row) to (lastPosition.column + nextDir.column)
            stack.add(nextPosition)
            lastPosition = nextPosition
        } else {
//            visited[row][column] = dirsToChar[nextDir]!!
//            visitedPaths[row][column].add(dirsToChar[nextDir]!!)
            visitedPaths[row][column].add(dirsToChar[nextDir]!!)
            lastPosition = row to column
            val nextPosition = (row + nextDir.row) to (column + nextDir.column)
            stack.add(nextPosition)
        }


    }

//    visitedPaths.forEach { println(it.joinToString(",")) }
    return false
}

