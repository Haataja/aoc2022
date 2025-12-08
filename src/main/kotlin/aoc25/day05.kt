package me.haataja.aoc25

import java.io.File

fun main() {

    val path = "src/main/input/aoc25/day05.txt"
    val ids = File(path).readText().split(Regex("(\\r?\\n)"))

    fun part1(ids: List<String>): Long {
        val ranges = ids.filter { id -> id.contains("-") }.map { id ->
            id.split("-")[0].toLong()..id.split("-")[1].toLong()
        }
        return ids.filter { !it.contains("-") && it.isNotBlank() }.sumOf { id ->
            if(ranges.any { range -> range.contains(id.toLong()) }){
                1L
            } else {
                0
            }
        }

    }

    fun part2(ids: List<String>): Long {
        val ranges = ids.filter { id -> id.contains("-") }.map { id ->
            Pair(id.split("-")[0].toLong(),id.split("-")[1].toLong())
        } as MutableList<Pair<Long, Long>>
        var fresh = 0L
        while (ranges.size > 0){
            val pair = ranges.removeFirst()
            var added = pair.second - pair.first + 1
            println("$pair and $ranges $added")
            // eti kaikki jotka overlappaa ja poista overlapit
            ranges.filter { it.first <= pair.first && pair.first <= it.second  }.forEach {
                println("first between $pair -> $it Get to (${it.second}, ${pair.second})")
                if(pair.second >= it.second){
                    added = added - ( it.second - pair.first + 1)
                } else {
                    added = 0
                }
            }
            ranges.filter { it.first <= pair.second && pair.second <= it.second  }.forEach {
                println("second between $pair -> $it Get to (${pair.first}, ${it.first})")
                if(pair.first <= it.first){
                    added = added - (pair.second - it.first + 1 )
                } else {
                    added = 0
                }
            }
            println(added)
            fresh += added
        }
        return fresh
    }

    println(part1(ids))
    println(part2(ids))
}