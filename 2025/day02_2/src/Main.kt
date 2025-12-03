import java.io.File

/**
 * Main entry point for the application.
 * Reads product ID ranges from a file and calculates the sum of invalid product IDs.
 * @param argv command line arguments, expects the file path as the first argument.
 */
fun main(argv:Array<String>) {
    println("AOC 2025, Day 2, Part 2 starting!!!!")

    var products = Products(listOf())
    File(argv[0]).forEachLine {
        products = Products(it.split(","))
    }

    println("Adding up all invalid product IDs: ${products.addAllInvalid()}")

    println("AOC 2025, Day 2, Part 2 completed!!!")
}

/**
 * Represents a collection of product ID ranges.
 * @param allProductIDs a list of strings, where each string is a range of product IDs (e.g., "10-20").
 */
class Products(val allProductIDs:List<String>) {
    /**
     * Calculates the sum of all invalid product IDs across all ranges.
     * @return the total sum of invalid product IDs.
     */
    fun addAllInvalid():Long {
        var sumInvalid:Long = 0
        for(productIDRange in allProductIDs) {
            sumInvalid += addInvalid(productIDRange)
        }

        return sumInvalid
    }

    /**
     * Calculates the sum of invalid product IDs within a given range.
     * @param productIDRange a string representing a range of product IDs (e.g., "10-20").
     * @return the sum of invalid product IDs in the range.
     */
    private fun addInvalid(productIDRange:String) : Long {
        var invalidSum:Long = 0
        val range = productIDRange.split("-")
        val startID = range[0].toLong()
        val endID = range[1].toLong()
        for (i in startID..endID) {
            if(isRepeated(i)) {
                invalidSum += i
            }
        }

        return invalidSum
    }

    /**
     * Checks if a product ID has a repeating pattern.
     * A product ID has a repeating pattern if its string representation is formed by repeating a smaller string.
     * For example, 1212 is a repeating pattern (of "12"), but 1213 is not.
     * @param productID the product ID to check.
     * @return true if the product ID has a repeating pattern, false otherwise.
     */
    private fun isRepeated(productID: Long) : Boolean {
        var width = 1
        val productIDString = productID.toString()
        var repeated = false
        while((width <= productIDString.length / 2)) {
            // if width is not evenly divisible in the string, skip this width
            val modWidth = productIDString.length % width
            if(modWidth != 0) {
                width++
                continue
            }

            val pattern = productIDString.substring(0, width)

            for(i in 0 until productIDString.length step width) {
                val test = productIDString.substring(i, i + width)
                if (test.compareTo(pattern) != 0) {
                    repeated = false
                    break
                } else {
                    repeated = true
                }
            }
            if(repeated) {
                println("$productID has a repeating pattern!!!!")
                return true
            }
            width++
        }

        return false
    }
}