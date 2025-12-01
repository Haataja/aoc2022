package me.haataja.aoc25

import java.io.File

fun main() {

    val path = "src/main/input/aoc25/day01.txt"
    val rows = File(path).readText().split(Regex("(\\r?\\n)+"))
    val start = 50

    fun part1(rows: List<String>): Int {
        var current = start
        return rows.sumOf {
            val direction = it[0]
            val distance = it.substring(1..<it.length).toInt() % 100
            if (direction == 'L'){
                current -= distance
            } else {
                current += distance
            }

            if(current >= 100) current -= 100
            if (current < 0) current += 100
            //println("$direction $distance $current")
            if(current == 0){
                1 as Int
            } else {
                0 as Int
            }
        }
    }

    fun part2(rows: List<String>): Int {
        var current = start
        var lastWasZero = false
        return rows.sumOf {
            var zeros = 0
            val direction = it[0]
            val distance = it.substring(1..<it.length).toInt() % 100
            zeros += (it.substring(1..<it.length).toInt() - distance) / 100
            if (direction == 'L'){
                current -= distance
            } else {
                current += distance
            }

            if(current == 100) {
                current = 0
            } else if(current >= 100) {
                current -= 100
                if (!lastWasZero) zeros += 1
            } else if (current < 0) {
                current += 100
                if (!lastWasZero) zeros += 1
            }
            if (current == 0){
                zeros += 1
                lastWasZero = true
            } else {
                lastWasZero = false
            }
            //println("$direction $distance $current $zeros")
            zeros
        }
    }

    println(part1(rows))
    println(part2(rows))
}