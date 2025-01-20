package days.two

import java.io.File
import kotlin.math.abs

// 236 too low
// 597 too high
// 389 too high
// 369 good

fun solveSecondDayFirstStar() {
//    tests()
    val result =
        File("src/days/two/src1.txt")
            .readLines()
            .map { it.split(" ").map { it.toInt() }}
//            .filter { it.zipWithNext { curr, next -> abs(curr - next ) <= 3}.all { it } }
//            .filter { it.zipWithNext { curr, next -> curr != next }.all { it } }
            .filter { it.zipWithNext().all { (curr,next) -> when {
                abs(curr - next ) > 3 -> false
                curr == next -> false
                else -> true
            } } }
            .sumOf { isValidSequence(it) }

    println("Result: ")
    println(result)
}

fun isValidSequence(list: List<Int>) = list
        .zipWithNext { curr,next -> curr > next }
        .let{ it.all { it } || it.none { it } }
        .let { when(it) {
            true -> 1
            false -> 0
        } }

fun tests() {
    println("--- TESTS ---");
    println("--- Numbers are decreasing");
    val testData1 = listOf(7, 6, 4, 2, 1)
    val testData2 = listOf(1, 2, 7, 8, 9)
    val testData3 = listOf(9, 7, 6, 2, 1)
    val testData4 = listOf(1, 3, 2, 4, 5)
    val testData5 = listOf(8, 6, 4, 4, 1)
    val testData6 = listOf(1, 3, 6, 7, 9)
    val outputtest1 = isValidSequence(testData1)
    val outputtest2 = isValidSequence(testData2)
    val outputtest3 = isValidSequence(testData3)
    val outputtest4 = isValidSequence(testData4)
    val outputtest5 = isValidSequence(testData5)
    val outputtest6 = isValidSequence(testData6)

    println(outputtest1)
    println(outputtest2)
    println(outputtest3)
    println(outputtest4)
    println(outputtest5)
    println(outputtest6)
}

/*


fun solveSecondDayFirstStar() {
    tests()

    val result = File("src/days/two/src1.txt")
        .readLines()
        .map { line -> line.split(" ").map(String::toInt) }
        .filter { isValidSequence(it) }
        .sumOf { countValidSequences(it) }

    println("Result: $result")
}

fun isValidSequence(numbers: List<Int>): Boolean {
    return numbers.zipWithNext().all { (curr, next) ->
        abs(curr - next) <= 3 && curr != next
    }
}

fun countValidSequences(list: List<Int>): Int {
    val isConsistent = list.zipWithNext().all { (curr, next) -> curr > next } ||
                       list.zipWithNext().none { (curr, next) -> curr > next }

    return if (isConsistent) 1 else 0
}
 */