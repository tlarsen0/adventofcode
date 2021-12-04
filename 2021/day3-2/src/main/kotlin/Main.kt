import java.io.File
import kotlin.math.pow

fun main(args: Array<String>) {
    println("AOC 2021, Day 3.1 starting!!!!")

    val oxygenGeneratorRating : Rate = OxygenGenerator()
    val cO2ScrubberRating : Rate = CO2Scrubber()
    File(args[0]).forEachLine {
        oxygenGeneratorRating.readLine(it)
        cO2ScrubberRating.readLine(it)
    }

    val lifeSupportRating = oxygenGeneratorRating.getRate() * cO2ScrubberRating.getRate()

    println("Life Support Rating = $lifeSupportRating")

    println("AOC 2021, Day 3.1 complete!!!!")
}

// Features common to both rates
interface Rate {
    fun readLine(line : String)
    fun getRate() : Int
}

class MyPair {
    var zeros : Int = 0
    var ones : Int = 0
}

// Shamelessly stolen https://www.programiz.com/kotlin-programming/examples/binary-decimal-convert
fun convertBinaryToDecimal(theNum: Long): Int {
    var num = theNum
    var decimalNumber = 0
    var i = 0
    var remainder: Long

    while (num.toInt() != 0) {
        remainder = num % 10
        num /= 10
        decimalNumber += (remainder * 2.0.pow(i.toDouble())).toInt()
        ++i
    }
    return decimalNumber
}

class OxygenGenerator : Rate {
    private val theSource : ArrayList<String> = ArrayList()
    override fun readLine(line: String) {
        theSource.add(line)
    }

    private fun filterListOn(latestSource : ArrayList<String>, position : Int) : String {
        if (latestSource.size == 1) {
            return latestSource[0]
        }

        val data : HashMap<Int, MyPair> = HashMap()

        // just like original readLine
        for (line in latestSource) {
            for (i in line.indices) {
                if(!data.containsKey(i)) {
                    data[i] = MyPair()
                }

                if(line[i] == '0') {
                    data[i]!!.zeros++
                }
                if(line[i] == '1') {
                    data[i]!!.ones++
                }
            }
        }

        // More zeros than ones: Keep the values with 0 in $position.
        if(data[position]!!.zeros > data[position]!!.ones) {
            val newSource : ArrayList<String> = ArrayList()

            for(str in latestSource) {
                if (str[position] == '0')
                    newSource.add(str)
            }

            //println("tlarsen,L104: filtered on 0, newSource = $newSource")
            return filterListOn(newSource, position + 1)
        }
        // More ones than zeroes: Keep the values with 1 in $position.
        // Same zeroes as ones: Keep the values with 1 in $position.
        if((data[position]!!.ones > data[position]!!.zeros) || (data[position]!!.ones == data[position]!!.zeros)){
            val newSource : ArrayList<String> = ArrayList()

            for(str in latestSource) {
                if (str[position] == '1')
                    newSource.add(str)
            }

            //println("tlarsen,L115: filtered on 1, newSource = $newSource")
            return filterListOn(newSource, position + 1)
        }
        return "0" // shouldn't get here??!
    }

    override fun getRate(): Int {
        val finalBinary = filterListOn(theSource, 0)
        val rating = convertBinaryToDecimal(finalBinary.toLong())
        println("Oxygen Generator Rating: $rating - $finalBinary")
        return rating
    }
}

class CO2Scrubber : Rate {
    private val theSource : ArrayList<String> = ArrayList()
    override fun readLine(line: String) {
        theSource.add(line)
    }

    private fun filterListOn(latestSource : ArrayList<String>, position : Int) : String {
        if (latestSource.size == 1) {
            return latestSource[0]
        }

        val data : HashMap<Int, MyPair> = HashMap()

        // just like original readLine
        for (line in latestSource) {
            for (i in line.indices) {
                if(!data.containsKey(i)) {
                    data[i] = MyPair()
                }

                if(line[i] == '0') {
                    data[i]!!.zeros++
                }
                if(line[i] == '1') {
                    data[i]!!.ones++
                }
            }
        }

        // Fewer zeros than ones: Keep the values with 0 in $position.
        // Same zeroes as ones: Keep the values with 0 in $position.
        if((data[position]!!.zeros < data[position]!!.ones) || (data[position]!!.ones == data[position]!!.zeros)) {
            val newSource : ArrayList<String> = ArrayList()

            for(str in latestSource) {
                if (str[position] == '0')
                    newSource.add(str)
            }

            //println("tlarsen,L104: filtered on 0, newSource = $newSource")
            return filterListOn(newSource, position + 1)
        }
        // Fewer ones than zeroes: Keep the values with 1 in $position.
        if (data[position]!!.ones < data[position]!!.zeros) {
            val newSource : ArrayList<String> = ArrayList()

            for(str in latestSource) {
                if (str[position] == '1')
                    newSource.add(str)
            }

            //println("tlarsen,L115: filtered on 1, newSource = $newSource")
            return filterListOn(newSource, position + 1)
        }
        return "0" // shouldn't get here??!
    }

    override fun getRate(): Int {
        val finalBinary = filterListOn(theSource, 0)
        val rating = convertBinaryToDecimal(finalBinary.toLong())
        println("CO2 Scrubber Rating: $rating - $finalBinary")
        return rating
    }
}