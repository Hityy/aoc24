package days.sixteen

import column
import row
import java.io.File
import java.util.*

val start = 'S'
val end = 'E'
val wall = '#'
val empty = '.'

private typealias Point = Pair<Int, Int>
private typealias Grid = List<List<Char>>
private typealias MutableGrid = List<MutableList<Char>>
private typealias Row = Int
private typealias Column = Int
private typealias Direction = Pair<Row, Column>
private typealias Path = MutableList<Point>


val up = -1 to 0
val right = 0 to 1
val down = 1 to 0
val left = 0 to -1

val directions = listOf(up, right, down, left)
val directionOpposite = mapOf(up to down, left to right, down to up, right to left)

val dirsToChar = mutableMapOf<Direction, Char>(
    up to '^',
    down to 'v',
    right to '>',
    left to '<',
)

val nextDir = mutableMapOf(
    up to right,
    right to down,
    down to left,
    left to up
)

val prevDir = mutableMapOf(
    right to up,
    down to right,
    left to down,
    up to left,
)

private operator fun Pair<Int, Int>.plus(dir: Direction): Point {
    return Point(this.row + dir.row, this.column + dir.column)
}

private fun <T> include(grid: List<List<T>>, point: Point): Boolean =
    point.row in grid.indices && point.column in grid[0].indices

private fun include(grid: Array<BooleanArray>, point: Point): Boolean = grid[point.row][point.column] ?: false

private operator fun <T> List<List<T>>.contains(point: Point) = include(this, point)
private operator fun Array<BooleanArray>.contains(point: Point) = include(this, point)


fun solveSixteenDayFirstStar() {

//    val grid = getGrid("test")

//    grid.forEach(::println)
//    val startPoint = findChar(grid, start) ?: throw Error("start point not found")
//    val endPoint = findChar(grid, end)
//    val path = mutableListOf<Point>(startPoint)
//    val paths = mutableListOf<List<Pair<Int, Int>>>()
//    val visited = Array(grid.size) { BooleanArray(grid[0].size) { false } }
//    visited[startPoint.row][startPoint.column] = true
//    findAllPaths(grid, startPoint,up, path, paths, visited,0)
//    grid.forEach(::println)
//    println(paths)
//
//    solve("src1")
    solveWithAStar("test1")
}

fun solve(name: String) {
    val grid = getGrid(name)
    val startPoint = findChar(grid, start) ?: throw Error("start point not found")
//    val endPoint = findChar(grid, end)
    val path = mutableListOf<Point>(startPoint)
    val paths = mutableListOf<List<Pair<Int, Int>>>()
    val visited = Array(grid.size) { BooleanArray(grid[0].size) { false } }
    visited[startPoint.row][startPoint.column] = true
    val s = findAllPaths(grid, startPoint, right, path, paths, visited,0)
//    grid.forEach(::println)
//    println(paths)
    println(s)
//
}

fun solveWithAStar(name: String) {
    val grid = getGrid(name)
    val startPoint = findChar(grid, start) ?: throw Error("start point not found")
    val endPoint = findChar(grid, end) ?: throw Error("start point not found")
    val result = astar(grid,startPoint, right,endPoint )
    println(result)
}

fun getGrid(name: String) = File("src/days/sixteen/$name.txt")
    .readLines()
    .map { it.toCharArray().toMutableList() }

fun findChar(grid: MutableGrid, symbol: Char): Point? {
    for (row in grid.indices) {
        for (column in grid[row].indices) {
            if (grid[row][column] == symbol) {
                return row to column
            }
        }
    }
    return null
}

fun findAllPaths(
    grid: MutableGrid,
    currentPoint: Point,
    currentDir: Direction,
    path: MutableList<Point>,
    paths: MutableList<List<Pair<Int, Int>>>,
    visited: Array<BooleanArray>,
    score: Int
): Int{
    val currentChar = grid[currentPoint.row][currentPoint.column]

    if (currentChar == end) {
        paths += path.toList()
//        println(score)
        return score
    }

    val score1 = moveInDirection(grid,currentPoint+currentDir,currentDir,path,paths,visited,score +1)
    val score2 = moveInDirection(grid,currentPoint + nextDir[currentDir]!!,nextDir[currentDir]!!,path,paths,visited,score + 1000 +1)
    val score3 = moveInDirection(grid,currentPoint + prevDir[currentDir]!!, prevDir[currentDir]!!,path,paths,visited,score + 1000 +1)
    return minOf(score1,score2,score3)
}

fun isSafe(point: Point, grid: Grid, visited: Array<BooleanArray>) =
    point in grid &&
            !visited[point.row][point.column] &&
            grid[point.row][point.column] != wall &&
            grid[point.row][point.column] != start

fun moveInDirection(grid: MutableGrid,newPoint: Point,dir: Direction, path: MutableList<Point>, paths: MutableList<List<Pair<Int, Int>>>, visited: Array<BooleanArray>,score: Int): Int {
    println(newPoint)
    if (isSafe(newPoint, grid, visited)) {
        visited[newPoint.row][newPoint.column] = true
        path.add(newPoint)

        val s = findAllPaths(grid, newPoint, dir,path, paths, visited,score)

        visited[newPoint.row][newPoint.column] = false
        path.removeLast()
        return s
    }

    return Int.MAX_VALUE
}

//fun getDirections(dir: Direction) = directions.filterNot { it == directionOpposite[dir] }

fun astar(grid: MutableGrid, start: Point,dir: Direction, end: Point): Int? {
    val frontier = PriorityQueue<Pair<Pair<Point,Direction>,Int>>(compareBy { it.second})
    frontier.add((start to right) to 0)
    val cameFrom = mutableMapOf<Point, Point?>(start to null)
    val costSoFar = mutableMapOf<Point, Int>(start to 0)


    while(frontier.isNotEmpty()) {
        val (pd,_) = frontier.remove()
        val (currentPoint, currentDir) = pd
        if(currentPoint == end) {
            break
        }

        for ((npd,cost) in getNeighbors(grid,currentPoint,currentDir)) {
            val (nextPoint,nextDir) = npd
            val newCost = costSoFar.getOrDefault(currentPoint,0) + cost
            if(nextPoint !in cameFrom || newCost < costSoFar[nextPoint]!!) {
                costSoFar[nextPoint] = newCost
                frontier.add((nextPoint to nextDir) to newCost)
                cameFrom[nextPoint] = currentPoint
            }
        }
    }

    if(cameFrom[end] == null) {
        return null
    }

    var current = end;
    val path = mutableListOf<Point>()
    while(current != start) {
        path.add(current)
        current = cameFrom[current]!!
    }
    path += start
    path.reverse()

//    println(path)
    return costSoFar[end]!!
}

fun getNeighbors(grid: Grid, point: Point, dir: Direction): List<Pair<Pair<Point,Direction>,Int>> {
    val neighbors = mutableListOf<Pair<Pair<Point,Direction>,Int>>()

    val straight = point + dir

    val leftDir = prevDir[dir]!!
    val left = point + leftDir

    val rightDir = nextDir[dir]!!
    val right = point + rightDir

    if(straight in grid && grid[straight.row][straight.column] != wall) {
        neighbors += (straight to dir) to 1
    }

    if(left in grid && grid[left.row][left.column] != wall) {
        neighbors += (left to leftDir) to 1001
    }

    if(right in grid && grid[right.row][right.column] != wall) {
        neighbors += (right to rightDir) to 1001
    }

    return neighbors
}

fun graphCost(currentPoint: Point, currentDir: Direction, nextPoint: Point, nextDir: Direction): Int {
    return 1
}