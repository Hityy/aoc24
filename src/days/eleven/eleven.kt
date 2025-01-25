
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.log10

fun solveElevenDayFirstStar() {
    tests()
    val test1 = "0 1 10 99 999"
    val test2 = "125 17"
    val src1 = "814 1183689 0 1 766231 4091 93836 46"


//    val restest1 = blink1(stones, 25).size
    // **

        val stones = parseInputToStones(src1).map{ it.toLong() }
        println(stones)

        val restest1 = blink1(stones, 75).size
        println(restest1)

}

fun stonesToFlow(stones: List<String>): Flow<Long> = flow {
    stones.forEach { emit(it.toLong()) }
}


fun parseInputToStones(input: String) = input.split(" ")

fun blink1(initalStones: List<Long>, repeat: Int): List<Long> {
    val stonesMutable = initalStones.toMutableList()
    for (r in 0..<repeat) {
        val whereReplace = mutableMapOf<Int,List<Long>>()
        for (stoneIndex in stonesMutable.indices) {
            val stone = stonesMutable[stoneIndex]
            when {
                stone == 0L ->  stonesMutable[stoneIndex] = 1L
                hasEvenDigits(stone) -> {
                    val newStones = split(stone)
                    whereReplace[stoneIndex] = newStones
                }
                else ->  stonesMutable[stoneIndex] = stone * 2024
            }
        }

        if(whereReplace.isNotEmpty()) {
            var i = 0
            for((stoneIndexToReplace,list) in whereReplace.toList()) {
                stonesMutable.removeAt(stoneIndexToReplace + i)
                stonesMutable.addAll(stoneIndexToReplace+i,list)
                i++
            }
            whereReplace.clear()
        }

//        println(stonesMutable)
    }
    return stonesMutable
}
//        stonesStonesMutable = stonesStonesMutable.flatMap {
//            when {
//                it == 0L -> listOf(1L)
//                hasEvenDigits(it) -> split(it)
//                else -> listOf(it * 2024)
//            }
//        }
//    }
//    return stonesStonesMutable
//}

//fun split(stone: String): Flow<String> = flow {
//    stone
//        .chunked(stone.length / 2)
//        .map { it.trimStart('0').ifEmpty { "0" } }
//        .forEach { emit(it.toL) }
//}

//
fun hasEvenDigits(n: Long): Boolean =
    if (n == 0L) false else (log10(n.toDouble()).toInt() + 1) % 2 == 0

fun split(stone: Long): List<Long>  {
    val str = stone.toString()
    return str.chunked(str.length / 2).map { it.toLong() }
}


fun tests() {
    println("--- TESTS ---")
    hasEvenDigitsTests()
}

fun hasEvenDigitsTests() {
    println("hasEvenDigitsTests")
    println("0 ${hasEvenDigits(0)}")
    println("12 ${hasEvenDigits(12)}")
    println("123 ${hasEvenDigits(123)}")
    println("1234 ${hasEvenDigits(1234)}")

}
// **

//fun blink2(initalStones: List<String>, repeat: Int): List<String> {
//    var stonesStonesMutable = initalStones
//    for (r in 0 until repeat) {
//        stonesStonesMutable = stonesStonesMutable.flatMap {
//            when {
//                it == "0" -> listOf("1")
//                it.length % 2 == 0 -> split(it)
//                else -> listOf("${it.toLong() * 2024}")
//            }
//        }
//
//    }
//    return stonesStonesMutable
//}


//fun blink(initalStones: List<Long>, repeat: Int): Int {
//
//    var buffer = listOf(initalStones)
//    for (r in 0 until repeat) {
////        var tempBuffer
//        for(chunk in buffer) {
//            val temp = chunk.flatMap {
//                when {
//                    it == 0L -> listOf(1)
//                    hasEvenDigits(it) -> split(it)
//                    else -> listOf(it * 2024)
//                }
//            }
//            if(temp.size > 10000) {
//                val t = temp.chunked(5000)
//            }
//        }
//    }
//    return buffer.sumOf { it.size }
//}
//
//fun split(stone: Long): List<Long>  {
//    val str = stone.toString()
//    return str.chunked(str.length /2 ).map { it.toLong() }
//}
//
//fun hasEvenDigits(n: Long): Boolean = (log10(n.toDouble()).toInt() + 1) % 2 == 0