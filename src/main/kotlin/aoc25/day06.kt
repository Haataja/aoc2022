package me.haataja.aoc25

import java.io.File

fun main() {

    val path = "src/main/input/aoc25/day06.txt"
    val rawRows = File(path).readText().split(Regex("(\\r?\\n)+")) as MutableList<String>
    val rows = rawRows.map { it.trim().split(Regex("\\s+")) } as MutableList<List<String>>
    val lastRow = rawRows.removeLast()

    fun part1(operands: List<String>, rows: MutableList<List<String>>): Long {
        var sum = 0L

        operands.forEachIndexed { index, operand ->
            var added = if(operand == "*"){
                1L
            } else {
                0L
            }
            rows.forEach { row ->
                if (operand == "*"){
                    added *= row[index].toLong()
                } else {
                    added += row[index].toLong()
                }
            }
            sum += added
        }
        return sum

    }

    fun part2(operandRow: String, operands:List<String>, rows: MutableList<String>): Long{
        // Figure out length of each column, done from the last row by calculating spaces between operands
        val lengths = operandRow.split(Regex("[*+]")).map { it.length } as MutableList<Int>
        lengths.removeFirst()
        // need to add one in the last as the split char is not calculated
        val last = lengths.removeLast() + 1
        lengths.add(last)

        // Map each column to list of numbers
        val columns = mutableListOf<List<String>>()
        var currentIndex = 0
        lengths.forEach { length ->
            val column = mutableListOf<String>()
            rows.forEach { r -> column.add(r.substring(currentIndex, currentIndex + length)) }
            columns.add(column)
            currentIndex += length + 1
        }
        var sum = 0L
        // Map each column to new number according to index
        columns.map { column ->
            val rCol = mutableListOf<String>()
            for (cNumberIndex in 0..column[0].lastIndex){
                var number = ""
                for (cNumber in column){
                    number = "$number${cNumber[cNumberIndex]}"
                }
                rCol.add(number.trim())
            }
            rCol
        }.forEachIndexed { index, strings -> // Sum or multiply each list of new numbers according to operand
            var added = if(operands[index] == "*"){
                1L
            } else {
                0L
            }
            strings.forEach { row ->
                if (operands[index] == "*"){
                    added *= row.toLong()
                } else {
                    added += row.toLong()
                }
            }
            sum += added
        }
        return sum
    }

    val operands = rows.removeLast()
    println(part1(operands, rows))
    println(part2(lastRow, operands, rawRows))
}