package me.haataja.aoc25

import java.io.File

fun main() {

    val path = "src/main/input/aoc25/day03.txt"
    val rows = File(path).readText().split(Regex("(\\r?\\n)+"))

    fun part1(rows: List<String>): Int {
        return rows.sumOf { bank ->
            val numbers = bank.toList().map { it.digitToInt() }
            val maxValue = numbers.max()
            if (numbers.indexOf(maxValue) + 1 > numbers.lastIndex){
                "${numbers.subList(0, numbers.lastIndex).max()}$maxValue".toInt()
            } else {
                "${maxValue}${numbers.subList(numbers.indexOf(maxValue) + 1, numbers.size).max()}".toInt()
            }
        }
    }

    fun recursive(numbers: List<Int>, bank: Long?, depthFromLast: Int, depthFromFront: Int): Long {
        if (bank?.toString()?.length == 12){
            return bank
        }
        //println("$numbers, $bank")
        val maxValue = numbers.max()
        if (numbers.indexOf(maxValue) + 1 > numbers.lastIndex){
            val sublist = numbers.subList(0, numbers.lastIndex)
            val newBank = if(bank != null){
                    val list = bank.toString().toMutableList()
                    list.add(depthFromFront, maxValue.digitToChar())
                    list.joinToString("").toLong()

                } else {
                    maxValue.toLong()
                }

            return recursive(sublist, newBank, depthFromLast + 1,depthFromFront)
        } else {
            var sublist = numbers.subList(numbers.indexOf(maxValue) + 1, numbers.size)
            val newBank = if(sublist.size >= 11 - (bank?.toString()?.length ?: 0)){
                if(bank != null){
                    val list = bank.toString().toMutableList()
                    list.add(depthFromFront, maxValue.digitToChar())
                    list.joinToString("").toLong()

                } else {
                    maxValue.toLong()
                }
            } else {
                //println("do something else $sublist < ${11 - bank.toString().length}")
                var newMax = maxValue - 1
                var newSublist = numbers.subList(numbers.indexOf(newMax) + 1, numbers.size)
                //println("   $newSublist $newMax")
                while (!numbers.contains(newMax) || newSublist.size < 11 - bank.toString().length){
                    newMax -= 1
                    newSublist = numbers.subList(numbers.indexOf(newMax) + 1, numbers.size)
                }
                sublist = newSublist
                if(bank != null){
                    val list = bank.toString().toMutableList()
                    list.add(depthFromFront, newMax.digitToChar())
                    list.joinToString("").toLong()

                } else {
                    newMax.toLong()
                }
            }
            return recursive(sublist, newBank, depthFromLast, depthFromFront + 1)
        }
    }

    fun part2(rows: List<String>): Long {
        return rows.sumOf { bank ->
            val numbers = bank.toList().map { it.digitToInt() }
            recursive(numbers, null, 0, 0)
        }
    }

    println(part1(rows))
    println(part2(rows))
}