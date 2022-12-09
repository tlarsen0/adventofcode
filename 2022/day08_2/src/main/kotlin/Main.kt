import java.io.File
import java.util.Comparator

fun main(args: Array<String>) {
    println("AOC 2022, Day 8, Part 2 starting!!!")

    val forest = Forest()
    var lineCount = 0
    File(args[0]).forEachLine {
        processLine(it, lineCount, forest)
        lineCount++
    }

    println("Count visible trees: ${forest.countVisible()}")
    println("Best scenic score: ${forest.bestScenicScore()}")

    println("AOC 2022, Day 8, Part 2 completed!!")
}

fun processLine(line: String, lineCount: Int, forest: Forest) {
    var xPos = 0
    for (c in line) {
        forest.addNewTree(Tree(MyCoord(xPos, lineCount), c.digitToInt()))
        xPos++
    }
}

data class Tree(val xyCoord:MyCoord, val height:Int)
data class MyCoord(val xPos:Int, val yPos:Int)

class Forest {
    private val allTrees: HashMap<MyCoord, Tree> = HashMap()

    fun addNewTree(newTree: Tree) {
        allTrees[newTree.xyCoord] = newTree
    }

    private var maxX: Int = 0
    private var maxY: Int = 0

    fun countVisible(): Int {
        maxX = allTrees.keys.stream().max(Comparator.comparingInt(MyCoord::xPos))!!.get().xPos
        maxY = allTrees.keys.stream().max(Comparator.comparingInt(MyCoord::yPos))!!.get().yPos

        var visibleCount = 0

        for (y in 0..maxY) {
            for (x in 0..maxX) {
                val theTree = allTrees[MyCoord(x, y)]
                if (checkVisibility(theTree!!)) {
                    visibleCount++
                }
            }
        }

        return visibleCount
    }

    fun bestScenicScore():Int {
        val scenicScores:ArrayList<Int> = ArrayList()

        for (tree in allTrees.values) {
            scenicScores.add(calculateScenicScore(tree))
        }

        return scenicScores.max()
    }

    private fun calculateScenicScore(tree: Tree):Int {
        // count trees seen left
        var leftTreeCount = 0
        for(x in (tree.xyCoord.xPos - 1) downTo 0) {
            val checkNextTree = allTrees[MyCoord(x, tree.xyCoord.yPos)]!!
            leftTreeCount++
            if(checkNextTree.height >= tree.height) {
                break
            }
        }
        // count trees seen right
        var rightTreeCount = 0
        for (x in (tree.xyCoord.xPos + 1)..maxX) {
            val checkNextTree = allTrees[MyCoord(x, tree.xyCoord.yPos)]!!
            rightTreeCount++
            if(checkNextTree.height >= tree.height) {
                break
            }
        }
        // count trees seen top
        var topTreeCount = 0
        for (y in (tree.xyCoord.yPos - 1) downTo 0) {
            val checkNextTree = allTrees[MyCoord(tree.xyCoord.xPos, y)]!!
            topTreeCount++
            if(checkNextTree.height >= tree.height) {
                break
            }
        }
        // count tress seen down
        var bottomTreeCount = 0
        for (y in (tree.xyCoord.yPos + 1)..maxY) {
            val checkNextTree = allTrees[MyCoord(tree.xyCoord.xPos, y)]!!
            bottomTreeCount++
            if(checkNextTree.height >= tree.height) {
                break
            }
        }
//        val foo = leftTreeCount * rightTreeCount * topTreeCount * bottomTreeCount
//        println("tlarsen,L108: scenic score = $foo")
        return leftTreeCount * rightTreeCount * topTreeCount * bottomTreeCount
    }

    private fun checkVisibility(tree: Tree): Boolean {
        // literal edge case: all trees on the edge of the forest are visible
        if ((tree.xyCoord.xPos == 0) || (tree.xyCoord.yPos == 0) || (tree.xyCoord.xPos == maxX) || (tree.xyCoord.yPos == maxY)) {
            return true
        }

        // is tree visible from the left edge?
        var isVisibleLeft = true
        for (x in (tree.xyCoord.xPos - 1) downTo 0) {
            val checkNextTree = allTrees[MyCoord(x, tree.xyCoord.yPos)]!!
            if (checkNextTree.height >= tree.height) {
                isVisibleLeft = false
                break
            }
        }

        // is tree visible from the right edge?
        var isVisibleRight = true
        for (x in (tree.xyCoord.xPos + 1)..maxX) {
            val checkNextTree = allTrees[MyCoord(x, tree.xyCoord.yPos)]!!
            if (checkNextTree.height >= tree.height) {
                isVisibleRight = false
                break
            }
        }

        // is tree visible from the top edge?
        var isVisibleTop = true
        for (y in (tree.xyCoord.yPos - 1) downTo 0) {
            val checkNextTree = allTrees[MyCoord(tree.xyCoord.xPos, y)]!!
            if (checkNextTree.height >= tree.height) {
                isVisibleTop = false
                break
            }
        }
        // is tree visible from the bottom edge?
        var isVisibleBottom = true
        for (y in (tree.xyCoord.yPos + 1)..maxY) {
            val checkNextTree = allTrees[MyCoord(tree.xyCoord.xPos, y)]!!
            if (checkNextTree.height >= tree.height) {
                isVisibleBottom = false
                break
            }
        }

        return isVisibleLeft || isVisibleRight || isVisibleTop || isVisibleBottom
    }
}