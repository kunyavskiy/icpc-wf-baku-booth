// https://open.kattis.com/problems/friendlyrivalry
import java.math.*
import kotlin.math.*

fun main() {
    val n = readln().toInt() * 2
    val data = List(n) {
        readln().split(" ").map { it.toLong() }
    }
    val x = data.map { it[0] }.toLongArray()
    val y = data.map { it[1] }.toLongArray()
    val d = Array(n) { i -> LongArray(n) { j->
        (x[i] - x[j]) * (x[i] - x[j]) +
                (y[i] - y[j]) * (y[i] - y[j])
    } }
    val es = buildList {
        val minDV = IntArray(n)
        val minD = Array(n) { d[0][it] }
        minDV[0] = -1
        minD[0] = Long.MAX_VALUE
        repeat(n - 1) {
            val v = (0 until n).minBy { minD[it] }
            add(v to minDV[v])
            minD[v] = Long.MAX_VALUE
            minDV[v] = -1
            for (j in 0 until n) {
                if (minDV[j] != -1 && d[v][j] < minD[j]) {
                    minD[j] = d[v][j]
                    minDV[j] = v
                }
            }
        }
        sortBy { d[it.first][it.second] }
    }

    val teams = Array(n) { mutableListOf(it) }
    fun tryMerge(i: Int, j: Int): Boolean {
        var x = BigInteger.ONE
        val can = teams.indices.any {
            val size = when (it) {
                i, j -> teams[i].size + teams[j].size
                else -> teams[it].size
            }
            if (size != 0) x = x or (x shl size)
            x.testBit(n / 2)
        }
        if (can) {
            teams[i].addAll(teams[j])
            teams[j].clear()
        }
        return can
    }
    val bad = es.first { (a, b) ->
        !tryMerge(
            teams.indexOfFirst { a in it },
            teams.indexOfFirst { b in it }
        )
    }
    println(sqrt(d[bad.first][bad.second].toDouble()))
    for (i in 1 until n) {
        if (teams[i].isNotEmpty())
            tryMerge(0, i)
    }
    println(teams[0].map { it+1 }.joinToString("\n"))
}
