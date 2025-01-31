import days.six.column
import days.six.row

//private typealias Point = Pair<Int,Int>
private typealias Grid = List<List<Char>>
private typealias Direction = Pair<Int, Int>

fun solveTwelveDayFirstStar() {

    tests()
}

fun tests() {

    val grid1 = listOf(
        listOf('A', 'A', 'A', 'A'),
        listOf('B', 'B', 'C', 'D'),
        listOf('B', 'B', 'C', 'C'),
        listOf('E', 'E', 'E', 'C')
    )


//    val gridA = listOf('A', 'A', 'A', 'A')
//    val pointsA = gridA.mapIndexed { index, _ -> 0 to index }
//    println(pointsA)

    grid1.forEach { println(it) }

    val testPerimeterA = calculatePerimeter(grid1,Point(0,0,'A'))
    println("testPerimeterA perimeter = 10 ${testPerimeterA == 10}")

    val testPerimeterB = calculatePerimeter(grid1,Point(1,0,'B'))
    println("testPerimeterB perimeter = 8 ${testPerimeterB == 8}")

    val testPerimeterC = calculatePerimeter(grid1,Point(1,2,'C'))
    println("testPerimeterC perimeter = 10 ${testPerimeterC == 10}")

    val testPerimeterD = calculatePerimeter(grid1,Point(1,3,'D'))
    println("testPerimeterD perimeter = 4 ${testPerimeterD == 4}")

    val testPerimeterE = calculatePerimeter(grid1,Point(3,0,'E'))
    println("testPerimeterE perimeter = 8 ${testPerimeterE == 8}")


}

val up = -1 to 0
val right = 0 to 1
val down = 1 to 0
val left = 0 to -1
val directions = listOf<Direction>(up, right, down, left)

data class Point(val row: Int, val column: Int, var type: Char) {
    constructor(point: Point, type: Char): this(point.row,point.column,type)
}

private operator fun Point.plus(dir: Direction): Point {
    return Point(this.row + dir.row, this.column + dir.column, this.type)
}


fun <T> include(grid: List<List<T>>, point: Point): Boolean =
    point.row in grid.indices && point.column in grid[0].indices
fun include(grid: Array<BooleanArray>, point: Point): Boolean = grid[point.row][point.column] ?: false

private operator fun <T> List<List<T>>.contains(point: Point) = include(this, point)
private operator fun Array<BooleanArray>.contains(point: Point) = include(this, point)




// sprawdzamy 4 komorki
// jesli next poza grid + 1
// jeli next jest inny + 1
fun calculatePerimeter(grid: Grid, point: Point): Int {
    val stack = ArrayDeque<Point>().also { it.add(point) }
    val visited = Array(grid.size) { BooleanArray(grid[0].size) { false } }
    var perimeter = 0

    while (stack.isNotEmpty()) {
        val currentPoint = stack.removeLast()
//        println(currentPoint)
        val (row,column,type) = currentPoint

//        println(currentPoint in visited)
        if(currentPoint in visited) {
            continue
        }

        visited[row][column] = true

        // check if neighbours are in grid
        val pointUp = currentPoint + up
        val pointRight = currentPoint + right
        val pointDown = currentPoint + down
        val pointLeft = currentPoint + left


//        println(pointUp !in grid)
//        println(pointUp.type != type)

        if(pointUp in grid) {
            pointUp.type = grid[pointUp.row][pointUp.column]
            if(pointUp.type != type) {
                perimeter++
            } else if(pointUp !in visited) {
                stack.add(pointUp)
            }
        } else {
            perimeter++
        }

        if(pointRight in grid) {
            pointRight.type = grid[pointRight.row][pointRight.column]
            if(pointRight.type != type) {
                perimeter++
            } else if(pointRight !in visited) {
                stack.add(pointRight)
            }
        } else {
            perimeter++
        }

        if(pointDown in grid) {
            pointDown.type = grid[pointDown.row][pointDown.column]
            if(pointDown.type != type) {
                perimeter++
            } else if(pointDown !in visited) {
                stack.add(pointDown)
            }
        } else {
            perimeter++
        }

        if(pointLeft in grid) {
            pointLeft.type = grid[pointLeft.row][pointLeft.column]
            if(pointLeft.type != type) {
                perimeter++
            } else if(pointLeft !in visited) {
                stack.add(pointLeft)
            }
        } else {
            perimeter++
        }




//          if(pointRight !in grid || pointUp.type != type) {
//            perimeter++
//        }
//          if(pointDown !in grid || pointUp.type != type) {
//            perimeter++
//        }
//          if(pointLeft !in grid || pointUp.type != type) {
//            perimeter++
//        }
//
    }
    return perimeter
}