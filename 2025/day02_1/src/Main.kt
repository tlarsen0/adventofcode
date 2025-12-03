import java.io.File

fun main(argv:Array<String>) {
    println("AOC 2025, Day 2, Part 1 starting!!!!")


    var products = Products(listOf())
    File(argv[0]).forEachLine {
        products = Products(it.split(","))
    }

    println("Adding up all invalid product IDs: ${products.addAllInvalid()}")

    println("AOC 2025, Day 2, Part 1 completed!!!")
}

class Products(val allProductIDs:List<String>) {
    fun addAllInvalid():Long {
        var sumInvalid:Long = 0
        for(productIDRange in allProductIDs) {
           sumInvalid += addInvalid(productIDRange)
        }

        return sumInvalid
    }

    private fun addInvalid(productIDRange:String) : Long {
        var invalidSum:Long = 0
        val range = productIDRange.split("-")
        val startID = range[0].toLong()
        val endID = range[1].toLong()
        for (i in startID..endID) {
            if(isRepeatedTwice(i)) {
                invalidSum += i
            }
        }

        return invalidSum
    }

    private fun isRepeatedTwice(productID:Long) : Boolean {
        val productIDString = productID.toString()
        val width = productIDString.length / 2
        val leftSide = productIDString.substring(0, width)
        val rightSide = productIDString.substring(width)

        return leftSide.compareTo(rightSide) == 0
    }
}