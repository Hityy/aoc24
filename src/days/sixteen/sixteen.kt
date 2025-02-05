package days.sixteen
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


private operator fun Pair<Int, Int>.plus(dir: Direction): Point {
    return Point(this.row + dir.row, this.column + dir.column)
}

private fun <T> include(grid: List<List<T>>, point: Point): Boolean =
    point.row in grid.indices && point.column in grid[0].indices

private fun include(grid: Array<BooleanArray>, point: Point): Boolean = grid[point.row][point.column] ?: false

private operator fun <T> List<List<T>>.contains(point: Point) = include(this, point)
private operator fun Array<BooleanArray>.contains(point: Point) = include(this, point)


fun solveSixteenDayFirstStar() {

    val grid = getGrid("test")

    grid.forEach(::println)
    val startPoint = findChar(grid, start) ?: throw Error("start point not found")
    val endPoint = findChar(grid, end)

    val path = mutableListOf<Point>()
    val paths = mutableListOf<Path>()
    findPath(grid,startPoint,up,path,paths)
    grid.forEach(::println)
    println(paths)


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

fun findPath(grid: MutableGrid, currentPoint: Point,dir: Direction, path: MutableList<Point>, paths: MutableList<Path>): Boolean{

    if(currentPoint !in grid) {
        return false
    }

    val currentChar = grid[currentPoint.row][currentPoint.column]

    if(currentChar == wall) {
        return false
    }

    if(currentChar == end) {
        path += currentPoint
        paths += path
        return true
    }

    path += currentPoint
    grid[currentPoint.row][currentPoint.column] = dirsToChar[dir]!!

    for(nextDir in getDirections(dir)) {
        if (findPath(grid, currentPoint + nextDir, nextDir, path, paths)) {
            return true
        }
    }

    grid[currentPoint.row][currentPoint.column] = '.'
    path.removeLast()
    return false
}

fun getDirections(dir: Direction) = directions.filterNot { it == directionOpposite[dir] }