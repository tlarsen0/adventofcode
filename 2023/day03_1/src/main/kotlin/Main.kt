import java.io.File

fun main(args: Array<String>) {
    println("AOC 2023, Day 3, Part 1 starting!!!")

    val schematic = Schematic()
    File(args[0]).forEachLine {
        schematic.addLine(it)
    }

    schematic.scanForParts()

    val answer = schematic.getAttachedParts()

    // Search parts for attachments

    println("The answer: $answer")

    println("AOC 2023, Day 3, Part 1 completed!!")
}

class Schematic {
    private val lines:ArrayList<String> = ArrayList()
    private var partsList:ArrayList<Int> = ArrayList()

    fun addLine(newLine:String) {
        lines.add(newLine)
    }

    fun scanForParts() {
        var y = 0
        for(line in lines) {
            var x = 0
            for (c in line) {
                if(!c.isDigit() && (c.compareTo('.') != 0)) {
                    scanForParts(x, y)
                }
                x++
            }
            y++
        }
    }

    private fun scanForParts(x:Int, y:Int) {
        val attachedParts:HashSet<Int> = HashSet()

        val xN1 = x - 1
        xN1.coerceIn(0, getMaxX())
        val xP1 = x + 1
        xP1.coerceIn(0, getMaxX())
        val yN1 = y - 1
        yN1.coerceIn(0, getMaxY())
        val yP1 = y + 1
        yP1.coerceIn(0, getMaxY())

        val c0 = getCharAtXY(xN1, yN1)
        if(c0.isDigit()) {
            attachedParts.add(getPartNumberAt(xN1, yN1))
        }

        val c1 = getCharAtXY(x, yN1)
        if(c1.isDigit()) {
            attachedParts.add(getPartNumberAt(x, yN1))
        }

        val c2 = getCharAtXY(xP1, yN1)
        if(c2.isDigit()) {
            attachedParts.add(getPartNumberAt(xP1, yN1))
        }

        val c3 = getCharAtXY(xN1, y)
        if(c3.isDigit()) {
            attachedParts.add(getPartNumberAt(xN1, y))
        }

        val c4 = getCharAtXY(xP1, y)
        if(c4.isDigit()){
            attachedParts.add(getPartNumberAt(xP1, y))
        }

        val c5 = getCharAtXY(xN1, yP1)
        if(c5.isDigit()) {
            attachedParts.add(getPartNumberAt(xN1, yP1))
        }

        val c6 = getCharAtXY(x, yP1)
        if(c6.isDigit()) {
            attachedParts.add(getPartNumberAt(x, yP1))
        }

        val c7 = getCharAtXY(xP1, yP1)
        if(c7.isDigit()) {
            attachedParts.add(getPartNumberAt(xP1, yP1))
        }

        partsList.addAll(attachedParts)
    }

    private fun getPartNumberAt(x: Int, y: Int):Int {
        val theLine = lines[y]
        var start = x
        var end = x

        while(start >= 1) {
            try {
                if (theLine[start - 1].isDigit()) {
                    start--
                } else {
                    break
                }
            } catch (e:Exception) {
                start = 0
                break
            }
        }

        while(end < getMaxX()) {
            try {
                if (theLine[end + 1].isDigit()) {
                    end++
                } else {
                    break
                }
            } catch (e:Exception) {
                // break out
                end = getMaxX() - 1
                break
            }
        }

        if((start < 0) || end > getMaxX()) {
            throw Exception("For safety do not go out of bounds")
        }

        return theLine.substring(start, end + 1).toInt()
    }

    private fun getCharAtXY(x:Int, y:Int):Char {
        val theLine = lines[y]
        return theLine[x]
    }

    private fun getMaxX():Int {
        return lines[0].length
    }

    private fun getMaxY():Int {
        return lines.size
    }

    fun getAttachedParts():Int {
        var answer = 0
        partsList.stream().forEach { answer += it }
        return answer
    }
}