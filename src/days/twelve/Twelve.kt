import days.six.column
import days.six.row
import java.io.File
import kotlin.math.abs

//private typealias Point = Pair<Int,Int>
private typealias Grid = List<List<Char>>
private typealias Direction = Pair<Int, Int>

fun solveTwelveDayFirstStar() {

    tests()

    val grid = getGrid("src/days/twelve/src1.txt")
    val res = findRegions(grid)
        .map { it.first() to it.size }
        .map { (point,area) -> area*calculatePerimeter(grid,point)}
        .sum()

    println(res)
}

fun solveTwelveDaySecondStar() {

    val grid = getGrid("src/days/twelve/src1.txt")
    val res = findRegions(grid)
        .map { it.first() to it.size }
        .map { (point, area) -> area * calculateAllSides(grid,point) }
        .sum()

    println(res)
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
    /// PERIMETERS
    println("PERIMETERS")

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

    val grid2 = getGrid("src/days/twelve/test1.txt")
    grid2.forEach{ println(it) }

    val testPerimeterO = calculatePerimeter(grid2,Point(0,0,'O'))
    println(testPerimeterO)
    println("testPerimeterO perimeter = 36 ${testPerimeterO == 36}")

    /// REGIONS
    println("REGIONS")
    val visitedC = Array(grid1.size) { BooleanArray(grid1[0].size) { false } }
    val regionsC = findPointsInRegion(grid1,Point(1,2,'C'),visitedC)
    println(regionsC)

    val allRegions = findRegions(grid1)
    allRegions.forEach { println(it) }
    println(allRegions.size)

    // SIDES
    println("SIDES")

//    calculateSides(grid1,Point(0,0,'A'))

    val grid3 = getGrid("src/days/twelve/test3.txt")
    val (horizontal,vertical) = calculateSides(grid3,Point(0,0,'E'))
//    println(points)

    println("STETSA")
//    val levelMinut1 = points.filter { it.row == -1 }
//    val level0 = points.filter { it.row == 0 }
    val level1 = horizontal//.filter { it.first.row == 1 }
        .groupBy { it.second }
        .mapValues { (_,list) -> mergeToSideHorizontal(list.sortedBy { it.first.column }) }

    val level2 = vertical//.filter { it.first.row == 1 }
        .groupBy { it.second }
        .mapValues { (_,list) -> mergeToSideVertical(list.sortedBy { it.first.row }) }

//        .values
//    val level2 = points.filter { it.row == 2 }
//    val level3 = points.filter { it.row == 4 }

//    println(levelMinut1)
//    println(level0)
    println(level1)
    println(level2)

    val res = level1.values.sum() + level2.values.sum()
    println(res)
//    println(level2)
//    println(level3)

//    val test = mergeToSideVertical(listOf(1,4,5,6,8,10,11,12,13).map { Point(it,0,'d') to "as"})
//    println(test)

}

fun calculateAllSides(grid: Grid, point: Point): Int {
    val (horizontal,vertical) = calculateSides(grid,point)

    val level1 = horizontal
        .groupBy { it.second }
        .mapValues { (_,list) -> mergeToSideHorizontal(list.sortedBy { it.first.column }) }

    val level2 = vertical
        .groupBy { it.second }
        .mapValues { (_,list) -> mergeToSideVertical(list.sortedBy { it.first.row }) }

    return level1.values.sum() + level2.values.sum()
}

fun mergeToSideHorizontal(list: List<Pair<Point,String>>): Int {
    var counter = 0
    for(index in 0 until list.size - 1 ) {
            val (p1,_) = list[index]
            val (p2,_) = list[index+1]
//            println("$p1 $p2")
            if(abs(p2.column-p1.column) > 1) {
                counter++
            }
    }
    return counter + 1
}

fun mergeToSideVertical(list: List<Pair<Point,String>>): Int {
    var counter = 0
    for(index in 0 until list.size - 1 ) {
        val (p1,_) = list[index]
        val (p2,_) = list[index+1]
//        println("$p1 $p2")
        if(abs(p2.row-p1.row) > 1) {
            counter++
        }
    }
    return counter + 1
}

fun getGrid(path: String): List<List<Char>> {
    return File(path).readLines().map { it.toCharArray().toList() }
}

val up = -1 to 0
val right = 0 to 1
val down = 1 to 0
val left = 0 to -1
val directions = listOf<Direction>(up, right, down, left)

data class Point(val row: Int, val column: Int, var type: Char) {
//    constructor(point: Point, type: Char): this(point.row,point.column,type)
}

private operator fun Point.plus(dir: Direction): Point {
    return Point(this.row + dir.row, this.column + dir.column, this.type)
}

