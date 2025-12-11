package me.haataja.aoc25

import java.io.File

fun main() {

    val path = "src/main/input/aoc25/day11.txt"
    val rows = File(path).readText().split(Regex("(\\r?\\n)+"))


    fun part1(rows: List<String>): Int {
        val racks = rows.associate { row ->
            val rack = row.split(":")
            rack[0] to rack[1].trim().split(Regex("\\s"))
        }
        println(racks)

        val paths = mutableListOf("you")
        var pathCount = 0
        while (paths.isNotEmpty()){
            val pathEnding = paths.removeFirst()
            if(racks.keys.contains(pathEnding)){
                if(racks[pathEnding]!!.contains("out")){
                    pathCount += 1
                    paths.addAll(racks[pathEnding]!!.filter { it != "out" })
                } else {
                    paths.addAll(racks[pathEnding]!!)
                }
            } else {
                println("HEY $path is not part of the racks!")
            }
        }
        return pathCount
    }

    fun part2(rows: List<String>): Int {
        return 0
    }

    println(part1(rows))
    println(part2(rows))
}