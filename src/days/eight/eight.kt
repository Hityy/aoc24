package days.eight
import days.five.isPageListCorrect
import days.six.column
import days.six.row
import java.io.File
import kotlin.math.abs

typealias Point = Pair<Int,Int>
typealias Vector = Pair<Int,Int>

class PointC(val row: Int, val column: Int, type: Char) {}

fun solveEighthDayFirstStar() {
    val grid =
        File("src/days/eight/test1.txt")
            .readLines()
            .map { it.split("").filter { it != "" }.map { it.first()} }

    val antennas  = findInGrid(grid) { it != '.' && it != '#'}
    val antiNodes = findInGrid(grid) { it == '#'}
    println("antennas $antennas")
//    println(antiNodes)
//    val pointsPair = antennas[0] to antennas[1]
//    val differenceVector = calculateDifferenceVector(antennas[0] to antennas[1])
//    val antiNodesPair = calculateAnitNodesCoordinates(pointsPair,differenceVector)
//    println(antiNodesPair)

//    val pairwise = collectAllPairwise(listOf(1,2,3,4))
//    println(pairwise)
    println("all expected antinodes = $antiNodes")

    val allPairwisePoints = collectAllPairwisePoints(antennas)
//    println(allPairwisePoints.size)
//    println(allPairwisePoints)

    val res = allPairwisePoints
        .flatMap {
            val diff = calculateDifferenceVector(it)
            calculateAnitNodesCoordinates(it,diff).toList()
        }
        .filter { isPointInGrid(grid,it) }
        .distinct()

    println("Result: $res")
}

fun isPointInGrid(grid: List<List<Char>>, point: Point) =
    point.row in 0 .. grid.size && point.column in 0 .. grid[0].size


fun findInGrid(grid: List<List<Char>>, isChar: (c: Char) -> Boolean): List<Point> =
     grid.foldIndexed(mutableListOf()) { row, acc, cur ->
        acc += cur.mapIndexedNotNull { column, char -> if(isChar(char))  row to column else null }
        acc }

fun calculateDifferenceVector(points: Pair<Point,Point>): Vector {
    val (point1,point2) = points
    val (row1,column1) = point1
    val (row2,column2) = point2
    return (row2 - row1) to (column2 - column1)
}

fun calculateAnitNodesCoordinates(points: Pair<Point,Point>, differenceVector: Vector): Pair<Point,Point> {
    val (point1, point2) = points
    val (row1,column1) = point1
    val (row2,column2) = point2
    val (dr,dc) = differenceVector

    return  (row1 - dr to column1 - dc) to (row2 + dr to column2 + dc)
}

fun collectAllPairwisePoints(points: List<Point>): List<Pair<Point,Point>> =
    points.flatMapIndexed() { index, point1 -> points.drop(index +1 ).map { point1 to it } }

fun collectAllPairwise(list: List<Int>) : List<Pair<Int,Int>> =
    list.flatMapIndexed { index, first -> list.drop(index + 1).map { first to it } }


//fun findAntenas(grid: List<List<Char>>): List<Pair<Int,Int>> {
//    return grid.foldIndexed(mutableListOf()) { row, acc, cur ->
//        acc += cur.mapIndexedNotNull { column, char -> if(char !=  '.' && char != '#')  row to column else null }
//        acc
//    }
//}
//
//fun findAntinodes(grid: List<List<Char>>): List<Pair<Int,Int>> {
//    return grid.foldIndexed(mutableListOf()) { row, acc, cur ->
//        acc += cur.mapIndexedNotNull { column, char -> if(char == '#')  row to column else null }
//        acc
//    }
//}