fun <T> include(grid: List<List<T>>, point: Point): Boolean =
    point.row in grid.indices && point.column in grid[0].indices && grid[point.row][point.column] == point.type

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

    if(point !in grid) {
        return 0
    }
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

    }
    return perimeter
}

fun findRegions(grid: Grid): List<List<Point>> {
    val regions = mutableListOf<List<Point>>()
    val visited = Array(grid.size) { BooleanArray(grid[0].size) { false } }

    // idziemy przez wszystkie nody
    // dfs zeby zaznaczyc odwiedzone -> zwracamy liste odwiedzonych puntkow
    // dodajemy nowa liste do bufera

    for(row in grid.indices) {
        for(column in grid[0].indices) {
            if(!visited[row][column]) {
                val type = grid[row][column]
                regions += findPointsInRegion(grid,Point(row,column,type),visited)
            }
        }
    }

    return regions
}

fun findPointsInRegion(grid: Grid, point:Point, visited: Array<BooleanArray>): List<Point> {

    val stack = ArrayDeque<Point>().also { it + point }
    val points = mutableListOf<Point>()

    while(stack.isNotEmpty()) {
        val currentPoint = stack.removeLast()

        if(currentPoint !in grid) {
            continue
        }

        if(currentPoint in visited) {
            continue
        }

        visited[currentPoint.row][currentPoint.column] = true
        points.add(currentPoint)

        for(dir in directions) {
            stack += currentPoint + dir
        }

    }
    return points
}

// **

// sprawdzamy 4 komorki
// jesli next poza grid + 1
// jeli next jest inny + 1
fun calculateSides(grid: Grid, point: Point): Pair<List<Pair<Point,String>>,List<Pair<Point,String>>> {
    val stack = ArrayDeque<Point>().also { it.add(point) }
    val visited = Array(grid.size) { BooleanArray(grid[0].size) { false } }

    val verticalBuffer = mutableListOf<Pair<Point,String>>()
    val horizontalBuffer = mutableListOf<Pair<Point,String>>()

    val verticalDir = listOf(left,right)
    val horizontalDir = listOf(up,down)

//    if(point !in grid) {
//        return buffer
//    }

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
//        val pointUp = currentPoint + up
//        val pointRight = currentPoint + right
//        val pointDown = currentPoint + down
//        val pointLeft = currentPoint + left


//        println(pointUp !in grid)
//        println(pointUp.type != type)

        for(dir in horizontalDir) {
            val newPoint = currentPoint + dir
            if(newPoint in grid) {
                newPoint.type = grid[newPoint.row][newPoint.column]
                if(newPoint.type != type) {
                    horizontalBuffer += newPoint to "r${newPoint.row}${row}"
                } else if(newPoint !in visited) {
                    stack.add(newPoint)
//                    buffer += newPoint
                }
            } else {
                horizontalBuffer += newPoint to "r${newPoint.row}${row}"
            }
        }

        for(dir in verticalDir) {
            val newPoint = currentPoint + dir
            if(newPoint in grid) {
                newPoint.type = grid[newPoint.row][newPoint.column]
                if(newPoint.type != type) {
                    verticalBuffer += newPoint to "r${newPoint.column}${column}"
                } else if(newPoint !in visited) {
                    stack.add(newPoint)
//                    buffer += newPoint
                }
            } else {
                verticalBuffer += newPoint to "r${newPoint.column}${column}"
            }
        }

//        if(pointUp in grid) {
//            pointUp.type = grid[pointUp.row][pointUp.column]
//            if(pointUp.type != type) {
//                perimeter++
//            } else if(pointUp !in visited) {
//                stack.add(pointUp)
//            }
//        } else {
//            perimeter++
//        }

//        if(pointRight in grid) {
//            pointRight.type = grid[pointRight.row][pointRight.column]
//            if(pointRight.type != type) {
//                perimeter++
//            } else if(pointRight !in visited) {
//                stack.add(pointRight)
//            }
//        } else {
//            perimeter++
//        }

//        if(pointDown in grid) {
//            pointDown.type = grid[pointDown.row][pointDown.column]
//            if(pointDown.type != type) {
//                perimeter++
//            } else if(pointDown !in visited) {
//                stack.add(pointDown)
//            }
//        } else {
//            perimeter++
//        }

//        if(pointLeft in grid) {
//            pointLeft.type = grid[pointLeft.row][pointLeft.column]
//            if(pointLeft.type != type) {
//                perimeter++
//            } else if(pointLeft !in visited) {
//                stack.add(pointLeft)
//            }
//        } else {
//            perimeter++
//        }

    }

//    println(perimeter)
    return horizontalBuffer to verticalBuffer
}

