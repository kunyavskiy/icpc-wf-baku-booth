// https://open.kattis.com/problems/bingoforthewin
fun main() {
    fun readInts() = readln().split(" ").map { it.toInt() }
    val (n, m) = readInts()
    val total = (n * m).toDouble()
    val data = List(n) { readInts().reversed() }
    val used = mutableSetOf<Int>()
    val counts = data.flatten().groupingBy { it }.eachCount()
    val result = data
        .map { l -> l.filter { used.add(it) } }
        .map { l -> l.sumOf { counts[it]!! } / total }
    println(result.reversed().joinToString("\n"))
}

