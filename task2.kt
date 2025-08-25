// https://open.kattis.com/problems/citizenship
const val YEAR = 365
fun main() {
    val fromStart = listOf(31, 28, 31, 30, 31, 30,
        31, 31, 30, 31, 30, 31).runningFold(0, Int::plus)
    val (n, rY, rD) = readln().split(" ").map { it.toInt() }
    fun date(s: String): Int {
        val (y, m, d) = s.split("-").map { it.toInt() }
        return y * YEAR + fromStart[m - 1] + d - 1
    }
    val out = IntArray((5000 + rY + 2) * YEAR)
    val ys = IntArray(out.size)
    repeat(n) {
        val (d1, d2) = readln().split(" ").map { date(it) }
        out.fill(1, d1, d2 + 1)
    }
    val last = out.lastIndexOf(1)
    var curDays = YEAR
    for (i in out.indices) {
        ys[i] = ys.getOrElse(i - YEAR) { 0 } + 1
        if (curDays < rD) ys[i] = 0
        curDays += out.getOrElse(i - YEAR) { 0 }
        curDays -= out[i]
    }
    val date = (last + 1 until ys.size).first { ys[it] >= rY }
    val y = date / YEAR
    val m = fromStart.indexOfLast { it <= date % YEAR }
    val d = date % YEAR - fromStart[m] + 1
    println("%04d-%02d-%02d".format(y, m + 1, d))
}
