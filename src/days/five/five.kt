package days.five
import java.io.File

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

    val res = pagesList
        .filter { isPageListCorrect(it, rules) }
        .sumOf { it.middle() ?: 0 }

    println(res)

}

fun <T> List<T>.middle(): T? {
    if(this.isEmpty()) return null
    val middleIndex = this.size
    return this[middleIndex/2];
}


fun isPageCorrect(current: Int, pages: List<Int>,rules:  Map<Int, List<Int>> ): Boolean {
    val currentRules = rules[current]
    return currentRules != null  && currentRules.containsAll(pages)
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
