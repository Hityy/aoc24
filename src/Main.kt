import days.eightteen.solveEightteenDayFirstStar
import days.eightteen.solveEightteenDaySecondStar
import days.sixteen.solveSixteenDayFirstStar

fun main() {
    solveEightteenDaySecondStar()
}

val <T, A> Pair<T, A>.row: T
    get() = this.first

val <T, A>Pair<T, A>.column: A
    get() = this.second

val <T, A> Pair<T, A>.Y: T
    get() = this.first

val <T, A>Pair<T, A>.X: A
    get() = this.second


fun <T> List<T>.middle(): T? {
    if(this.isEmpty()) return null
    val middleIndex = this.size/2
    return this[middleIndex];
}