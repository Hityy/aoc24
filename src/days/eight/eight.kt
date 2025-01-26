package days.eight
import days.six.column
import days.six.row
import java.io.File

typealias Point = Pair<Int,Int>
typealias Vector = Pair<Int,Int>

data class Node(val point: Point,val type: Char) {}
// 338 too high
// 332

fun solveEighthDayFirstStar() {
    val grid =
        File("src/days/eight/src1.txt")
            .readLines()
            .map { it.split("").filter { it != "" }.map { it.first()} }
    
    val res  = findInGrid(grid) { it != '.' && it != '#'}
        .groupBy { it.type }
        .let { collectAllPairwisePoints(it) }
        .mapValues { (_, points) -> points.flatMap {
            val diff = calculateDifferenceVector(it)
            calculateAnitNodesCoordinates(it,diff).toList()
        } }
        .values.flatten()
        .filter { isPointInGrid(grid,it) }
        .distinct()

    println("Result size: ${res.size}")
}

fun isPointInGrid(grid: List<List<Char>>, point: Point) =
    point.row in 0 until grid.size && point.column in 0 until grid[0].size

fun findInGrid(grid: List<List<Char>>, isChar: (c: Char) -> Boolean): List<Node> =
     grid.foldIndexed(mutableListOf()) { row, acc, cur ->
        acc += cur.mapIndexedNotNull { column, char ->
            if(isChar(char))
                Node(row to column,char)
            else
                null
        }
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

fun collectAllPairwisePoints(points: Map<Char,List<Node>>): Map<Char,List<Pair<Point,Point>>> =
    points.mapValues { (type,list) ->
        list.flatMapIndexed() { index, node ->
            list.drop(index +1 ).map { node.point to it.point }
        }
    }

fun collectAllPairwise(list: List<Int>) : List<Pair<Int,Int>> =
    list.flatMapIndexed { index, first -> list.drop(index + 1).map { first to it } }


//    val antiNodes = findInGrid(grid) { it == '#'}
//    val expectedAntinodes = antiNodes.map { it.point }
//    println("all expected antinodes = $expectedAntinodes")
//    println("all expected antinodes size = ${expectedAntinodes.size}")

// **

fun solveEightDaySecondStart() {
    val grid =
        File("src/days/eight/src1.txt")
            .readLines()
            .map { it.split("").filter { it != "" }.map { it.first()} }
    val res  = findInGrid(grid) { it != '.' && it != '#'}
        .groupBy { it.type }
        .let { collectAllPairwisePoints(it) }
        .mapValues { (_, points) -> points.flatMap { pair -> generateAntinodes(pair, calculateDifferenceVector(pair),grid) } }
        .values.flatten()
        .distinct()

    println(res.size)

}

fun generateAntinodes(points: Pair<Point,Point>, differenceVector: Vector, grid: List<List<Char>>) =
            generateAntinodesInDirection(points.first,differenceVector,grid) {(row,col),(dr,dc ) -> (row - dr) to (col - dc)} +
            generateAntinodesInDirection(points.second,differenceVector,grid) {(row,col),(dr,dc ) -> (row + dr) to (col + dc)}


fun generateAntinodesInDirection(point: Point, differenceVector: Vector, grid: List<List<Char>>, calcNewPoint: (point: Point, vector: Vector) -> Point): List<Point> {
    val antinodes = mutableListOf(point)

    while(true) {
        val antinode = calcNewPoint(antinodes.last(),differenceVector)

        if(isPointInGrid(grid,antinode)) antinodes += antinode
        else break
    }
    return antinodes
}
