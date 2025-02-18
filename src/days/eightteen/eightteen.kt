package days.eighteen

import column
import row
import java.io.File
import java.util.*

typealias Point = Pair<Int, Int>
typealias Grid = Array<Array<Char>>

fun solveEighteenDayFirstStar() {
    //    tests()
    val input = getInput("src1")
    val bytesSeq = getBytes(input).take(1024)
    val grid = getGrid(71)
    for (byte in bytesSeq) {
        val (row, column) = byte
        grid[row][column] = '#'
    }
    val path = dijkstra(grid) ?: throw Exception("No path found")
    println(path.size - 1)
}

fun solveEighteenDaySecondStar() {
    val result = binarySearch()
    println(result)
}

fun binarySearch(): Point {
    val input = getInput("src1")
    var found: Point = -1 to -1
    var max = 5000
    var min = 0
    var middle = 0

    while (max - min > 1) {
        middle = (max + min) / 2
        val (result, lastPoint) = arrangeBytesInMemoryFor(middle, input)
        found = lastPoint
        if (result) {
            min = middle
        } else {
            max = middle
        }
    }

    return found
}

fun arrangeBytesInMemoryFor(n: Int, input: List<String>): Pair<Boolean, Point> {
    val bytesSeq = getBytes(input).take(n)
    val grid = getGrid(71)
    var lastByte: Point = 0 to 0
    for (byte in bytesSeq) {
        val (row, column) = byte
        grid[row][column] = '#'
        lastByte = row to column
    }

    val path = dijkstra(grid)
    return if (path != null) {
        true to lastByte
    } else {
        false to lastByte
    }
}

fun countSteps(grid: Grid): Int {
    var count = 0;
    for (row in grid.indices) {
        for (column in grid[row].indices) {
            if (grid[row][column] == 'O') {
                count++
            }
        }
    }
    return count
}

fun countTestGrid() {
    val g = File("src/days/eightteen/testResult.txt")
        .readLines()
        .sumOf { line -> line.count { it == 'O' } }

    println("TEST GRID: $g")
}

fun getInput(name: String) = File("src/days/eightteen/$name.txt").readLines()

fun getBytes(input: List<String>): Sequence<Point> {
    return input.asSequence()
        .map { line -> line.split(',') }
        .map { l -> l[1].toInt() to l[0].toInt() }
}

fun getGrid(length: Int): Array<Array<Char>> {
    return Array(length) { Array(length) { '.' } }
}

val up = -1 to 0
val right = 0 to 1
val down = 1 to 0
val left = 0 to -1

val directions = listOf(up, right, down, left)

fun dijkstra(grid: Array<Array<Char>>): List<Point>? {
    val start = 0 to 0
    val end = grid.size - 1 to grid[0].size - 1

    val priorityQueue = PriorityQueue<Pair<Point, Int>>(compareBy({ it.second }))
    priorityQueue.add(start to 0)
    val costSoFar = mutableMapOf<Point, Int>()
    val cameFrom = mutableMapOf<Point, Point>()

    while (priorityQueue.isNotEmpty()) {
        val (currentPoint, _) = priorityQueue.remove()
        if (currentPoint == end) {
            break
        }

        for (dir in directions) {
            val nextPoint = currentPoint + dir
            val newCost = costSoFar.getOrDefault(currentPoint, 0) + 1
            if (nextPoint !in grid || grid[nextPoint] == '#') {
                continue
            }
            if (nextPoint !in cameFrom || newCost < costSoFar[nextPoint]!!) {
                costSoFar[nextPoint] = newCost
                cameFrom[nextPoint] = currentPoint
                priorityQueue.add(nextPoint to newCost)
            }
        }
    }

    if (cameFrom[end] == null) {
        return null
    }

    val path = mutableListOf<Point>()
    var current = end
    while (current != start) {
        path.add(current)
        current = cameFrom[current]!!
    }
    path.add(start)
    return path
}

operator fun Point.plus(other: Point): Point {
    return this.row + other.row to this.column + other.column
}

operator fun Grid.get(p: Point): Char {
    return this[p.row][p.column]
}

operator fun Grid.contains(p: Point): Boolean {
    return p.row >= 0 && p.column >= 0 && p.row < this.size && p.column < this[0].size
}

fun tests() {
    val input = getInput("test")
    val bytesSeq = getBytes(input).take(13)

    val grid = getGrid(7)

    for (byte in bytesSeq) {
        val (row, column) = byte
        grid[row][column] = '#'
    }

    grid.forEach { println(it.joinToString("")) }
    val path = dijkstra(grid) ?: throw Exception("No path found")

    for (p in path) {
        grid[p.first][p.second] = 'O'
    }
    grid.forEach { println(it.joinToString("")) }

    countTestGrid()
}


//
//fun bs(l: List<Int>,s: Int): Int {
//    var min = 0
//    var max = l.size - 1
//
//    println("seek: $s")
//    var middle: Int = 0
//    while(min < max) {
//        middle = (max + min)/2
//        if(s == l[middle]) {
//            return middle
//        }
//
//        if(s > l[middle]) {
//            min = middle
//        } else {
//            max = middle
//        }
//    }
//
//
//    return -1
//}