package me.haataja.aoc25

import java.io.File

fun main() {

    val path = "src/main/input/aoc25/day02.txt"
    val ids = File(path).readText().split(Regex(","))

    fun part1(ids: List<String>): Long {
        return ids.sumOf {
            val ends = it.split("-")
            (ends[0].toLong()..ends[1].toLong()).toList().filter { r -> r.toString().contains(Regex("^(.+)\\1\$")) }.sum()
        }

    }

    fun part2(ids: List<String>): Long {
        return ids.sumOf {
            val ends = it.split("-")
            (ends[0].toLong()..ends[1].toLong()).toList().filter { r -> r.toString().contains(Regex("^(.+)\\1+\$")) }.sum()
        }
    }

    println(part1(ids))
    println(part2(ids))
}