import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 8.2 starting!!!!")

    var sum4Digits = 0
    File(args[0]).forEachLine {
        val line = it.split("|")

        val display10Digits = ArrayList<SevenSegmentDisplay>()
        // left of the | : displaying 1 through 10
        val tenDigits = line[0].split(" ")
        for(digit in tenDigits) {
            display10Digits.add(SevenSegmentDisplay(digit.trim()))
        }

        val display4Digits = ArrayList<SevenSegmentDisplay>()
        // right of the | : displaying 4 digit data
        val fourDigits = line[1].split(" ")
        for(digit in fourDigits) {
            if(digit.isEmpty())
                continue
            display4Digits.add(SevenSegmentDisplay(digit.trim()))
        }

        val displayDecoder = DisplayDecoder()
        sum4Digits += displayDecoder.decodeWithTenDigits(display10Digits, display4Digits)
    }

    println("The output sum: $sum4Digits")


    println("AOC 2021, Day 8.2 complete!!!!")
}

class SevenSegmentDisplay constructor(val displaySegments : String) {
}

fun String.alphabetized() = String(toCharArray().apply { sort() })

class DisplayDecoder {
    private val decoderDictionary = HashMap<Int, String>()

    private fun clearDictionary() {
        decoderDictionary[0] = ""
        decoderDictionary[1] = ""
        decoderDictionary[2] = ""
        decoderDictionary[3] = ""
        decoderDictionary[4] = ""
        decoderDictionary[5] = ""
        decoderDictionary[6] = ""
        decoderDictionary[7] = ""
        decoderDictionary[8] = ""
        decoderDictionary[9] = ""
    }

    private fun initDictionary(tenDigitDisplay: List<SevenSegmentDisplay>) {
        for(display in tenDigitDisplay) {
            if(display.displaySegments.isEmpty()) {
                continue
            }

            // 1 has 2 segments
            if ((display.displaySegments.length == 2) && decoderDictionary[1]!!.isEmpty()) {
                decoderDictionary[1] = display.displaySegments.alphabetized()
            }
            // 7 has 3 segments
            if ((display.displaySegments.length == 3) && decoderDictionary[7]!!.isEmpty()) {
                decoderDictionary[7] = display.displaySegments.alphabetized()
            }
            // 4 has 4 segments
            if ((display.displaySegments.length == 4) && decoderDictionary[4]!!.isEmpty()) {
                decoderDictionary[4] = display.displaySegments.alphabetized()
            }
            // 8 has 7 segments
            if ((display.displaySegments.length == 7) && decoderDictionary[8]!!.isEmpty()) {
                decoderDictionary[8] = display.displaySegments.alphabetized()
            }

            // 0, 6, 9 have 6 segments
            if (display.displaySegments.length == 6) {
                // 9 should have all the segments of 4 and 7.
                if (decoderDictionary[9]!!.isEmpty() && decoderDictionary[4]!!.isNotEmpty() && decoderDictionary[7]!!.isNotEmpty()) {
                    var nineCheck = true
                    for (c in decoderDictionary[7]!!) {
                        nineCheck = nineCheck && display.displaySegments.contains(c)
                    }
                    for (c in decoderDictionary[4]!!) {
                        nineCheck = nineCheck && display.displaySegments.contains(c)
                    }

                    if (nineCheck)
                        decoderDictionary[9] = display.displaySegments.alphabetized()
                }

                // 0 should have 7 along with 4 except for 1 segment
                if (decoderDictionary[0]!!.isEmpty() && decoderDictionary[4]!!.isNotEmpty() && decoderDictionary[7]!!.isNotEmpty()) {
                    var zeroCheck = true
                    for (c in decoderDictionary[7]!!) {
                        zeroCheck = zeroCheck && display.displaySegments.contains(c)
                    }
                    var fourMatch = 0
                    for(c in decoderDictionary[4]!!) {
                        if(display.displaySegments.contains(c))
                            fourMatch++
                    }
                    zeroCheck = zeroCheck && (fourMatch == 3)

                    if(zeroCheck)
                        decoderDictionary[0] = display.displaySegments.alphabetized()
                }

                // 6 is the one other 6 segment that isn't 9 or 0
                if(decoderDictionary[6]!!.isEmpty() && decoderDictionary[9]!!.isNotEmpty() && decoderDictionary[0]!!.isNotEmpty()) {
                    if((display.displaySegments.alphabetized() != decoderDictionary[9]!!) && (display.displaySegments.alphabetized() != decoderDictionary[0]!!)) {
                        decoderDictionary[6] = display.displaySegments.alphabetized()
                    }
                }
            }

            // 2, 3, 5 have 5 segments
            if (display.displaySegments.length == 5) {
                // 3 should have all of 7 while 2 and 5 do not have 7
                if(decoderDictionary[3]!!.isEmpty() && decoderDictionary[7]!!.isNotEmpty()) {
                    var threeCheck = true
                    for(c in decoderDictionary[7]!!) {
                        threeCheck = threeCheck && display.displaySegments.contains(c)
                    }

                    if(threeCheck)
                        decoderDictionary[3] = display.displaySegments.alphabetized()
                }
                // 5 should have all of 6 except 1 segment
                if(decoderDictionary[5]!!.isEmpty() && decoderDictionary[6]!!.isNotEmpty()) {
                    var sixMatch = 0
                    for(c in decoderDictionary[6]!!) {
                        if(display.displaySegments.contains(c))
                            sixMatch++
                    }
                    if(sixMatch == 5)
                        decoderDictionary[5] = display.displaySegments.alphabetized()
                }
                // 2 is the one left that isn't 5 or 3
                if(decoderDictionary[2]!!.isEmpty() && decoderDictionary[3]!!.isNotEmpty() && decoderDictionary[5]!!.isNotEmpty()) {
                    if((display.displaySegments.alphabetized() != decoderDictionary[3]!!) && (display.displaySegments.alphabetized() != decoderDictionary[5]!!))
                        decoderDictionary[2] = display.displaySegments.alphabetized()
                }
            }
        }

        // If entire dictionary isn't defined, which can happen because digits are in any order, call again.
        if(decoderDictionary.values.stream().filter { it.isEmpty() }.count() != 0L)
            initDictionary(tenDigitDisplay)
    }

    private fun decodeDigit(digit : SevenSegmentDisplay) : Int {
        for(d in decoderDictionary) {
            if(d.value == digit.displaySegments.alphabetized())
                return d.key
        }
        return 0
    }

    fun decodeWithTenDigits(tenDigitDisplay: List<SevenSegmentDisplay>, fourDigitDisplay : List<SevenSegmentDisplay>) : Int {
        clearDictionary()
        initDictionary(tenDigitDisplay)

        // Ugly but works since field is fixed to 4 digits
        val thousand = decodeDigit(fourDigitDisplay[0])
        val hundred = decodeDigit(fourDigitDisplay[1])
        val ten = decodeDigit(fourDigitDisplay[2])
        val one = decodeDigit(fourDigitDisplay[3])

        // 10706
        println("Segment Display ${fourDigitDisplay[0].displaySegments} ${fourDigitDisplay[1].displaySegments} ${fourDigitDisplay[2].displaySegments} ${fourDigitDisplay[3].displaySegments} -> $thousand $hundred $ten $one")
        return thousand * 1000 + hundred * 100 + ten * 10 + one
    }
}
