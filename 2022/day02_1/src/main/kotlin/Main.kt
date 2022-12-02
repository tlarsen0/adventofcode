import java.io.File

fun main(args: Array<String>) {
    println("AOC 2022, Day 2, Part 1 starting!!")

    val turns = ArrayList<RockPaperScissorsTurn>()

    File(args[0]).forEachLine {
        processLine(it, turns)
    }

    val scores = turns.sumOf { it.theScore() }

    println("The sum of scores: $scores")

    println("AOC 2022, Day 2, Part 1 completed!!")
}

class RockPaperScissorsTurn constructor (private val opponent:Char, private val mine:Char) {
    // Opponent A = rock    = 1
    // Opponent B = paper   = 2
    // Opponent C = scissor = 3

    private fun opponentMove() : String {
        return when (opponent) {
            'A' -> "Rock"
            'B' -> "Paper"
            'C' -> "Scissor"
            else -> "INVALID"
        }
    }

    private fun myMove() : String {
        return when (mine) {
            'X' -> "Rock"
            'Y' -> "Paper"
            'Z' -> "Scissor"
            else -> "INVALID"
        }
    }

    // Mine X = rock
    // Mine Y = paper
    // Mine Z = scissor

    // Outcome loss = 0
    // Outcome tie  = 3
    // outcome win  = 6

    // score = opponent X + outcome Y
    fun theScore() : Int {
        var score = when(mine) {
            'X' -> 1
            'Y' -> 2
            'Z' -> 3
            else -> -1
        }


        println("  ${opponentMove()} vs ${myMove()}...")
        score += outcome(opponent, mine)

        return score
    }

    private fun outcome(opponent: Char, mine: Char) : Int {
        // rock vs rock = tie
        if((opponent == 'A') && (mine == 'X')) {
            return 3
        }
        // rock vs paper = win
        if((opponent == 'A') && (mine == 'Y')) {
            return 6
        }
        // rock vs scissor = loss
        if((opponent == 'A') && (mine == 'Z')) {
            return 0
        }

        // paper vs rock = loss
        if((opponent == 'B') && (mine == 'X')) {
            return 0
        }
        // paper vs paper = tie
        if((opponent == 'B') && (mine == 'Y')) {
            return 3
        }
        // paper vs scissors = win
        if((opponent == 'B') && (mine == 'Z')) {
            return 6
        }

        // scissor vs rock = win
        if((opponent == 'C') && (mine == 'X')) {
            return 6
        }
        // scissor vs paper = loss
        if((opponent == 'C') && (mine == 'Y')) {
            return 0
        }
        // scissor vs scissor = tie
        if((opponent == 'C') && (mine == 'Z')) {
            return 3
        }
        throw Exception("outside params!")
    }
}

fun processLine(line:String, turns: ArrayList<RockPaperScissorsTurn>) {
    val opponent = line[0]
    val mine = line[2]

    turns.add(RockPaperScissorsTurn(opponent, mine))
}