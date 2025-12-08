package me.haataja.aoc25

import java.io.File
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
        while (boxes.isNotEmpty()) {
            val currentBox = boxes.removeFirst()
            // find the closest
            boxes.map {
                    sqrt(
                        (it.x.toFloat() - currentBox.x.toFloat()).pow(2) +
                             (it.y.toFloat() - currentBox.y.toFloat()).pow(2) +
                             (it.z.toFloat() - currentBox.z.toFloat()).pow(2)
                    )
                }.forEachIndexed{ index, distance ->
                    val max = minDistances.maxOfOrNull { it.first }
                    if(max == null || minDistances.size < 10){
                        minDistances.add(Pair(distance, Pair(currentBox, boxes[index])))
                    } else if( max > distance){
                        val maxIndex = minDistances.withIndex().maxBy { it.value.first }.index
                        minDistances.removeAt(maxIndex)
                        minDistances.add(Pair(distance, Pair(currentBox, boxes[index])))
                    } 

                }
        }
        minDistances.sortBy { it.first }
        println(minDistances)
        
        val circuits = mutableListOf<MutableList<JunctionBox>>()
        while (minDistances.isNotEmpty() ){
            val boxPair = minDistances.removeFirst().second
            // check if current box is in circuit
            if(circuits.any { it.contains(boxPair.first)}){
                if(circuits.any { it.contains(boxPair.second) }){
                    val firstCircuitIndex = circuits.withIndex().find { it.value.contains(boxPair.first) }!!.index
                    val secondCircuitIndex = circuits.withIndex().find { it.value.contains(boxPair.second) }!!.index
                    if(firstCircuitIndex != secondCircuitIndex){
                        val firstCircuit = circuits.removeAt(firstCircuitIndex)
                        val secondCircuit = if(firstCircuitIndex < secondCircuitIndex){
                            circuits.removeAt(secondCircuitIndex - 1)
                        } else {
                            circuits.removeAt(secondCircuitIndex)
                        }
                        firstCircuit.addAll(secondCircuit)
                        circuits.add(firstCircuit)
                    }
                } else {
                    val firstCircuitIndex = circuits.withIndex().find { it.value.contains(boxPair.first) }!!.index
                    val firstCircuit = circuits.removeAt(firstCircuitIndex)
                    firstCircuit.add(boxPair.second)
                    circuits.add(firstCircuit)
                }
            } else if(circuits.any { it.contains(boxPair.second)}){
                if(circuits.any { it.contains(boxPair.first) }){
                    val firstCircuitIndex = circuits.withIndex().find { it.value.contains(boxPair.first) }!!.index
                    val secondCircuitIndex = circuits.withIndex().find { it.value.contains(boxPair.second) }!!.index
                    if(firstCircuitIndex != secondCircuitIndex){
                        val firstCircuit = circuits.removeAt(firstCircuitIndex)
                        val secondCircuit = circuits.removeAt(secondCircuitIndex)
                        firstCircuit.addAll(secondCircuit)
                        circuits.add(firstCircuit)
                    }
                } else {
                    val firstCircuitIndex = circuits.withIndex().find { it.value.contains(boxPair.second) }!!.index
                    val firstCircuit = circuits.removeAt(firstCircuitIndex)
                    firstCircuit.add(boxPair.first)
                    circuits.add(firstCircuit)
                }
            } else {
                circuits.add(mutableListOf(boxPair.first, boxPair.second))
            }
        }
        circuits.sortBy { it.size }
        var multiple = 1
        circuits.map { it.size }.sortedDescending().subList(0, 3).forEach {
            multiple *= it
        }
        return multiple
    }

    fun part2(boxes: List<JunctionBox>): Int {
        return 0
    }
    val box1 = mutableListOf<JunctionBox>()
    box1.addAll(boxes)
    val box2 = mutableListOf<JunctionBox>()
    box2.addAll(boxes)
    println(part1(box1))
    println(part2(box2))
}