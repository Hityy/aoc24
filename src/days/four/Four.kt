package days.four

import java.io.File

fun solveFourthDayFirstStar() {
    val result = countPattern("XMAS", getSourceInput("src1"))
    println("Result: ")
    println(result.toString())
}

fun getSourceInput(name: String): List<List<Char>> {
    return File("src/days/four/$name.txt")
        .readLines()
        .map { it.split("").filter { it != "" }.map { it.first() } }
}

fun countPattern(pattern: String, grid: List<List<Char>>): Int {
    var total = 0
    val directions = listOf(
        // proste
        0 to 1, // gora
        1 to 0, // prawo
        0 to -1, // dol
        -1 to 0, // lewo
        // przekatne
        1 to 1, // prawo gora
        1 to -1, // prawo dol
        -1 to 1, // lewo gora
        -1 to -1 // lewo dol
    )

    for (row in grid.indices) {
        for (column in grid[row].indices) {
            if (grid[row][column] == pattern[0]) {
                for (dir in directions) {
                    val (dr, dc) = dir
                    total += findPattern(row + dr, column + dc, grid, pattern, 1, dir)
                }
            }
        }
    }
    return total;
}

fun findPattern(row: Int, column: Int, grid: List<List<Char>>, pattern: String, index: Int, dir: Pair<Int, Int>): Int {

    if (index == pattern.length) {
        return 1
    }

    if (row !in 0..<grid.size || column !in 0..<grid[0].size) {
        return 0
    }

    if (grid[row][column] != pattern[index]) {
        return 0
    }

    return findPattern(row + dir.first, column + dir.second, grid, pattern, index + 1, dir)
}


fun solveSecondStarFourthDay() {
    val res = countPattern2(getSourceInput("src1"))
    println(res)
}


fun countPattern2(grid: List<List<Char>>): Int {
    var total = 0

    for (row in grid.indices) {
        for (column in grid[row].indices) {
            if (grid[row][column] == 'A') {
                val r0 = row - 1
                val r1 = row + 1
                val c0 = column - 1
                val c1 = column + 1

                if (r0 !in grid.indices ||
                    r1 !in grid.indices ||
                    c0 !in grid[0].indices ||
                    c1 !in grid[0].indices) {
                    continue
                }

                val lt = grid[r0][c0]
                val rt = grid[r0][c1]
                val lb = grid[r1][c0]
                val rb = grid[r1][c1]

                if (isDiagonalCorrect(lt, rb) && isDiagonalCorrect(lb, rt)) {
                    total++
                }
            }
        }
    }
    return total;
}

fun isDiagonalCorrect(p1: Char, p2: Char) =
    when {
        p1 == 'M' && p2 == 'S' -> true
        p1 == 'S' && p2 == 'M' -> true
        else -> false
    }

