package me.haataja.aoc25

import java.io.File

fun main() {

    val path = "src/main/input/aoc25/day04.txt"
    val rows = File(path).readText().split(Regex("(\\r?\\n)+"))

    fun getSurrounding(roll: Pair<Int, Int>): List<Pair<Int, Int>> {
        return mutableListOf(
            Pair(roll.first - 1, roll.second),
            Pair(roll.first - 1, roll.second - 1),
            Pair(roll.first, roll.second - 1),
            Pair(roll.first + 1, roll.second - 1),
            Pair(roll.first + 1, roll.second),
            Pair(roll.first + 1, roll.second + 1),
            Pair(roll.first, roll.second + 1),
            Pair(roll.first - 1, roll.second + 1),
        ).filter { it.first >= 0 && it.second >= 0 }
    }

    fun part1(rows: List<String>): Int {
        val rolls = mutableListOf<Pair<Int, Int>>()
        rows.forEachIndexed { rowIndex, row ->
            for (index in 0..row.lastIndex){
                if(row[index] == '@'){
                    rolls.add(Pair(index, rowIndex))
                }
            }
        }
        return rolls.sumOf { roll ->
            if(getSurrounding(roll).filter { s -> rolls.contains(s) }.size < 4){
                1
            } else {
                0 as Int
            }
        }
    }

    fun part2(rows: List<String>): Long {
        val rolls = mutableListOf<Pair<Int, Int>>()
        rows.forEachIndexed { rowIndex, row ->
            for (index in 0..row.lastIndex){
                if(row[index] == '@'){
                    rolls.add(Pair(index, rowIndex))
                }
            }
        }
        val delete = mutableListOf<Pair<Int, Int>>()
        var firstRound = true
        var sum = 0L
        while(delete.size > 0 || firstRound){
            rolls.removeAll(delete)
            delete.clear()
            sum += rolls.sumOf { roll ->
                if(getSurrounding(roll).filter { s -> rolls.contains(s) }.size < 4){
                    delete.add(roll)
                    1L
                } else {
                    0L
                }
            }
            firstRound = false
        }
        return sum
    }

    println(part1(rows))
    println(part2(rows))
}