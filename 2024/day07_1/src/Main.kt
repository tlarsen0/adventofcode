import java.io.File


/**
 * Main function for Day 7, Part 1 of Advent of Code 2024.
 * This program reads calibration data from a file, performs calculations to find a matching operator sequence,
 * and prints the sum of the final values for which a match was found.
 *
 * @param args Command line arguments, expects the input file path as the first argument.
 */
fun main(args: Array<String>) {
    println("AOC 2024, Day 7, Part 1 starting!!!!")

    val allData = ArrayList<CalibrationData>()
    File(args[0]).forEachLine {
        val colonSplit = it.split(":")
        val finalVal = colonSplit[0].toLong()
        val numberSplit = colonSplit[1].split(" ")
        val vals = ArrayList<Long>()
        for(num in numberSplit) {
            if(num.isNotBlank()) vals.add(num.toLong())
        }
        allData.add(CalibrationData(finalVal, vals, ArrayList()))
    }


    initializeOperators(allData)
    val answer = runCalculations(allData)

    println("Answer: $answer")

    println("AOC 2024, Day 7, Part 1 completed!!!")
}

/**
 * Runs the calculations for all calibration data entries.
 * It iterates through each data entry and tries to find a sequence of operators
 * that results in the expected final value.
 *
 * @param allData The list of all calibration data.
 * @return The sum of the final values for which a matching operator sequence was found.
 */
fun runCalculations(allData:List<CalibrationData>) : Long {
    var answer:Long = 0
    for (data in allData) {

        var theFlag = false
        val maxOps = 1 shl data.ops.size
        var opIndex = 0
        // Rotate through operators till match finalVal or exhausted
        while(!theFlag && opIndex < maxOps) {
            theFlag = checkData(data)
            if(theFlag) {
                answer += data.finalVal
            } else {
                rotateOperators(data)
            }
            opIndex++
        }
    }
    return answer
}

/**
 * Initializes the operators for all calibration data entries.
 *
 * @param allData The list of all calibration data.
 */
fun initializeOperators(allData:List<CalibrationData>) {
    for(data in allData) {
        initializeOperator(data)
    }
}

/**
 * Initializes the operators for a single calibration data entry.
 * It adds a default "+" operator between each pair of values.
 *
 * @param theData The calibration data to initialize.
 */
fun initializeOperator(theData: CalibrationData) {
    for(i in 1..(theData.vals.size - 1)) {
        theData.ops.add(Operators("+"))
    }
}

/**
 * Rotates the operators for a given calibration data entry.
 * This function treats the sequence of operators as a binary number,
 * where "+" is 0 and "*" is 1, and increments it.
 *
 * @param theData The calibration data whose operators need to be rotated.
 */
fun rotateOperators(theData: CalibrationData) {
    var index = 0
    var carry: Boolean
    do {
        val thePrevOp = theData.ops[index].strOp
        val theNextOp = theData.ops[index].nextOp()
        carry = (thePrevOp.compareTo("*") == 0) && (theNextOp.compareTo("+") == 0)
        index++
    } while (carry && index < theData.ops.size)
}

/**
 * Checks if the current sequence of operators for a given calibration data
 * results in the expected final value.
 *
 * @param data The calibration data to check.
 * @return True if the calculation matches the final value, false otherwise.
 */
fun checkData(data: CalibrationData): Boolean {
    // initialize runTotal: runTotal = vals[0] (ops[0]) vals[1]
    var runTotal : Long = if(data.ops[0].strOp.compareTo("+") == 0) {
        data.vals[0] + data.vals[1]
    } else {
        data.vals[0] * data.vals[1]
    }
    for(i in 1..(data.ops.size - 1)) {
        if(data.ops[i].strOp.compareTo("+") == 0) {
            runTotal += data.vals[i + 1]
        } else {
            runTotal *= data.vals[i + 1]
        }
    }
    return runTotal == data.finalVal
}

/**
 * Represents an operator that can be either "+" or "*".
 *
 * @property strOp The string representation of the operator.
 */
class Operators(var strOp:String) {
    /**
     * Changes the operator to the next one in the sequence (+ -> *, * -> +).
     *
     * @return The new operator string.
     */
    fun nextOp() : String {
        strOp = if(strOp.compareTo("+") == 0) {
            "*"
        } else {
            "+"
        }
        return strOp
    }

    override fun toString():String {
        return "Operators(strOp='$strOp')"
    }
}

/**
 * Data class to hold the calibration data for a single line from the input file.
 *
 * @property finalVal The expected result of the calculation.
 * @property vals The list of values to be used in the calculation.
 * @property ops The list of operators to be used between the values.
 */
data class CalibrationData(val finalVal:Long, val vals:List<Long>, val ops:ArrayList<Operators>)