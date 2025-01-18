package days.one

import java.io.File
import kotlin.math.abs


fun solveFirstStar() {
    val lines =
        File("src/days/one/src1.txt")
            .readLines()
                .map {
            val (left, right) = it.split("   ")
            Pair(left.toLong(), right.toLong())
        }
            .fold(Pair(mutableListOf<Long>(), mutableListOf<Long>())) {
                    (leftList, rightList),
                    (leftValue, rightValue) ->
                leftList.add(leftValue)
                rightList.add(rightValue)
                Pair(leftList, rightList)
            }
            .let { (first, second) -> Pair(first.sorted(), second.sorted()) }
            .let { (first, second) -> first.zip(second) }
            .map { (first, second) -> abs(first - second) }
            .sum()

    // val lines = File("./src1.txt")
    // .readLines()
    // .map {
    //     val (left, right) = it.split("   ")
    //     left.toLong() to right.toLong()
    // }
    // .unzip()  // Rozdziela na dwie listy
    // .let { (leftList, rightList) ->
    //     leftList.sorted() to rightList.sorted()  // Sortowanie list w jednym kroku
    // }
    // .let { (sortedLeft, sortedRight) ->
    //     sortedLeft.zip(sortedRight)  // Zipowanie dwóch list
    // }
    // .map { (first, second) -> abs(first - second) }  // Liczenie różnicy
    // .sum()  // Sumowanie wyników

    // println(lines)

    // val lines = File("./src1.txt")
    // .readLines()
    // .map {
    //     val (left, right) = it.split("   ")
    //     left.toLong() to right.toLong()
    // }
    // .sortedBy { it.first }  // Sortowanie po pierwszym elemencie (left)
    // .sortedBy { it.second } // Sortowanie po drugim elemencie (right)
    // .map { (first, second) -> abs(first - second) }  // Liczenie różnicy
    // .sum()  // Sumowanie wyników

    println(lines)
}
