import java.io.File
import java.util.*
import kotlin.collections.ArrayList

fun main(args: Array<String>) {
    println("AOC 2023, Day 8, Part 2 starting!!!!")

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
    val nodesEndWithA = findNodesEndsWithA(allNodes)
    println("Finding nodes that end with A: $nodesEndWithA")
    val nodesEndWithZ = findNodesEndsWithZ(allNodes)
    println("Finding nodes that end with Z: $nodesEndWithZ")

    val foundCount = Stack<Long>()
    nodesEndWithA.forEach {
        println("Starting search starting with $it")
        foundCount.push(traverseNodeIterative(directions, allNodes, it))
        println("found it! Path took ${foundCount.peek()} steps.")
    }

    println("Final path count results: $foundCount")

    var stepsStack = foundCount.pop()
    while(!foundCount.empty()) {
        stepsStack = lcm(stepsStack, foundCount.pop())
    }

    println("Steps taken to traverse to all nodes end with Z: $stepsStack")

    println("AOC 2023, Day 8, Part 2 completed!!!")
}

fun lcm(a: Long, b: Long): Long {
    return (a / gcd(a, b)) * b
}

fun gcd(a: Long, b: Long): Long {
    if (a == 0L) return b
    return gcd(b % a, a)
}


fun findNodesEndsWithA(nodes: ArrayList<Node>):ArrayList<Node> {
    val nodesEndsWithA = ArrayList<Node>()
    nodes.filter { it.nodeName.last() == 'A' }.toCollection(nodesEndsWithA)
    return nodesEndsWithA
}

fun findNodesEndsWithZ(nodes: ArrayList<Node>):ArrayList<Node> {
    val nodesEndsWithZ = ArrayList<Node>()
    nodes.filter { it.nodeName.last() == 'Z' }.toCollection(nodesEndsWithZ)
    return nodesEndsWithZ
}

fun traverseNodeIterative(directions: String, nodes: ArrayList<Node>, startNode: Node): Long {
    var totalSteps = 0L
    var nextStep = 0
    var currentNode = Node(startNode.nodeName, startNode.connectionLeft, startNode.connectionRight)
    var nextNode:Node
    while(currentNode.nodeName.last() != 'Z') {
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