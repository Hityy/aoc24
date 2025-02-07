package days.seven

import java.io.File

fun solveSeventhDayFirstStar() {
    val result =
        File("src/days/seven/test2.txt")
            .readLines()
            .map {
                it.split(": ")
                    .let { it.first().toLong() to it.drop(1).first().split(" ").map { it.toLong() } }
            }
            .sumOf { (first, second) ->
                if (canListBeEvaluatedToValue(second.drop(1), second.first(), first)) first
                else 0
            }

    println(result)
}
//    +10
//12             120
//    *10

fun canListBeEvaluatedToValue2(list: List<Long>, currentValue: Long, searchValue: Long): Boolean = when {
    currentValue == searchValue -> true
    currentValue > searchValue -> false
    list.isEmpty() -> false
    else -> {
        val nextList = list.drop(1)
        val lastItem = list.first()

        val resultByMultiply = canListBeEvaluatedToValue2(nextList, currentValue + lastItem, searchValue)
        val resultByAddition = canListBeEvaluatedToValue2(nextList, currentValue * lastItem, searchValue)
        resultByMultiply || resultByAddition
    }
}

fun canListBeEvaluatedToValue(list: List<Long>, currentValue: Long, searchValue: Long): Boolean {
    if (currentValue == searchValue)
        return true

    if (currentValue > searchValue) {
        return false
    }

    if (list.isEmpty())
        return false

    val nextList = list.drop(1)
    val lastItem = list.first()

    val resultByMultiply = canListBeEvaluatedToValue(nextList, currentValue + lastItem, searchValue)
    val resultByAddition = canListBeEvaluatedToValue(nextList, currentValue * lastItem, searchValue)


    return resultByMultiply || resultByAddition
}


// **
// 106016739583593 to high

fun solveSeventhDaySecondStar() {

    println("--- TESTS ---")
    val v2 = concat(6 * 8, 6 * 15)
    val v1 = concat(17, 22)
    println("$v1 $v2")

    println("--- SOLUTION ---")
    val result =
        File("src/days/seven/src1.txt")
            .readLines()
            .map {
                it.split(": ")
                    .let { it.first().toLong() to it.drop(1).first().split(" ").map { it.toLong() } }
            }
            .sumOf { (first, second) ->
                if (canListBeEvaluatedToValueWithConcat(second.drop(1), second.first(), first)) first
                else 0
            }

    println(result)
}


fun canListBeEvaluatedToValueWithConcat(list: List<Long>, currentValue: Long, searchValue: Long): Boolean {
    if ((currentValue == searchValue) && list.isEmpty())
        return true

    if (list.isEmpty())
        return false

    val nextList = list.drop(1)
    val lastItem = list.first()

    val resultByMultiply = canListBeEvaluatedToValueWithConcat(nextList, currentValue * lastItem, searchValue)
    val resultByAddition = canListBeEvaluatedToValueWithConcat(nextList, currentValue + lastItem, searchValue)
    val resultByConcat = canListBeEvaluatedToValueWithConcat(nextList, concat(currentValue, lastItem), searchValue)

    return resultByMultiply || resultByAddition || resultByConcat
}

fun concat(value1: Long, value2: Long): Long {
    return ("$value1" + "$value2").toLong()
}