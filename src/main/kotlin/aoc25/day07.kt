package me.haataja.aoc25

import java.io.File

fun main() {
    val path = "src/main/input/aoc25/day07.txt"
    val rows = File(path).readText().split(Regex("(\\r?\\n)+"))
    val start = Pair(0, rows[0].indexOf("S"))
    val splitters = rows.flatMapIndexed { index, row ->
        row.withIndex().filter { it.value == '^' }.map { Pair(index, it.index) }
    }
    
    fun part1(start: Pair<Int, Int>, splitters: List<Pair<Int, Int>>): Int {
        // check if splitter is used
        return splitters.sumOf { splitter ->
            // splitter cannot be used if there is splitter on top of that and no splitters on eather side
            if (splitter.first > 2){
                // if splitter has another splitter over it 
                // get the rows where there must be a splitter to split the beam to have beam over the current splitter
                // if there is no splitter above current splitter, splitter can be on any row above the current row
                val splittersOnTop = splitters.filter { s -> s != splitter && s.second == splitter.second && s.first < splitter.first }
                val minY = if (splittersOnTop.isNotEmpty()){
                    splittersOnTop.last().first
                } else {
                    0
                }
                // now check if there is splitter above and eather left or right from the current splitter
                if(splitters.any { s -> s.first > minY && s.first < splitter.first && (s.second == splitter.second - 1 || s.second == splitter.second + 1) }){
                    1
                } else {
                    0
                }
            } else if (splitter.first == 2 && splitter.second == start.second){ 
                // if it is the first splitter, and it is directly down from start it can be used
                1 as Int
            } else {
                0
            }
        }
        
    }

    fun part2(start: Pair<Int, Int>, splitters: List<Pair<Int, Int>>): Long {
        val maxY = splitters.maxOf { it.first + 1 }
        var y = 0
        val paths = mutableListOf(Pair(start, 1L))
        // move beam, check splitters, split the stream
        // If multiple beams come to the same point up the multiplier at the that point
        while (y < maxY ){
            y+=2 // move two at the time as splitters are at the odd rows (even index)
            // println("$y")
            val nextSplitters = splitters.filter { it.first == y }
            if(nextSplitters.isNotEmpty()){
                val nextPaths = mutableListOf<Pair<Pair<Int, Int>,Long>>()
                while (paths.isNotEmpty()){
                    val currentBeam = paths.removeFirst()
                    val splitter = nextSplitters.find { it.second == currentBeam.first.second }
                    if(splitter != null){
                        // split to left
                        val left = Pair(y, currentBeam.first.second - 1)
                        if(nextPaths.map { it.first }.contains(left)){
                            val index = nextPaths.map { it.first }.withIndex().filter { it.value == left }.map { it.index }.first()
                            val point = nextPaths.removeAt(index)
                            nextPaths.add(Pair(point.first, currentBeam.second + point.second))
                        } else {
                            nextPaths.add(Pair(left, currentBeam.second))
                        }
                        // split to right
                        val right = Pair(y, currentBeam.first.second + 1)
                        if(nextPaths.map { it.first }.contains(right)){
                            val index = nextPaths.map { it.first }.withIndex().filter { it.value == right }.map { it.index }.first()
                            val point = nextPaths.removeAt(index)
                            nextPaths.add(Pair(point.first, currentBeam.second + point.second)) 
                        } else { 
                            nextPaths.add(Pair(right, currentBeam.second)) 
                        }
                        
                    } else {
                        // no splitting just move y
                        nextPaths.add(Pair(Pair(y, currentBeam.first.second), currentBeam.second))
                    }
                }
                paths.addAll(nextPaths)
                nextPaths.clear()
            }
        }
        
        return paths.sumOf { it.second }
    }

    println(part1(start, splitters))
    println(part2(start, splitters))
}