import java.io.File

/*
    BA: X+94, Y+34
    BB: X+22, Y+37

    X=8400
    Y=5400

    =>

    n: a, b
    m: c, d

    X= a*n + c*m
    Y= b*n + d*m

     =>

     n = (c*Y-d*X)/(b*c-d*a)
     m = (X-a*n)/c

     c!=0 && b*c-d*a != 0

*/


// 39162 too high
// 39154 too high
// 38742 too high
fun solveThirteenfirstDay() {
//    val res = getInput(path = "src/days/thirteen/src1.txt")
//        .mapNotNull { getButtonPressedCount2(it[0],it[1],it[2],it[3],it[4],it[5]) }
//        .sumOf { (n,m) -> n*3 + m }

    val higher = 10000000000000
    val res = getInput(path = "src/days/thirteen/src1.txt")
        .mapNotNull { getButtonPressedCount2(it[0],it[1],it[2],it[3],it[4]+higher,it[5]+higher) }
        .sumOf { (n,m) -> n*3 + m }

    println(res)
}



fun getInput(path: String): List<List<Int>> {
    return File(path)
        .readLines()
        .flatMap { """\d+""".toRegex().findAll(it).toList().map { it.value.toInt() } }
        .windowed(6,6)
}


fun getButtonPressedCount( a: Int, b:Int, c: Int, d: Int, X: Int, Y: Int): Pair<Int,Int>? {
//    println("$X $Y $a $b $c $d")

    val denominator = (b*c-d*a)
    if(denominator == 0) {
        // b*c-d*a = 0
        if(X == Y)
            return 1 to 1
    }

    if(c == 0) {
        return null
    }
    val n = (c*Y-d*X)/(b*c-d*a)
    val m = (X-a*n)/c

    if(n !in 0.. 100 || m !in 0 .. 100) {
        return null
    }

    //check
    val Xt = a*n + c*m
    val Yt = b*n + d*m

    if(Xt != X) {
        return null
    }

    if(Yt != Y) {
        return null
    }

    return n to m
}

fun getButtonPressedCount2( a: Int, b:Int, c: Int, d: Int, X: Long, Y: Long): Pair<Long,Long>? {
//    println("$X $Y $a $b $c $d")

    val denominator = a*d-b*c
    if(denominator == 0) {
        // a*d = b*c
        if(X == Y)
        return 1L to 1L
    }

    if(c == 0) {
        return null
    }

    val n = (c*Y-d*X)/(b*c-d*a)
    val m = (X-a*n)/c

    if(n < 0 || m<0) {
        return null
    }

    //check
    val Xt = a*n + c*m
    val Yt = b*n + d*m

    if(Xt != X) {
        return null
    }

    if(Yt != Y) {
        return null
    }


    return n to m
}