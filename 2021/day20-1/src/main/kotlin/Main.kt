import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 20.1 starting!!!!")

    val imageEnhancement = ImageEnhancementAlgorithm()
    val inputImage = InputImage()
    File(args[0]).forEachLine {
        // args[0] should read /Users/tlarsen/src/adventofcode/2021/DAY-PART/src/main/resources
        if (imageEnhancement.isNotSet()) {
            // imageEnhancement should be first line, read/set only once
            imageEnhancement.readLine(it)
        } else if(it.isNotEmpty()) {
            inputImage.readLine(it)
        }
    }

    println("Starting with Image Data is ${inputImage.maxX} X ${inputImage.maxY}")
    inputImage.dumpImage()

    println("Enhance Image Step 0")
    inputImage.enhanceImage(imageEnhancement)
    inputImage.dumpImage()

    println("Enhance Image Step 1")
    inputImage.enhanceImage(imageEnhancement)
    inputImage.dumpImage()

    println("Number of pixels lighted: " + inputImage.countEnabledPixels())

    println("AOC 2021, Day 20.1 complete!!!!")
}

class ImageEnhancementAlgorithm {
    val bitArray = ArrayList<Int>()

    fun readLine(line: String) {
        for(ch in line) {
            if(ch == '.') {
                bitArray.add(0)
            }
            if(ch == '#') {
                bitArray.add(1)
            }
        }
    }

    fun isNotSet() : Boolean {
        return bitArray.size != 512
    }
}

data class MyCoord constructor(val x: Int, val y: Int)

class InputImage {
    private var imageData  = HashMap<MyCoord, Int>()

    var minX : Int = 0
        private set
    var minY : Int = 0
        private set
    var maxX : Int = 0
        private set
    var maxY : Int = 0
        private set

    fun readLine(line: String) {
        var x = 0
        for(ch in line) {
            if(ch == '.') {
                imageData[MyCoord(x, maxY)] = 0
            }
            if(ch == '#') {
                imageData[MyCoord(x, maxY)] = 1
            }
            x++
            maxX = Math.max(maxX, x)
        }
        maxY++
    }

    fun enhanceImage(iea: ImageEnhancementAlgorithm) {
        val newImage = HashMap<MyCoord, Int>()

        maxX += 1
        maxY += 1
        minX -= 1
        minY -= 1
        // 1 -> 5290, 5318, 5811??, 5341??, 5103
        // 2 -> 5357
        // 3 -> 5777
        // 16 -> 11965

        for(y in minY until maxY) {
            for(x in minX until maxX) {
                val thePixel = MyCoord(x, y)

                val upperLeft  = MyCoord(thePixel.x - 1, thePixel.y - 1)
                val upPixel    = MyCoord(thePixel.x, thePixel.y - 1)
                val upperRight = MyCoord(thePixel.x + 1, thePixel.y - 1)

                val leftPixel  = MyCoord(thePixel.x - 1, thePixel.y)
                val rightPixel = MyCoord(thePixel.x + 1, thePixel.y)

                val lowerLeft  = MyCoord(thePixel.x - 1, thePixel.y + 1)
                val downPixel  = MyCoord(thePixel.x, thePixel.y + 1)
                val lowerRight = MyCoord(thePixel.x + 1, thePixel.y + 1)

                var bitValues = ""
                bitValues += imageData.getOrDefault(upperLeft,0).toString()
                bitValues += imageData.getOrDefault(upPixel, 0).toString()
                bitValues += imageData.getOrDefault(upperRight,0).toString()

                bitValues += imageData.getOrDefault(leftPixel, 0).toString()
                bitValues += imageData.getOrDefault(thePixel, 0).toString()
                bitValues += imageData.getOrDefault(rightPixel, 0).toString()

                bitValues += imageData.getOrDefault(lowerLeft, 0).toString()
                bitValues += imageData.getOrDefault(downPixel, 0).toString()
                bitValues += imageData.getOrDefault(lowerRight, 0).toString()

                var theIntValue : Int
                if(!imageData.containsKey(thePixel)) {
                    theIntValue = -1
                } else {
                    theIntValue = convertBinaryToDecimal(bitValues.toLong())
                }
                //println("tlarsen,L124: $thePixel -> " + imageData.containsKey(thePixel))
                if(imageData.containsKey(thePixel)) {
                    newImage[thePixel] = iea.bitArray[theIntValue]
                } else {
                    //println("tlarsen,L128: minX = $minX, maxX = $maxX, pixel = $thePixel")
                    //println("tlarsen,L129: minY = $minY, maxY = $maxY, pixel = $thePixel")
                    if(theIntValue == -1) {
                        var offIndex = iea.bitArray.first()
                        if((maxX % 2) == 0) {
                            offIndex = iea.bitArray.last()
                        }
                        println("tlarsen,L141: " + iea.bitArray[offIndex])
                        newImage[thePixel] = iea.bitArray[offIndex]
                    } else {
                        newImage[thePixel] = iea.bitArray[theIntValue]
                    }
                }
            }
        }

        imageData = newImage
    }

    private fun convertBinaryToDecimal(numIn: Long): Int {
        var num = numIn
        var decimalNumber = 0
        var i = 0
        var remainder: Long

        while (num.toInt() != 0) {
            remainder = num % 10
            num /= 10
            decimalNumber += (remainder * Math.pow(2.0, i.toDouble())).toInt()
            ++i
        }
        return decimalNumber
    }

    fun dumpImage() {
        for(y in minY until maxY) {
            for(x in minX until maxX) {
                if(imageData[MyCoord(x, y)] == 1) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println()
        }
    }

    fun countEnabledPixels() : Int {
        return imageData.values.count { it == 1}
    }
}
