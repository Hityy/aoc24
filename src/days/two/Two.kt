package days.two

import java.io.File
import kotlin.math.abs

fun solveSecondDayFirstStar() {
    val result =
        File("src/days/two/src1.txt")
            .readLines()
            .map { it.split(" ").map { it.toInt() } }
            .filter { isLevelValidByRules(it) && isLevelIncOrDec(it) }
            .size


    println("Result: ")
    println(result)
}

fun solveSecondDaySecondStar() {
    val source = getLevelsList()
    val levelsValid = source.filter { isLevelValid(it) }
    val levelsInValid = source.filter { !isLevelValid(it) }

    // bruteforce
    val levelsRepaired = levelsInValid.mapNotNull { level ->
        singleMatchIndexed(level) { isLevelValid(level.remove(it)) }
    }

    println(levelsRepaired.size + levelsValid.size)
}

fun singleMatchIndexed(list: List<Int>, predicate: (Int) -> Boolean): Boolean? {
    for (index in list.indices) {
        if (predicate(index)) {
            return true
        }
    }
    return null
}

fun isLevelValid(level: List<Int>) = isLevelValidByRules(level) && isLevelIncOrDec(level)

fun getLevelsList(): List<List<Int>> {
    return File("src/days/two/src1.txt")
        .readLines()
        .map { it.split(" ").map { it.toInt() } }
}

fun isLevelIncOrDec(list: List<Int>) = list
    .zipWithNext { curr, next -> curr > next }
    .let { it.all { it } || it.none { it } }

fun isLevelValidByRules(level: List<Int>): Boolean = level
    .withIndex()
    .zipWithNext().all { (curr, next) ->
        when {
            abs(curr.value - next.value) > 3 -> false
            curr.value == next.value -> false
            else -> true
        }
    }

fun <T> List<T>.remove(index: Int): List<T> {
    if (index !in this.indices) {
        throw IndexOutOfBoundsException()
    }

    return this.filterIndexed { i, _ -> i != index }
}

//fun tests() {
//    println("--- TESTS ---");
//    println("--- Numbers are decreasing");
//    val testData1 = listOf(7, 6, 4, 2, 1)
//    val testData2 = listOf(1, 2, 7, 8, 9)
//    val testData3 = listOf(9, 7, 6, 2, 1)
//    val testData4 = listOf(1, 3, 2, 4, 5)
//    val testData5 = listOf(8, 6, 4, 4, 1)
//    val testData6 = listOf(1, 3, 6, 7, 9)
//    val outputtest1 = isValidSequence(testData1)
//    val outputtest2 = isValidSequence(testData2)
//    val outputtest3 = isValidSequence(testData3)
//    val outputtest4 = isValidSequence(testData4)
//    val outputtest5 = isValidSequence(testData5)
//    val outputtest6 = isValidSequence(testData6)
//
//    println(outputtest1)
//    println(outputtest2)
//    println(outputtest3)
//    println(outputtest4)
//    println(outputtest5)
//    println(outputtest6)
//}

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

//fun mapLevelListToValidNotNull(levelList: List<Int>): Int? {
//
//    if (isLevelValidByRules(levelList) && isLevelIncOrDec(levelList)) {
//        return 1
//    }
//
//    var isInc = levelList[1] > levelList[0]
//    var corruptedLevelIndex: Int? = null
//    for (index in 0..<levelList.lastIndex) {
//        val currentLevel = levelList[index]
//        val nextLevel = levelList[index + 1]
//
//        if (nextLevel > currentLevel != isInc) {
//            if (corruptedLevelIndex != null) {
//                return null
//            }
//            isInc = !isInc
//            corruptedLevelIndex = index
//            continue
//        }
//
//        if ((abs(currentLevel - nextLevel) > 3) || currentLevel == nextLevel) {
//            if (corruptedLevelIndex != null) {
//                return null
//            }
//
//            corruptedLevelIndex = index
//        }
//
//    }
//
//    if (corruptedLevelIndex != null) {
////        println(levelList)
////        println(" do usuniecia ${levelList[corruptedLevelIndex]}")
//
//        val levelListWithoutFirstCorrupted = levelList.toMutableList()
//        levelListWithoutFirstCorrupted.removeAt(corruptedLevelIndex)
//        if (isLevelValidByRules(levelListWithoutFirstCorrupted) && isLevelIncOrDec(levelListWithoutFirstCorrupted)) {
//            return 1
//        }
//
//        val nextIndex = corruptedLevelIndex + 1
//        val levelListWithoutSecondCorrupted = levelList.toMutableList()
//        if (nextIndex in levelListWithoutSecondCorrupted.indices) {
////            println("before second $levelListWithoutSecondCorrupted")
//            levelListWithoutSecondCorrupted.removeAt(nextIndex)
////            println("after second $levelListWithoutSecondCorrupted")
//
//            if (isLevelValidByRules(levelListWithoutSecondCorrupted) && isLevelIncOrDec(levelListWithoutSecondCorrupted)) {
//                return 1
//            }
//        }
//
//        return null
//    }
//
//    return 1
//
//}
//
