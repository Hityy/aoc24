package days.five
import java.io.File
import java.util.*

fun solveFiveDayFirstStar() {

    val (pagesAsString,rulesAsString) =
        File("src/days/five/src1.txt")
            .readLines()
            .map { it.split(',')}
            .partition { it.size > 2 }

    val rules = rulesAsString
        .mapNotNull {
            val value = it.first()
            if (value.length > 1) {
                val (first,second) =  value.split("|")
                first.toInt() to second.toInt()
            }
            else null
        }
        .groupBy({ it.first }, { it.second })

    val pagesList = pagesAsString.map { it.map { it.toInt() } }
    println(rules)
    val res = pagesList
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

    val rules = rulesAsString
        .mapNotNull {
            val value = it.first()
            if (value.length > 1) {
                val (first,second) =  value.split("|")
                first.toInt() to second.toInt()
            }
            else null
        }
        .groupBy({ it.first }, { it.second })


    val rules2 = rulesAsString
        .mapNotNull {
            val value = it.first()
            if (value.length > 1) {
                val (first,second) =  value.split("|")
                first.toInt() to second.toInt()
            }
            else null
        }
        .groupBy({ it.second }, { it.first })
//        .groupBy({ it.second }, { it.first })
//{47=[53, 13, 61, 29], 97=[13, 61, 47, 29, 53, 75], 75=[29, 53, 47, 61, 13], 61=[13, 53, 29], 29=[13], 53=[29, 13]}
// {53=[47, 75, 61, 97], 13=[97, 61, 29, 47, 75, 53], 61=[97, 47, 75], 47=[97, 75], 29=[75, 97, 53, 61, 47], 75=[97]}
    val pagesList = pagesAsString.map { it.map { it.toInt() } }
    println(rules)
    println(rules2)
    val notCorrect = pagesList
        .filter { !isPageListCorrect(it, rules) }
//        .sumOf { it.middle() ?: 0 }

//    println(res)

//    val l1 = listOf(75,97,47,61,53)
//    val f1 = l1.first()
//    for(n in l1.indices.drop(1)) {
//        val pivot = 75
//        if(l1[n] in rules[f1]!!) {
////
//        }
//    }
//    val l1 = listOf(75, 47, 61, 53, 29)
//    fun pickRight(first: Int,rest: List<Int>) {
//        val rule = rules[first]!!
//        rule.filter { rest }
//    }
//
//    val test = pickRight(l1.first(),l1.drop(1))

    val right = mapOf(
        3 to 5,
        5 to 3
    )

    val left = mapOf(
        3 to 1
    )


    fun getFromLeft(search: Int): List<Int> {
        return listOf(left[search]!!)
    }

    fun getFromRight(search: Int): List<Int> {
        return listOf(right[search]!!)
    }

    fun getTest(search: Int): List<Int> {
        return getFromLeft(search) + listOf(3) + getFromRight(search)
    }

    val te = getTest(3)
    println(te)


    val numbers = listOf(10,5,3323,2,1)
    println(numbers)

    fun bubleSort() {
        for(i in numbers.indices) {
            for(j in i + 1 until numbers.size) {
                if(numbers[j] < numbers[i]) {
                    Collections.swap(numbers,i,j)
                }
            }
        }
    }

    val list = listOf(75,97,47,61,53)
//    val list = listOf(97,13,75,29,47)
    fun bubleSortWithRules(pages: List<Int>) {
        for(i in pages.indices) {
            for(j in i + 1 until pages.size) {
                val pageI = pages[i]
                val pageJ = pages[j]
                val rulesI = rules2[pageI]

                if(rulesI != null && pageJ in rulesI) {
                    Collections.swap(pages,i,j)
                }
            }
        }
    }
    println(list)
    bubleSortWithRules(list)
    println(list)

    notCorrect.forEach{ bubleSortWithRules(it) }
    val test = notCorrect.sumOf { it.middle() ?: 0 }
    println(test)
    //        .sumOf { it.middle() ?: 0 }


//    bubleSort()
//
//    println(numbers)

}

fun <T> List<T>.middle(): T? {
    if(this.isEmpty()) return null
    val middleIndex = this.size/2
    return this[middleIndex];
}


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


// 75,97,47,61,53

//     97 <- 75 -> 29,53,47,
//
//<- 97 -> 13,61,47,29,53,75
//
//        75 <- 47 -> 53,29,13,61
//
//        <- 61 ->
//
