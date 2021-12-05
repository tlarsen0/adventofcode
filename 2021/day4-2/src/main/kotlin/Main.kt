import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun main(args: Array<String>) {
    println("AOC 2021, Day 4.1 starting!!!!")

    val drawPool = DrawPool()
    val bingoBoards = ArrayList<BingoBoard>()
    var newBingoBoard = BingoBoard("board:0")
    File(args[0]).forEachLine {
        if (it.isNotEmpty()) { // skip empty lines
            if (!drawPool.loaded) { // only reads the first line, once. skips the rest of the time.
                drawPool.readLine(it)
            } else if (newBingoBoard.isNotLoaded()) {
                newBingoBoard.readLine(it)
            } else {
                bingoBoards.add(newBingoBoard)
                newBingoBoard = BingoBoard("board:${bingoBoards.size}")
                newBingoBoard.readLine(it)
            }
        }
    }
    // add the last board.
    bingoBoards.add(newBingoBoard)

    println("Playing Bingo with ${bingoBoards.size} boards.")
    println("Draw Pool has ${drawPool.drawData.size} draws.")

    // play the game!
    var calledOnNumber : Long = 0
    var bingoCalculation : Long = 0
    val boardsCleared : HashMap<String, Long> = HashMap()
    for (drawNumber in drawPool.drawData) {
        for (board in bingoBoards) {
            if(boardsCleared.containsKey(board.boardName)) {
                continue
            }
            bingoCalculation = board.play(drawNumber)
            if (bingoCalculation != 0L) {
                calledOnNumber = drawNumber
            }
            if (bingoCalculation != 0L) {
                println("Bingo on     $calledOnNumber")
                println("  Board Name ${board.boardName}")
                println("Row total    $bingoCalculation")
                println("Final Score: " + calledOnNumber * bingoCalculation)
                boardsCleared.put(board.boardName, (calledOnNumber * bingoCalculation))
            }
        }
    }

    println("AOC 2021, Day 4.2 complete!!!!")
}

class BingoBoard constructor (_boardName : String) {
    var boardName : String private set

    init {
        boardName = _boardName
    }
    private var boardLayout : ArrayList<ArrayList<Long>> = ArrayList()
    private var playedNumbers : ArrayList<Long> = ArrayList()

    fun isNotLoaded() : Boolean {
        return boardLayout.size <= 4
    }

    fun readLine(line : String) {
        val scanner = Scanner(line)
        val newRow = ArrayList<Long>()
        while(scanner.hasNextLong()) {
            newRow.add(scanner.nextLong())
        }

        boardLayout.add(newRow)
    }

    // return true if bingo?
    fun play(n : Long) : Long {
        playedNumbers.add(n)

        return checkForBingo()
    }

    // Checks for 5 in a row horizontal or vertical.
    private fun checkForBingo() : Long {
        // Check for horizontal bingo.
        for (row in boardLayout) {
            var rowCheck = true
            var rowCalculation = 0L
            for (r in row) {
                rowCheck = rowCheck && playedNumbers.contains(r)
                rowCalculation += r
            }
            // if a row is all in playedNumbers then return true right now.
            if (rowCheck) {
                println("bingo on row, $boardName!")
                println("  $boardLayout")
                return sumUnmarked()
            }
        }

        // Check for verticle bingo.
        for(i in 0 .. 4) {
            var columnCheck = true
            var columnCalculation = 0L
            for (c in boardLayout) {
                columnCheck = columnCheck && playedNumbers.contains(c[i])
                columnCalculation += c[i]
            }
            // if a column is in all in playedNumbers then return true right now.
            if (columnCheck) {
                println("bingo on column, $boardName!")
                println("  $boardLayout")
                return sumUnmarked()
            }
        }

        // No matches on rows or columns so return false
        return 0
    }

    // Return the total all the board that hasn't been played/marked yet.
    private fun sumUnmarked() : Long {
        var total : Long = 0

        for (row in boardLayout) {
            for (i in row) {
                if (!playedNumbers.contains(i)) {
                    //println("tlarsen,L120: $i is not on the board!")
                    total += i
                } else {
                    //println("tlarsen,L123: $i is on the board so skip it")
                }
            }
        }

        return total
    }
}

class DrawPool {
    var loaded : Boolean = false
        private set

    var drawData : ArrayList<Long> = ArrayList()
        private set

    fun readLine(line : String) {
        if (loaded) {
            // only set/load once
            return
        }

        for (s in line.split(",").stream()) {
            drawData.add(s.toLong())
        }

        loaded = true
    }
}