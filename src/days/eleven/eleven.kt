import kotlin.math.log10
import kotlin.math.pow

fun solveElevenDay() {
    val test1 = "0 1 10 99 999"
    val test2 = "125 17"
    val src1 = "814 1183689 0 1 766231 4091 93836 46"

    val stones = parseInput(src1).let { getStoneFrequenciesMap(it, 75).values.sum() }
    println(stones)
}

fun parseInput(input: String) = input.split(" ").map { it.toLong() }

fun getStoneFrequenciesMap(stones: List<Long>, repeatTimes: Int): Map<Long, Long> {
    val frequency = stones.associateWith { 1L }.toMutableMap()
    println(frequency)

    for(blink in  0 ..< repeatTimes) {
        val frequencyInOneBlink = mutableMapOf<Long, Long>()

        for((stone,count) in frequency) {
            when {
                stone == 0L -> {
                    frequencyInOneBlink[1L] = frequencyInOneBlink.getOrDefault(1L, 0) + count
                }

                isEven(stone) -> {
                    val (left, right) = splitStone(stone)

                    frequencyInOneBlink[left] = frequencyInOneBlink.getOrDefault(left, 0) + count
                    frequencyInOneBlink[right] = frequencyInOneBlink.getOrDefault(right, 0) + count
                }

                else -> {
                    val newStone = stone * 2024
                    frequencyInOneBlink[newStone] = frequencyInOneBlink.getOrDefault(newStone, 0) + count
                }
            }
        }
        frequency.clear()
        frequency.putAll(frequencyInOneBlink)
    }
    return frequency
}


fun isEven(stone: Long): Boolean {
    return calculateLength(stone) % 2 == 0
}

fun calculateLength(number: Long): Int {
    return if (number == 0L) 1 else (log10(number.toDouble()).toInt() + 1)
}

fun splitStone(number: Long): Pair<Long, Long> {
    val half = calculateLength(number) / 2
    val left = (number / (10.0.pow(half))).toLong()
    val right = (number % (10.0.pow(half))).toLong()

    return left to right
}