package me.haataja.aoc25

import java.io.File

fun main() {

    val path = "src/main/input/aoc25/day05.txt"
    val ids = File(path).readText().split(Regex(","))

    fun part1(ids: List<String>): Long {
        return 0

    }

    fun part2(ids: List<String>): Long {
        return 0
    }

    println(part1(ids))
    println(part2(ids))
}