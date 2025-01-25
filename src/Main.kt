fun main() {
//    solveFirstStar()
//    solveSecondDayFirstStar()
//    val input = "ABC".toCharArray()
//    permute(input, 0)
//    solveNinethDayFirstStar()
    solveElevenDayFirstStar()
}


fun permute(chars: CharArray, start: Int) {
    if (start == chars.size - 1) {
        println(chars.joinToString(""))
        return
    }

    for (i in start until chars.size) {
        chars.swap(start, i)  // Zamieniamy miejscami
        permute(chars, start + 1)  // Rekurencja
        chars.swap(start, i)  // Cofamy zmianę (backtrack)
    }
}

fun CharArray.swap(i: Int, j: Int) {
    val temp = this[i]
    this[i] = this[j]
    this[j] = temp
}
