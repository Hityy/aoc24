import java.io.File
import java.util.*
import kotlin.NoSuchElementException

// format Option + CMD + L

// 6283404596082 too high
// 6283404590840

fun solveNinethDayFirstStar() {
//    val input = "2333133121414131402".split("").filter { it.isNotEmpty() }.map { it.toInt() };
    val res = File("src/days/nine/src1.txt")
        .readLines()
        .first()
        .split("")
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
        .let { mapDiskMapToBlocks(it) }
        .let { amphipod(it) }
        .mapIndexedNotNull { index, it -> if (it != ".") index * it.toLong() else null }
        .sum()

    println(res)
}

fun solveNinethDaySecondStar() {
    val res2 = File("src/days/nine/src1.txt")
        .readLines()
        .first()
        .split("")
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
        .let { mapDiskMapToNodeBlocks(it) }
        .let { amphipodNode(it.toMutableList()) }
        .flatMapIndexed { index, block -> List(block.size) { block.type } }
        .mapIndexedNotNull { index, it -> if (it != ".") index * it.toLong() else null }
        .sum()

    println(res2)
}

fun mapDiskMapToBlocks(diskMap: List<Int>): List<String> =
    diskMap.flatMapIndexed { index, block ->
        when (index % 2) {
            0 -> List(block) { "${index / 2}" }
            else -> List(block) { "." }
        }
    }

fun amphipod(blocks: List<String>): List<String> {
    val blocksMutable = blocks.toMutableList()
    var fromStart = 0

    while (true) {
        if (blocksMutable[fromStart] == ".") {
            val fromEnd = blocksMutable.getLastBlockIndex()
            if (fromStart >= fromEnd)
                break;
            Collections.swap(blocksMutable, fromStart, fromEnd)
        }
        fromStart++;
    }
    return blocksMutable
}

fun MutableList<String>.getLastBlockIndex(): Int {
    if (this.isEmpty()) throw NoSuchElementException("List is empty")

    for (indexFromEnd in this.indices.reversed()) {
        if (this[indexFromEnd] != ".")
            return indexFromEnd
    }
    throw NoSuchElementException("No valid block found")
}

// ---- **

data class Block(val size: Int, val type: String)

fun mapDiskMapToNodeBlocks(diskMap: List<Int>): List<Block> =
    diskMap.mapIndexedNotNull() { index, block ->
        when (index % 2) {
            0 -> if (block > 0) Block(block, "${index / 2}") else null
            else -> if (block > 0) Block(block, ".") else null
        }
    }

fun amphipodNode(blocks: MutableList<Block>): List<Block> = blocks
    .iterateOverBlocksFreeSpace { fromStart ->
        blocks.iterateOverBlocksFiles { fromEnd ->
            (fromStart > fromEnd) || blocks.swapBlocks(fromStart, fromEnd)
        }
    }

fun MutableList<Block>.swapBlocksIfEqual(fromStart: Int, fromEnd: Int): Boolean {
    if (fromStart >= this.size || fromEnd < 0)
        throw IndexOutOfBoundsException()

    Collections.swap(this, fromStart, fromEnd)
    return true
}

fun MutableList<Block>.swapBlocksIfOneGreater(fromStart: Int, fromEnd: Int, diff: Int): Boolean {
    if (fromStart >= this.size || fromEnd < 0)
        throw IndexOutOfBoundsException()

    val fileToSwap = this[fromEnd]

    // usuwamy przestrzen
    this.removeAt(fromStart)
    // dodajemy plik w nowe miejsce
    this.add(fromStart, fileToSwap)
    // wypelniamy pozostalosc przestrzenia
    this.add(fromStart + 1, Block(diff, "."))

    // usuwamy z konca block i dodajemy w jego miejsce przestrzen
    this.removeAt(fromEnd + 1)
    this.add(fromEnd + 1, Block(fileToSwap.size, "."))
    return true
}

fun MutableList<Block>.swapBlocks(fromStart: Int, fromEnd: Int): Boolean {
    if (fromStart >= this.size || fromEnd < 0)
        throw IndexOutOfBoundsException()

    val blockToSwap = this[fromEnd]
    val firstFreeSpace = this[fromStart]
    val diff = firstFreeSpace.size - blockToSwap.size

    return when {
        diff == 0 -> this.swapBlocksIfEqual(fromStart, fromEnd)
        diff > 0 -> this.swapBlocksIfOneGreater(fromStart, fromEnd, diff)
        else -> false
    }
}

fun MutableList<Block>.iterateOverBlocksFreeSpace(cb: (index: Int) -> Unit): MutableList<Block> {
    for (fromStart in this.indices) {
        if (this[fromStart].type == ".") {
            cb(fromStart)
        }
    }
    return this
}


fun MutableList<Block>.iterateOverBlocksFiles(cb: (index: Int) -> Boolean): MutableList<Block> {
    for (fromEnd in this.indices.reversed()) {
        if (this[fromEnd].type != ".") {
            if (cb(fromEnd))
                break
        }
    }
    return this
}


