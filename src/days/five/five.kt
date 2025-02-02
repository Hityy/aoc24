package days.five
import java.io.File
import java.util.*

import middle



fun solveFiveDayFirstStar() {

    val (pagesAsString,rulesAsString) =
        File("src/days/five/src1.txt")
            .readLines()
            .map { it.split(',')}
            .partition { it.size > 2 }

    val rules = parseRules(rulesAsString).groupBy({ it.first }, { it.second })
    val res = parsePages(pagesAsString)
        .filter { isPageListCorrect(it, rules) }
        .sumOf { it.middle() ?: 0 }

    println(res)
}

fun solveFiveDaySecondStar() {
    val (pagesAsString,rulesAsString) =
        File("src/days/five/src1.txt")
            .readLines()
            .map { it.split(',')}
            .partition { it.size > 2 }

    val rulesGroupLeftToRight = parseRules(rulesAsString).groupBy({ it.first }, { it.second })
    val rulesGroupRightToLeft = parseRules(rulesAsString).groupBy({ it.second }, { it.first })
    val notCorrect = parsePages(pagesAsString).filter { !isPageListCorrect(it, rulesGroupLeftToRight) }


    notCorrect.forEach{ bubleSortWithRules(it,rulesGroupRightToLeft) }
    val res = notCorrect.sumOf { it.middle() ?: 0 }
    println(res)
}

fun parseRules(rules: List<List<String>>): List<Pair<Int,Int>> = rules.mapNotNull {
    val value = it.first()
    if (value.length > 1) {
        val (first,second) =  value.split("|")
        first.toInt() to second.toInt()
    }
    else null
}

fun parsePages(pages:  List<List<String>>): List<List<Int>> = pages.map { it.map { it.toInt() } }

fun isPageCorrect(current: Int, pages: List<Int>,rules:  Map<Int, List<Int>> ): Boolean {
    val currentRules = rules[current]
    return currentRules != null && currentRules.containsAll(pages)
}

fun isPageListCorrect(pages: List<Int>,rules:  Map<Int, List<Int>>): Boolean {
    for(index in 0 until pages.size - 1 ) {
        val isCorrect = isPageCorrect(pages[index],pages.drop(index+1),rules)
        if(!isCorrect) {
            return false
        }
    }
    return true
}

fun bubleSort(numbers: List<Int>) {
    for(i in numbers.indices) {
        for(j in i + 1 until numbers.size) {
            if(numbers[j] < numbers[i]) {
                Collections.swap(numbers,i,j)
            }
        }
    }
}

fun bubleSortWithRules(pages: List<Int>, rules: Map<Int, List<Int>>) {
    for(i in pages.indices) {
        for(j in i + 1 until pages.size) {
            val pageI = pages[i]
            val pageJ = pages[j]
            val rulesI = rules[pageI]

            if(rulesI != null && pageJ in rulesI) {
                Collections.swap(pages,i,j)
            }
        }
    }
}


// 75,97,47,61,53

//     97 <- 75 -> 29,53,47,
//
//<- 97 -> 13,61,47,29,53,75
//
//        75 <- 47 -> 53,29,13,61
//
//        <- 61 ->
//
