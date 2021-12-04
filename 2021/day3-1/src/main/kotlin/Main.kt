import java.io.File
import kotlin.math.pow

fun main(args: Array<String>) {
    println("AOC 2021, Day 3.1 starting!!!!")

    val gammaRate = GammaRate()
    val epsilonRate = EpsilonRate()
    File(args[0]).forEachLine {
        gammaRate.readLine(it)
        epsilonRate.readLine(it)
    }

    val powerConsumption = gammaRate.getRate() * epsilonRate.getRate()

    println("Power Consumption      = $powerConsumption")

    println("AOC 2021, Day 3.1 complete!!!!")
}

// Features common to both rates
interface Rate {
    fun readLine(line : String)
    fun getRate() : Int
}

data class MyPair constructor(val startZeroes : Int, val startOnes : Int){
    var zeros : Int = 0
    var ones : Int = 0
    init {
        zeros = startZeroes
        ones = startOnes
    }
}

// Shamelessly stolen https://www.programiz.com/kotlin-programming/examples/binary-decimal-convert
fun convertBinaryToDecimal(binaryNum: Long): Int {
    var num = binaryNum
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

class GammaRate : Rate {
    private val theData : HashMap<Int, MyPair> = HashMap()
    override fun readLine(line : String) {
        for (i in line.indices) {
            // Initialize if first time
            if(!theData.containsKey(i)) {
                theData[i] = MyPair(0,0)
            }

            if(line[i] == '0') {
                theData[i]!!.zeros++
            }
            if(line[i] == '1') {
                theData[i]!!.ones++
            }
        }
    }

    override fun getRate() : Int {
        var gammaRate : String = ""
        for (data in theData) {
            if(data.value.zeros > data.value.ones) {
                gammaRate += "0"
            }
            if(data.value.ones > data.value.zeros) {
                gammaRate += "1"
            }
        }

        println("Calculated gammaRate   = $gammaRate")
        val gammaRateAsInt = convertBinaryToDecimal(gammaRate.toLong())
        println("   Decimal gammaRate   = $gammaRateAsInt")

        return gammaRateAsInt
    }
}

class EpsilonRate : Rate {
    private val theData : HashMap<Int, MyPair> = HashMap()
    override fun readLine(line : String) {
        for (i in line.indices) {
            // Initialize if first time
            if(!theData.containsKey(i)) {
                theData[i] = MyPair(0,0)
            }

            if(line[i] == '0') {
                theData[i]!!.zeros++
            }
            if(line[i] == '1') {
                theData[i]!!.ones++
            }
        }
    }

    override fun getRate() : Int {
        var epsilonRate : String = ""
        for (data in theData) {
            if(data.value.zeros < data.value.ones) {
                epsilonRate += "0"
            }
            if(data.value.ones < data.value.zeros) {
                epsilonRate += "1"
            }
        }

        println("Calculated epsilonRate = $epsilonRate")
        val epsilonRateAsInt = convertBinaryToDecimal(epsilonRate.toLong())
        println("   Decimal epsilonRate = $epsilonRateAsInt")

        return epsilonRateAsInt
    }
}