import java.io.File

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

    println("tlarsen,L27: lcm(15, 20) = ${lcm(15, 20)}")

    val stepsRecursive = traverseNodesIterative(directions, allNodes, nodesEndWithA)

    println("Steps taken to traverse to all nodes end with Z: $stepsRecursive")

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

fun countNodesEndsWithZ(nodes: ArrayList<Node>):Int {
    return nodes.count { it.nodeName.last() == 'Z' }
}

fun traverseNodesIterative(directions: String, nodes: ArrayList<Node>, startNodes: ArrayList<Node>): Long {
    var totalSteps = 0L
    var nextStep = 0
    var currentNodes = ArrayList<Node>(startNodes)
    val nextNodes = ArrayList<Node>()
    val countA = currentNodes.count()
    while(countNodesEndsWithZ(currentNodes) != countA) {
        nextNodes.clear()
        val leftOrRight = directions[nextStep]
        if(leftOrRight == 'L') {
            currentNodes.forEach { theNode ->
                val leftNodeName = theNode.connectionLeft
                nextNodes.add(nodes.find { it.nodeName == leftNodeName }!!)
            }
        } else {
            currentNodes.forEach { theNode ->
                val rightNodeName = theNode.connectionRight
                nextNodes.add(nodes.find { it.nodeName == rightNodeName }!!)
            }
        }
        currentNodes = ArrayList(nextNodes)

        nextStep++
        totalSteps++
        if(nextStep >= directions.length) {
            nextStep = 0
        }
        //if(totalSteps.mod(1) == 0) {
        if((countNodesEndsWithZ(currentNodes) > 2) || (totalSteps.mod(1000000) == 0)) {
            println("tlarsen,L78: $totalSteps : ${countNodesEndsWithZ(currentNodes)} / ${countA} current node = $currentNodes")
        }
    }

    return totalSteps
}

data class Node (val nodeName:String, val connectionLeft:String, val connectionRight:String)