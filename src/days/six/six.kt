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

// 4382 too high
// 4374 good !

fun solveSixDayFirstStar() {
    val result =
        File("src/days/six/src1.txt")
            .readLines()
            .map { it.split("").filter { it != "" }.map { it.first()} }
            .let { countPositions(it) }

    println("Result: $result")
}


fun countPositions(grid: List<List<Char>>): Int {
    val guardInitialPosition = findGuardPosition(grid)
    if(guardInitialPosition != null) {
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

fun traverseInGridByDir(grid: List<List<Char>>, row: Int, column: Int, positions: List<Pair<Int,Int>>, dir: Pair<Int,Int>): List<Pair<Int,Int>> {
    if (row !in grid.indices || column !in grid[0].indices)
        return positions.dropLast(1)

    if(obstacle == grid[row][column]) {
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

fun findGuardPosition(grid: List<List<Char>>): Pair<Int,Int>? {
    for(row in grid.indices) {
        for(column in grid[0].indices) {
            if(guard == grid[row][column]) {
                return row to column
            }
        }
    }
    return null
}

val <T, A> Pair<T, A>.row: T
    get() = this.first

val <T,A>Pair<T,A>.column: A
    get() = this.second



fun solveSixDaySecondStar() {
    val grid =
        File("src/days/six/test.txt")
            .readLines()
            .map { it.split("").filter { it != "" }.map { it.first()} }

    val guardInitialPosition = findGuardPosition(grid)
    if(guardInitialPosition != null) {
        val visited = Array(grid.size) { CharArray(grid[0].size) { '.' } }

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
            up
        )
//        println("Result: ${visited.toString()}")
        visited.forEach { println(it.map { it.toString() }) }
//        val distinct = visited.map { it.filter { it == 'X'}.size }.sum()
        val distinct = visited.sumOf { it -> it.filter { it == 'X' }.size }
        println(distinct)
    }



}

// fun getNextPosition(grid: List<List<Char>>, row: Int, column: Int,dir: Pair<Int,Int) {
//     if(obstacle == grid[row][column]) {

//     }

// }

// -- **

fun dfsRecursive(grid: List<List<Char>>, row: Int, column: Int,lastRow: Int, lastColumn: Int, visited: Array<CharArray>, dir: Pair<Int,Int>): Array<CharArray> {
    println("$row $column")
    if (row !in grid.indices || column !in grid[0].indices || visited[row][column] == 'X')
        return visited

    visited[row][column] = 'X'

    if(obstacle == grid[row][column]) {
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

fun dfsIterative(grid: List<List<Char>>, initalrow: Int, initalcolumn: Int, visited: Array<CharArray>, dir: Pair<Int,Int>): Array<CharArray> {
    val stack = ArrayDeque<Pair<Int,Int>>().also { it.add(initalrow to initalcolumn) }
    var nextDir = dir
    var lastPosition = initalrow to initalcolumn

    while(stack.isNotEmpty()) {
        val (row,column) = stack.removeLast()

        if (row !in grid.indices || column !in grid[0].indices)
            continue

        if(obstacle == grid[row][column]) {
            visited[row][column] = '#'
            nextDir = nextDirs.get(nextDir)!!
            val nextPosition = (lastPosition.row + nextDir.row) to (lastPosition.column + nextDir.column)
            stack.add(nextPosition)
        } else {
            visited[row][column] = 'X'
            lastPosition = row to column
            val nextPosition = (row + nextDir.row) to (column + nextDir.column)
            stack.add(nextPosition)
        }

    }

    return visited
}