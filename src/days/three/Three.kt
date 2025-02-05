import java.io.File

/*
const solver1 = test => test
    .matchAll(/mul\(\d+,\d+\)/gm)
    .toArray().flatMap(r => r[0])
    .map(r => r.matchAll(/\d+/gm).toArray()
    .flatMap(rr => parseInt(rr)))
    .map(a => a[0] * a[1])
    .reduce((a,c) => a+c,0)
 */

fun solveFirstStarThirdDay() {
    val input = File("src/days/three/src1.txt").readText()

    val res = """mul\(\d+,\d+\)""".toRegex()
        .findAll(input)
        .map { atoil(it.value) }
        .sumOf { it[0] * it[1] }

    println(res)
    println("end")
}

fun solveSecondStarThirdDay() {
    val input = File("src/days/three/src1.txt").readText().trimMargin()

    val res = """mul\(\d+,\d+\)""".toRegex()
        .findAll(skipCorrupted(input))
        .map { atoil(it.value) }
        .sumOf { it[0] * it[1] }

    println(res)
    println("end")
}

// mul(123,456) -> listof(123,456)
fun atoil(text: String): List<Int> {
    val buffer = mutableListOf<Int>()
    val iterationBuffer = mutableListOf<Char>()

    for (c in text) {
        if (c in '0'..'9') {
            iterationBuffer += c
        } else if (iterationBuffer.isNotEmpty()) {
            buffer += iterationBuffer.joinToString("").toInt()
            iterationBuffer.clear()
        }
    }
    return buffer
}


fun skipCorrupted(memory: String): String {
    val dontRanges = """don't\(\)""".toRegex().findAll(memory).map { it.range.first }.toList()
    val doRanges = """do\(\)""".toRegex().findAll(memory).map { it.range.last }.toList()

    val zipRanges = mutableListOf<Pair<Int, Int>>()
    for (dontStart in dontRanges) {
        for (doEnd in doRanges) {
            if (dontStart < doEnd) {
                zipRanges += dontStart to doEnd
                break;
            }
        }
    }

    var currentMemory = memory
    for ((dontStart, doEnd) in zipRanges) {
        currentMemory = currentMemory.replaceRange(dontStart..doEnd, ".".repeat(doEnd - dontStart + 1))
    }
    return currentMemory
}


fun containsAll(search: String, text: String): List<IntRange> {
    var lastIndex = text.indexOf(search)
    val buffer = mutableListOf<IntRange>()

    while (lastIndex != -1) {
        buffer += lastIndex..<lastIndex + search.length
        lastIndex = text.indexOf(search, lastIndex + 1)
    }
    return buffer.toList()
}
