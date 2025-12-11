package me.haataja.aoc25

import java.io.File

fun main() {

    val filePath = "src/main/input/aoc25/day11.txt"
    val rows = File(filePath).readText().split(Regex("(\\r?\\n)+"))


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
                println("HEY $pathEnding is not part of the racks!")
            }
        }
        return pathCount
    }

    // does not work with full dataset
    fun part2(rows: List<String>): Int {
        val racks = rows.associate { row ->
            val rack = row.split(":")
            rack[0] to rack[1].trim().split(Regex("\\s"))
        }
        println(racks)

        val paths = mutableListOf(Pair("svr",Pair(false, false)))
        var pathCount = 0
        while (paths.isNotEmpty()){
            println("$pathCount, $paths")
            val pathMarker = paths.removeFirst()
            val pathEnding = pathMarker.first
            if(racks.keys.contains(pathEnding)){
                if(racks[pathEnding]!!.contains("out")){
                    if(pathMarker.second.first && pathMarker.second.second){
                        pathCount += 1
                    }

                    for (step in racks[pathEnding]!!.filter { it != "out" }){
                        paths.add(Pair(step, Pair(pathMarker.second.first, pathMarker.second.second)))
                    }
                } else {
                    val pair = when (pathEnding) {
                        "fft" -> {
                            Pair(true,pathMarker.second.second)
                        }
                        "dac" -> {
                            Pair(pathMarker.second.first, true)
                        }
                        else -> {
                            Pair(pathMarker.second.first, pathMarker.second.second)
                        }
                    }

                    for (step in racks[pathEnding]!!){
                        paths.add(Pair(step, pair))
                    }
                }
            } else {
                println("HEY $pathEnding is not part of the racks!")
            }
        }

        return pathCount
    }

    println(part1(rows))
    println(part2(rows))
}