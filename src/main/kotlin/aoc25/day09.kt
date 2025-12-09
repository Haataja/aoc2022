package me.haataja.aoc25

import java.io.File
import kotlin.math.abs

fun main() {

    val path = "src/main/input/aoc25/day09.txt"
    val rows = File(path).readText().split(Regex("(\\r?\\n)+"))


    fun part1(rows: List<String>): Long {
        val tiles = rows.map {
            val split = it.split(",")
            Pair(split[0].toLong(), split[1].toLong())
        } as MutableList<Pair<Long, Long>>

        var maximum = 0L
        while (tiles.isNotEmpty()){
            val currentTile = tiles.removeFirst()

            val max =
                tiles.maxOfOrNull { (abs(currentTile.first - it.first) + 1) * (abs(currentTile.second - it.second) + 1) }
            if(max != null && max > maximum){
                maximum = max
            }
        }
        return maximum
    }

    fun part2(rows: List<String>): Long {
        val redTiles = rows.map {
            val split = it.split(",")
            Pair(split[0].toLong(), split[1].toLong())
        } as MutableList<Pair<Long, Long>>

        val boundary = mutableMapOf<Long, MutableList<Long>>()
        redTiles.forEachIndexed { index, red ->
            if(index == 0){
                // connect first and last
                if(red.first - redTiles.last().first == 0L){
                    // connect vertically second -> second
                    val connection = red.second - redTiles.last().second
                    for (step in 0..<abs(connection)){
                        if (connection < 0) {
                            //boundary.add(Pair(red.first, redTiles.last().second - step))
                            if(boundary.keys.contains(redTiles.last().second - step)){
                                val b = boundary[redTiles.last().second - step]
                                b!!.add(red.first)
                            } else {
                                boundary[redTiles.last().second - step] = mutableListOf(red.first)
                            }
                        } else {
                            //boundary.add(Pair(red.first, redTiles.last().second + step))
                            if(boundary.keys.contains(redTiles.last().second + step)){
                                val b = boundary[redTiles.last().second + step]
                                b!!.add(red.first)
                            } else {
                                boundary[redTiles.last().second + step] = mutableListOf(red.first)
                            }
                        }
                    }
                } else {
                    // connect horizontally first -> first
                    val connection = red.first - redTiles.last().first
                    for (step in 0..<abs(connection)){
                        if (connection < 0) {
                            //boundary.add(Pair(redTiles.last().first - step, red.second))
                            if(boundary.keys.contains(red.second)){
                                val b = boundary[red.second]
                                b!!.add(redTiles.last().first - step)
                            } else {
                                boundary[red.second] = mutableListOf(redTiles.last().first - step)
                            }
                        } else {
                            //boundary.add(Pair(redTiles.last().first + step, red.second))
                            if(boundary.keys.contains(red.second)){
                                val b = boundary[red.second]
                                b!!.add(redTiles.last().first + step)
                            } else {
                                boundary[red.second] = mutableListOf(redTiles.last().first + step)
                            }
                        }
                    }
                }

            } else {
                // connect to one before
                if(red.first - redTiles[index - 1].first == 0L){
                    // connect vertically second -> second
                    val connection = red.second - redTiles[index - 1].second
                    for (step in 0..<abs(connection)){
                        if (connection < 0) {
                            //boundary.add(Pair(red.first, redTiles[index - 1].second - step))
                            if(boundary.keys.contains(redTiles[index - 1].second - step)){
                                val b = boundary[redTiles[index - 1].second - step]
                                b!!.add(red.first)
                            } else {
                                boundary[redTiles[index - 1].second - step] = mutableListOf(red.first)
                            }
                        } else {
                            //boundary.add(Pair(red.first, redTiles[index - 1].second + step))
                            if(boundary.keys.contains(redTiles[index - 1].second + step)){
                                val b = boundary[redTiles[index - 1].second + step]
                                b!!.add(red.first)
                            } else {
                                boundary[redTiles[index - 1].second + step] = mutableListOf(red.first)
                            }
                        }
                    }
                } else {
                    // connect vertically first -> first
                    val connection = red.first - redTiles[index - 1].first
                    for (step in 0..<abs(connection)){
                        if (connection < 0) {
                            //boundary.add(Pair(redTiles[index - 1].first - step, red.second))
                            if(boundary.keys.contains(red.second)){
                                val b = boundary[red.second]
                                b!!.add(redTiles[index - 1].first - step)
                            } else {
                                boundary[red.second] = mutableListOf(redTiles[index - 1].first - step)
                            }
                        } else {
                            //boundary.add(Pair(redTiles[index - 1].first + step, red.second))
                            if(boundary.keys.contains(red.second)){
                                val b = boundary[red.second]
                                b!!.add(redTiles[index - 1].first + step)
                            } else {
                                boundary[red.second] = mutableListOf(redTiles[index - 1].first + step)
                            }
                        }
                    }
                }
            }
        }
        println("$boundary")

        var maximum = 0L
        while (redTiles.isNotEmpty()){
            val currentTile = redTiles.removeFirst()

            val max = redTiles.maxOfOrNull {
                val corner1 = Pair(currentTile.first, it.second)
                val border1 = boundary[corner1.second]
                val corner2 = Pair(it.first, currentTile.second)
                val border2 = boundary[corner2.second]
                if(border1 != null && corner1.first < border1.max() && corner1.second > border1.min()){
                    if(border2 != null && corner2.first < border2.max() && corner2.second > border2.min()){
                        val area = (abs(currentTile.first - it.first) + 1) * (abs(currentTile.second - it.second) + 1)

                        area
                    } else {
                        0L
                    }
                } else {
                    0
                }
            }
            if(max != null && max > maximum){
                println("HOP $currentTile ")
                maximum = max
            }
        }


        return maximum
    }

    println(part1(rows))
    println(part2(rows))
}

/*
    for(rowIndex in 0..boundary.keys.max()){
            println(rowIndex)

            if(boundary.keys.contains(rowIndex)){
                val tilesInRow = boundary[rowIndex]!!
                var rowString = ""
                for(colIndex in 0..tilesInRow.max()){
                    rowString = if(tilesInRow.contains(colIndex)){
                        "$rowString#"
                    } else {
                        "$rowString."
                    }
                }
                println(rowString)
            }
        }

        for(rowIndex in 0..boundary.keys.max()){
            println(rowIndex)

            if(boundary.keys.contains(rowIndex)){
                val tilesInRow = boundary[rowIndex]!!
                val min = tilesInRow.min()
                val max = tilesInRow.max()
                if(min != max){
                    for(between in 1..<(max-min)){
                        if(!tilesInRow.contains(min + between)){
                            tilesInRow.add(min + between)
                        }
                    }
                }
            }

        }
 */