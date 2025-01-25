
import kotlin.math.log10
import kotlin.math.pow

var blinked = 0
fun solveElevenDayFirstStar() {
    tests()
    val test1 = "0 1 10 99 999"
    val test2 = "125 17"
    val src1 = "814 1183689 0 1 766231 4091 93836 46"



        val stones = parseInputToStones(src1).map{ Stone(it.toLong()) }
        println(stones)

        val stonesBlinked25Times = blinkStones1(stones, 15)
        val stonesChunked1 = stonesBlinked25Times.chunked(128)


        val aggregate = mutableListOf<Stone>()
        for(chunk1 in stonesChunked1) {
            val stonesChunked2 = blinkStones1(chunk1,10).chunked(128)
            val aggregate2 = mutableListOf<List<Stone>>()
//            for(chunk2 in stonesChunked2) {
//                aggregate2 += blinkStones1(chunk2,5)
//            }
//            aggregate += aggregate2.flatten()
            aggregate += stonesChunked2.flatten()
        }
        println("blinked $blinked")
        println(aggregate)
        println(aggregate.size)
}


fun parseInputToStones(input: String) = input.split(" ")

data class Stone (
    var number: Long,
) {
    var length: Int = calculateLength(this)
    var isEven = isEven(this)
    var isZero = number == 0L
}


fun multiply(stone: Stone) {
//    if (stone.number > Long.MAX_VALUE / 2024) {
//        throw Error("ZA DUZY KAMIEN")
//    }
    stone.number = stone.number * 2024
    stone.length = calculateLength(stone)
    stone.isEven = isEven(stone)
    stone.isZero = false
}

fun flipStone(stone: Stone) {
    stone.number = 1L
    stone.isZero = false
}

fun isEven(stone:Stone): Boolean {
    return calculateLength(stone) % 2 == 0
}

fun calculateLength(stone: Stone): Int {
    return if (stone.number == 0L) 1 else (log10(stone.number.toDouble()).toInt() + 1)
}

fun splitStone(stone: Stone): List<Stone> {
    val length = stone.length / 2
    val number = stone.number
    val right = (number % (10.0.pow(length ))).toLong()
    val left = (number / (10.0.pow(length))).toLong()
    return listOf(Stone(left),Stone(right))
}

//data class Stone(
//    var  number: Long,
//) {
//    var isEven = hasEvenDigits(number)
//    var isZero = number == 0L
//
//    fun flipZero() {
//        number = 1L
//        isEven = false
//        isZero = false
//    }
//    fun multiply() {
//        if (number > Long.MAX_VALUE / 2024) {
//            throw Error("ZA DUZY NA INT")
//        }
//        number *= 2024
//        isEven = hasEvenDigits(number)
//        isZero = number == 0L
//    }
//
//
//    fun hasEvenDigits(n: Long): Boolean {
//        return if (n == 0L) false else (log10(n.toDouble()).toInt() + 1) % 2 == 0
//    }
//    fun split(): List<Long>  {
//        val str = number.toString()
//        return str.chunked(str.length / 2).map { it.toLong() }
//    }
//
//}


fun blinkStones1(initalStones: List<Stone>, repeat: Int): List<Stone> {
    val stonesMutable = initalStones.toMutableList()
    repeat(repeat, { r ->
        blinked++
        val whereReplace = mutableMapOf<Int,List<Stone>>()
        for (stoneIndex in stonesMutable.indices) {
            val stone = stonesMutable[stoneIndex]
            when {
                stone.isZero -> flipStone(stone)
                stone.isEven -> { whereReplace[stoneIndex] = splitStone(stone) }
                else -> multiply(stone)
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

        println("age: ${r+1} stones: ${stonesMutable.size}")
    })
//    for (r in 0 until repeat) {

//        println(stonesMutable)
//        if(stonesMutable.size > 10000) {
//
//        }
//    }
    return stonesMutable
}

fun blink1(initalStones: List<Long>, repeat: Long): List<List<Long>> {
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

        println(stonesMutable)
        println("age: ${r+1} stones: ${stonesMutable.size}")
    }
    return stonesMutable.chunked(2)
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

    println("splitStoneTests")
    println("12 ${splitStone(Stone(12))}")

}
// **

//fun blink2(initalStones: List<String>, repeat: Long): List<String> {
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


//fun blink(initalStones: List<Long>, repeat: Long): Long {
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
//fun hasEvenDigits(n: Long): Boolean = (log10(n.toDouble()).toLong() + 1) % 2 == 0