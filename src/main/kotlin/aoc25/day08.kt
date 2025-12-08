package me.haataja.aoc25

import java.io.File
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {

    val path = "src/main/input/aoc25/day08.txt"
    val rows = File(path).readText().split(Regex("(\\r?\\n)+"))

    data class JunctionBox(
        val x : Int,
        val y : Int,
        val z : Int
    )

    val boxes = rows.map { row ->
        val numbers = row.split(",")
        JunctionBox(numbers[0].toInt(), numbers[1].toInt(), numbers[2].toInt())
    } as MutableList<JunctionBox>

    fun part1(boxes: MutableList<JunctionBox>): Int {
        val minDistances = mutableListOf<Pair<Float, Pair<JunctionBox, JunctionBox>>>()
        // find min connection between all boxes
        while (boxes.size > 1 ) {
            val currentBox = boxes.removeFirst()
            // find the closest
            val indexOfMin = boxes
                .map {
                    sqrt(
                        (it.x.toFloat() - currentBox.x.toFloat()).pow(2) +
                                (it.y.toFloat() - currentBox.y.toFloat()).pow(2) +
                                (it.z.toFloat() - currentBox.z.toFloat()).pow(2)
                    )
                }.forEachIndexed{ index, distance ->
                    val max = minDistances.maxOfOrNull { it.first }
                    if(max == null){
                        // todo
                    }else if( max != null && max > distance){
                        val maxIndex = minDistances.withIndex().maxBy { it.value.first }.index
                        minDistances.removeAt(maxIndex)
                        minDistances.add(Pair(distance, Pair(currentBox, boxes[index])))
                    }

                }
        }

            val circuits = mutableListOf<MutableList<JunctionBox>>()
        while (boxes.size > 1 ){
            val currentBox = boxes.removeFirst()
            // find the closest
            val indexOfMin = boxes.withIndex()
                .minBy {
                    sqrt(
                        (it.value.x.toFloat() - currentBox.x.toFloat()).pow(2) +
                                (it.value.y.toFloat() - currentBox.y.toFloat()).pow(2) +
                                (it.value.z.toFloat() - currentBox.z.toFloat()).pow(2)
                    )
                }.index

            // check if current box is in circuit
            if(circuits.any { it.contains(currentBox)}){
                val circuitIndex = circuits.withIndex().find { it.value.contains(currentBox) }!!.index
                val circuit = circuits.removeAt(circuitIndex)
                if(!circuit.contains(boxes[indexOfMin])){
                    circuit.add(boxes[indexOfMin])
                }
                circuits.add(circuit)
            } else {
                circuits.add(mutableListOf(currentBox, boxes[indexOfMin]))
            }
        }
        println(circuits.map { it.size })
        return 0
    }

    fun part2(rows: List<String>): Int {
        return 0
    }

    println(part1(boxes))
    println(part2(rows))
}