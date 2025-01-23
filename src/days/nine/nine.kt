import java.util.*
import kotlin.NoSuchElementException

fun solveNinethDayFirstStar() {
    val input = "2333133121414131402".split("").filter { it.isNotEmpty() }.map { it.toInt() };

    println(input)

//    val spacing3 = mapIntToSpacing(3)
//    println(spacing3)
//    val spacing0 = mapIntToSpacing(0)
//    println(spacing0)
//    val spacing5 = mapNumberToNList(5,'0')
//    println(spacing5)
    val blockMap = mapDiskMapToBlocks(input)
    println(blockMap)


    val movedBlocks = amphipod(blockMap)
    println(movedBlocks)

}

// format Option + CMD + L
//fun <T>mapNumberToNList(number: Int, id: T): List<T> =  (0 ..< number).map { id }
//fun mapIntToSpacing(number: Int): List<Char> = mapNumberToNList(number,'.')
fun mapDiskMapToBlocks(diskMap: List<Int>): List<String> =
    diskMap.flatMapIndexed { index, block ->
        when (index % 2) {
            0 -> List(block) { "${index / 2}" }
            else -> List(block) { "." }
        }
    }

//fun mapBlocksToIndexMap(blocks: List<String>) = blocks
//    .mapIndexed { index, block ->
//        when (block) {
//            "." -> index
//            else -> -1
//        }
//    }.filter { it != -1 }

fun amphipod(blocks: List<String>): List<String> {
    val blocksMutable = blocks.toMutableList()
    var fromStart = 0
    var fromEnd = blocksMutable.size - 1

    while(fromStart < fromEnd) {
        if(blocksMutable[fromStart] == ".") {
            fromEnd = blocksMutable.getLastBlockIndex()
            Collections.swap(blocksMutable,fromStart,fromEnd)
        }
        fromStart++;
    }
    return blocksMutable.toList()
}

fun MutableList<String>.getLastBlockIndex(index: Int = this.size - 1): Int {
    if (this.isEmpty()) throw NoSuchElementException("List is empty")

    if(this[index] != ".")
        return index
    else {
        if (index == 0) throw NoSuchElementException("No valid block found")
        return this.getLastBlockIndex(index -1)
    }
}


