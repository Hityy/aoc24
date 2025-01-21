package days.seven

import java.io.File

fun solveSeventhDayFirstStar() {
    val result =
        File("src/days/seven/src1.txt")
            .readLines()
            .map { it.split(": ")
                .let { it.first().toLong() to it.drop(1).first().split(" ").map { it.toLong() } }
            }
            .sumOf { (first,second) ->
                if(canListBeEvaluatedToValue(second.drop(1),second.first(),first)) first
                else 0
            }

    println(result)
}

fun canListBeEvaluatedToValue(list: List<Long>,currentValue: Long,searchValue: Long): Boolean {
    if(currentValue == searchValue)
        return true

    if(list.isEmpty())
        return false

    val nextList = list.drop(1)
    val lastItem = list.first()

    val resultByMultiply = canListBeEvaluatedToValue(nextList,currentValue + lastItem, searchValue)
    val resultByAddition = canListBeEvaluatedToValue(nextList, currentValue * lastItem, searchValue)

    return resultByMultiply || resultByAddition
}

