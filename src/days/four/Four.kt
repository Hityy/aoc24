package days.four
import java.io.File

fun solveFourthDayFirstStar() {

    val result =
        File("src/days/four/src1.txt")
            .readLines()
            .map { it.split("").filter { it != "" }.map { it.first()} }
            .let { countPattern("XMAS",it) }

    println("Result: ")
    println(result.toString())
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

    for(row in grid.indices) {
        for(column in grid[row].indices) {
                if(grid[row][column] == pattern[0]) {
                    for (dir in directions) {
                        val (dr,dc) = dir
                        total += findPattern(row+dr, column+dc, grid, pattern, 1, dir)
                    }
                }
        }
    }
    return total;
}

fun findPattern(row: Int, column: Int, grid: List<List<Char>>,pattern: String, index: Int, dir: Pair<Int,Int> ): Int {

    if (index == pattern.length) {
        return 1
    }

    if (row !in 0..<grid.size || column !in 0..<grid[0].size) {
        return 0
    }

    if(grid[row][column] != pattern[index]) {
        return 0
    }

    return findPattern(row + dir.first, column + dir.second, grid, pattern, index +1, dir)
}


fun tests() {
    println("--- TESTS ---");


}