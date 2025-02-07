package days.sixteen2

import column
import row
import java.io.File

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

// 352224 too high
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
    solve("src1")
}

fun solve(name: String) {
    val grid = getGrid(name)
    val startPoint = findChar(grid, start) ?: throw Error("start point not found")
//    val endPoint = findChar(grid, end)
    val path = mutableListOf<Point>(startPoint)
    val paths = mutableListOf<List<Pair<Int, Int>>>()
    val visited = Array(grid.size) { BooleanArray(grid[0].size) { false } }
//    val visited3d = Array(grid.size) { Array(grid[0].size) { mutableSetOf<Direction>() } }
    val visited3d = mutableSetOf<Pair<Point, Direction>>()
    visited[startPoint.row][startPoint.column] = true
    val s = findAllPaths(grid, startPoint, right, visited, 0)
//    grid.forEach(::println)
//    println(paths)
    println(s)
//
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
//    path: MutableList<Point>,
//    paths: MutableList<List<Pair<Int, Int>>>,
    visited: Array<BooleanArray>,
    score: Int,
): Int? {
    val currentChar = grid[currentPoint.row][currentPoint.column]

    if (currentChar == end) {
//        paths += path.toList()
        println(score)
        return score
    }

    val newPoint1 = currentPoint + currentDir
    var score1: Int? = null
    if (isSafe(newPoint1, grid, visited)) {
        visited[newPoint1.row][newPoint1.column] = true
        score1 = findAllPaths(grid, currentPoint + currentDir, currentDir, visited, score + 1)
        visited[newPoint1.row][newPoint1.column] = false
    }
    val newPoint2 = currentPoint + nextDir[currentDir]!!
    var score2: Int? = null
    if (isSafe(newPoint2, grid, visited)) {
        visited[newPoint2.row][newPoint2.column] = true
        score2 = findAllPaths(grid, newPoint2, nextDir[currentDir]!!, visited, score + 1000 + 1)
        visited[newPoint2.row][newPoint2.column] = false
    }
    val newPoint3 = currentPoint + prevDir[currentDir]!!
    var score3: Int? = null
    if (isSafe(newPoint3, grid, visited)) {
        visited[newPoint3.row][newPoint3.column] = true
        score3 = findAllPaths(grid, newPoint3, prevDir[currentDir]!!, visited, score + 1000 + 1)
        visited[newPoint3.row][newPoint3.column] = false
    }


    if(score1 == null && score2 == null && score3 == null) {
        return null
    }

    return minOf(score1 ?: Int.MAX_VALUE,score2?: Int.MAX_VALUE,score3?: Int.MAX_VALUE)
}

fun isSafe(point: Point, grid: Grid, visited: Array<BooleanArray>) =
    point in grid &&
            !visited[point.row][point.column] &&
            grid[point.row][point.column] != wall

//fun moveInDirection(grid: MutableGrid,newPoint: Point,dir: Direction, visited: Array<BooleanArray>,score: Int): Int {
//    println(newPoint)
//    if (isSafe(newPoint, grid, visited)) {
//        visited[newPoint.row][newPoint.column] = true
////        path.add(newPoint)
//
//        val s = findAllPaths(grid, newPoint, dir,visited,score)
//
//        visited[newPoint.row][newPoint.column] = false
////        path.removeLast()
//        return s
//    }
//
//    return Int.MAX_VALUE
//}

//fun getDirections(dir: Direction) = directions.filterNot { it == directionOpposite[dir] }

