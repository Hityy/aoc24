package days.fourteen

import days.five.middle
import java.io.File

private typealias Row = Int
private typealias Column = Int
//private typealias X = Int
//private typealias Y = Int
private typealias Velocity = Pair<Column,Row>

private data class Point(var row: Row,var column: Column) {}

// too low 540540
fun solveFourteenDayFirstStar() {
//    tests()

    val res = getSourcePoints("src/days/fourteen/src1.txt")

        .map { (px,py,vx,vy) -> moveRobotInGrid(py,px,vy,vx,100,101,103) }
//        .let { splitRobotsToQuadrants(it,11,7) }
        .let { splitRobotsToQuadrants(it,101,103) }
        .fold(1L) { acc, cur -> acc*cur }

    println(res)

    val res2 = getSourcePoints("src/days/fourteen/src1.txt").groupBy { (px,py,vx,vy) -> vx to vy }.filterValues { it.size > 1 }
    println(res2)


}

private fun tests() {

    val rowIndices = 0..<7
    val columnIndices = 0..<11
    println(rowIndices.toList())
    println(columnIndices.toList())
    println(rowIndices.toList().middle())
    println(columnIndices.toList().middle())

    val p2 = moveRobotInGrid(4,2,-3,2,1,11,7)
    assertEqual(p2,Point(1,4))
    val p3 = moveRobotInGrid(p2.row,p2.column,-3,2,1,11,7)
    assertEqual(p3,Point(5,6))
    val p4 = moveRobotInGrid(p3.row,p3.column,-3,2,1,11,7)
    assertEqual(p4,Point(2,8))
    val p5 = moveRobotInGrid(p4.row,p4.column,-3,2,1,11,7)
    assertEqual(p5,Point(6,10))
    val p6 = moveRobotInGrid(p5.row,p5.column,-3,2,1,11,7)
    assertEqual(p6,Point(3,1))

    getSourcePoints("src/days/fourteen/test.txt")
}

private fun getSourcePoints(path: String): List<List<Int>> {
    return File(path).readLines().map { """[-]?\d+""".toRegex().findAll(it).toList().map { it.value.toInt() } }
}

private fun splitRobotsToQuadrants(robots: List<Point>, width: Int, height: Int): List<Int> {
//    val test0 = robots.filter { it.row == 0 }
//    println(test0)
//
//    val test6 = robots.filter { it.row == 6 }
//    println(test6)
//
//    val test5 = robots.filter { it.row == 5}
//    println(test5)
//
//    val test4 = robots.filter { it.row == 4}
//    println(test4)
//
//    val test3 = robots.filter { it.row == 3}
//    println(test3)

    val widthMiddle = width/2
    val heightMiddle = height/2

    val qc1 = 0..<widthMiddle
    val qc2 = widthMiddle+1 ..<width

    val qr1 = 0..<heightMiddle
    val qr2 = heightMiddle+1..<height
//
//    println(qc1)
//    println(qc2)
//    println(qr1)
//    println(qr2)

    val q1 = mutableListOf<Point>()
    val q2 = mutableListOf<Point>()
    val q3 = mutableListOf<Point>()
    val q4 = mutableListOf<Point>()

   for(robot in robots) {
       when {
           robot.column in qc1 && robot.row in qr1 -> q1 += robot
           robot.column in qc2 && robot.row in qr1 -> q2 += robot
           robot.column in qc1 && robot.row in qr2 -> q3 += robot
           robot.column in qc2 && robot.row in qr2 -> q4 += robot
//           else -> println(robot)
       }
   }

    return listOf(q1.size,q2.size,q3.size,q4.size)
}
private fun moveRobotInGrid(
    intialRow: Row,
    initialColumn: Column,
    velocityRow: Row,
    velocityColumn: Column,
    seconds: Int,
    widthLength: Int,
    hightLength: Int
): Point {
//    val widthLength = 11
//    val hightLength = 7
    // rows //    4 1 5 2 6  3
    // columns // 2 4 6 8 10 1
    var currentPoint = Point(intialRow, initialColumn)
    for(r in 0 until seconds) {
        var nc = currentPoint.column + velocityColumn
        var nr = currentPoint.row + velocityRow

        if(nc < 0) {
            nc = widthLength + nc

        } else if(nc > (widthLength - 1)) {
            nc %= widthLength
        }

        if(nr < 0) {
            nr = hightLength  + nr
        } else if(nr > hightLength -1) {
            nr %= hightLength
        }

        currentPoint.column = nc
        currentPoint.row = nr
    }

    return currentPoint

}

private fun assertEqual(p1: Point, p2: Point) {
    if(p1 != p2) {
        throw Error("FAILED! ${p1} not equal ${p2}")
    }
}
