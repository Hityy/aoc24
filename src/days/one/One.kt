package days.one

import java.io.File
import kotlin.math.abs


fun solveFirstDaySecondStar() {
    val (leftList, rightList) =
        File("src/days/one/src1.txt")
            .readLines()
            .map {
                val (left, right) = it.split("   ")
                left.toInt() to right.toInt()
            }
            .unzip()

    val res = leftList.sumOf { l -> l * rightList.count { r -> l == r } }
    println(res)
}

fun solveFirstStar() {
    val (leftList, rightList) =
        File("src/days/one/src1.txt")
            .readLines()
            .map {
                val (left, right) = it.split("   ")
                left.toInt() to right.toInt()
            }
            .unzip()
            .let { (first, second) -> first.sorted() to second.sorted() }

    val res = leftList.mapIndexed { index, l -> abs(l - rightList[index]) }.sum()
    println(res)

}
