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

    val steps = traverseNodes(directions, allNodes)

    println("Steps taken to traverse to ZZZ: $steps")

    println("AOC 2023, Day 8, Part 1 completed!!!")
}

fun traverseNodes(directions: String, nodes:ArrayList<Node>):Int {
    return helper(0, nodes.first(), directions, nodes)
}

fun helper(step: Int, theNode:Node, directions: String, nodes: ArrayList<Node>):Int {
    if(theNode.nodeName.compareTo("ZZZ") == 0) {
        return 0
    }

    val leftOrRight = directions[step]
    //println("tlarsne,L40: leftOrRight = $leftOrRight")
    val nextNode:Node
    if(leftOrRight == 'L') {
        //println("tlarsen,L41: taking left node")
        nextNode = nodes.find { it.nodeName == theNode.connectionLeft }!!
    } else {
        //println("tlarsen,L45: taking right node, moving to ${theNode.connectionRight} ")
        nextNode = nodes.find { it.nodeName == theNode.connectionRight }!!
    }

    var nextStep = step + 1
    if(nextStep >= directions.length) {
        nextStep = 0
    }

    return 1 + helper(nextStep, nextNode, directions, nodes)
}

data class Node (val nodeName:String, val connectionLeft:String, val connectionRight:String)