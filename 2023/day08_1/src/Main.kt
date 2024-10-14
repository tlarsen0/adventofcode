import java.io.File

fun main(args: Array<String>) {
    println("AOC 2023, Day 8, Part 1 starting!!!!")

    var firstLine = false
    var directions = "None"
    val allNodes = ArrayList<Node>()
    File(args[0]).forEachLine {
        if(!firstLine) {
            directions = it
            firstLine = true
        } else if(it.isNotEmpty()) {
            val nodeName = it.substring(0..2)
            val connectionLeft = it.substring(7..9)
            val connectionRight = it.substring(12..14)
            allNodes.add(Node(nodeName, connectionLeft, connectionRight))
        }
    }

    println("Total Nodes: ${allNodes.count()}")

    val stepsIter = traverseNodesIterative(directions, allNodes)

    println("Steps taken to traverse to ZZZ: $stepsIter")

    println("AOC 2023, Day 8, Part 1 completed!!!")
}

fun traverseNodesIterative(directions: String, nodes:ArrayList<Node>):Long {
    var nextStep = 0
    var totalSteps = 0L
    var currentNode = nodes.find { it.nodeName.compareTo("AAA") == 0 }!!
    var nextNode:Node
    while(currentNode.nodeName.compareTo("ZZZ") != 0) {
        val leftOrRight = directions[nextStep]
        nextNode = if(leftOrRight.compareTo('L') == 0) {
            nodes.find { it.nodeName == currentNode.connectionLeft }!!
        } else {
            nodes.find { it.nodeName == currentNode.connectionRight }!!
        }
        currentNode = nextNode

        nextStep++
        totalSteps++
        if(nextStep >= directions.length) {
            nextStep = 0
        }

    }

    return totalSteps
}

data class Node (val nodeName:String, val connectionLeft:String, val connectionRight:String)