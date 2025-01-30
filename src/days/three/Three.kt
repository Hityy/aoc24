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
    val input = File("src/days/three/src1.txt")
        .readText()

    val res = """mul\(\d+,\d+\)""".toRegex()
        .findAll(input)
        .map {
            """\d+""".toRegex(RegexOption.MULTILINE)
                .findAll(it.value)
                .map { a -> a.value }
                .map { a -> a.toLong() }
                .toList()
        }
        .map { it[0] * it[1]}
        .sum()

    println(res)
    println("end")
}

// 34202765 too low
// 93922240 too low

fun solveSecondStarThirdDay() {
    val input = File("src/days/three/src1.txt")
        .readText().trimMargin()

    val dontRanges = """don't\(\)""".toRegex().findAll(input).map { it.range.start }.toList().reversed().toMutableList()
    val doRanges = """do\(\)""".toRegex().findAll(input)
        .map { it.range.endInclusive }
        .toList()
        .reversed()

    val zipRanges = mutableListOf<Pair<Int,Int>>()
    for(doEnd in doRanges) {
        for(dontStart in dontRanges) {
            if(dontStart <= doEnd) {
                zipRanges += dontStart to doEnd
                break;
            }
        }


    }

    var currentInput = input
    for((dontStart,doEnd) in zipRanges) {
//        println("${dontRange.start} until ${doRange.last}")
//        val test = currentInput.substring(dontStart..doEnd)
//        println(test)
//        println(" ${test.length}, ${doEnd-dontStart +1 }, ${ test.length == ".".repeat(doEnd-dontStart +1).length}")
        currentInput = currentInput.replaceRange(dontStart ..doEnd,".".repeat(doEnd-dontStart +1))

    }

    println(currentInput)


//    val input = """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"""

//    val replace = """don't\(\)(.+)do\(\)""".toRegex(RegexOption.MULTILINE).findAll(input).map { it.groups }
//    replace.forEach {
//        println(it)
//    }
//    println(replace)
//        .replace(input) { "" }

//    println(replace)
//return
    val res = """mul\(\d+,\d+\)""".toRegex()
        .findAll(currentInput)
        .map {
            """\d+""".toRegex()
                .findAll(it.value)
                .map { a -> a.value }
                .map { a -> a.toLong() }
                .toList()
        }
//        .map {
//            println(it)
//            it
//        }
        .map { it[0] * it[1]}
        .sum()

    println(res)
    println("end")
}