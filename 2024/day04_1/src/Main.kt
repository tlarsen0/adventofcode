import java.io.File
import kotlin.math.max

fun main(args: Array<String>) {
    println("AOC 2024, Day 4, Part 1 starting!!!!")

    val theData = WordSearchData()
    var yPosition = 0
    File(args[0]).forEachLine {
        var xPosition = 0
        for(c in it.toCharArray()) {
            val newWordSearchCharacter = WordSearchCharacter(c, xPosition, yPosition)
            theData.characters.add(newWordSearchCharacter)
            xPosition++
        }
        theData.maxX = xPosition
        yPosition++
    }
    theData.maxY = yPosition

    val answer = theData.searchForXmas()

    println("Word search grid: ${theData.maxX} x ${theData.maxY}")
    println("Number of matches: $answer")

    println("AOC 2024, Day 4, Part 1 completed!!!")
}

class WordSearchData {
    val characters = ArrayList<WordSearchCharacter>()

    var maxX:Int = 0
        set(value) {
            field = max(field, value)
        }
    var maxY:Int = 0
        set(value) {
            field = max(field, value)
        }

    enum class Direction {
        UP_LEFT, UP, UP_RIGHT, LEFT, RIGHT, DOWN_LEFT, DOWN, DOWN_RIGHT, NONE
    }

    fun searchForXmas():Int {
        var matches = 0
        // All characters in word search space
        for(wordSearchCharacter in characters) {
            // Look for matches to on characters XMAS

            for (direction in WordSearchData.Direction.entries) {
                if (wordSearchCharacter.theChar == 'X' &&
                    searchForXmas(wordSearchCharacter, 'M', direction, 1) &&
                    searchForXmas(wordSearchCharacter, 'A', direction, 2) &&
                    searchForXmas(wordSearchCharacter, 'S', direction, 3)
                ) {
                    println("Found XMAS at ${wordSearchCharacter.xPosition}, ${wordSearchCharacter.yPosition} in direction $direction")
                    matches++
                }
            }
        }

        return matches
    }

    private fun searchForXmas(character: WordSearchCharacter, lookFor:Char, direction:Direction, step:Int):Boolean {
        if(direction == Direction.NONE) {
            return false
        }

        var nextX = character.xPosition
        var nextY = character.yPosition
        when (direction) {
            // search up left   : x - 1, y - 1
            Direction.UP_LEFT -> {
                nextX -= step
                nextY -= step
            }
            // search up        : x,     y - 1
            Direction.UP -> {
                nextY -= step
            }
            // search up right  : x + 1, y - 1
            Direction.UP_RIGHT -> {
                nextX += step
                nextY -= step
            }
            // search left      : x - 1, y
            Direction.LEFT -> {
                nextX -= step
            }
            // search right     : x + 1, y
            Direction.RIGHT -> {
                nextX += step
            }
            // search down left : x - 1, y + 1
            Direction.DOWN_LEFT -> {
                nextX -= step
                nextY += step
            }
            // search down      : x,     y + 1
            Direction.DOWN -> {
                nextY += step
            }
            // search down right: x + 1, y + 1
            Direction.DOWN_RIGHT -> {
                nextX += step
                nextY += step
            }
            else -> {
                // do nothing
            }
        }
        if (positionCheck(nextX, nextY)) {
            val nextChar = characters.find { allCharacters -> allCharacters.xPosition == nextX && allCharacters.yPosition == nextY }
            return nextChar?.theChar == lookFor
        }

        // If code arrives here, then false
        return false
    }

    private fun positionCheck(x:Int, y:Int):Boolean {
        if((x < 0) || (y < 0)) return false

        if((x > maxX) || (y > maxY)) return false

        return true
    }
}



data class WordSearchCharacter (val theChar:Char, val xPosition:Int, val yPosition:Int)
