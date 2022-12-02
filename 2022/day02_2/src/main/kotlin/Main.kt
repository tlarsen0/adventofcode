import java.io.File

fun main(args: Array<String>) {
    println("AOC 2022, Day 2, Part 2 starting!!")

    val turns = ArrayList<RockPaperScissorsTurn>()

    File(args[0]).forEachLine {
        processLine(it, turns)
    }

    val scores = turns.sumOf { it.theScore() }

    println("The sum of scores: $scores")

    println("AOC 2022, Day 2, Part 2 completed!!")
}

class RockPaperScissorsTurn {
    constructor (opponentVal:Char, resultVal:Char) {
        opponent = opponentVal
        result = resultVal

        // Opponent picks rock...
        if(opponent == 'A') {
            //...need to lose, so my pick is scissor
            if(result == 'X') {
                mine = 'S'
            }
            //...need to tie, so pick rock
            if(result == 'Y') {
                mine = 'R'
            }
            //...need to win, so pick paper
            if(result == 'Z') {
                mine = 'P'
            }
        }
        // Opponent picks paper...
        if(opponent == 'B') {
            //...need to lose, so pick rock
            if(result == 'X') {
                mine = 'R'
            }
            //...need to tie, so pick paper
            if(result == 'Y') {
                mine = 'P'
            }
            //...need to win, so pick scissor
            if(result == 'Z') {
                mine = 'S'
            }
        }
        // Opponent pick scissor...
        if(opponent == 'C') {
            //...need to lose, so pick paper
            if(result == 'X') {
                mine = 'P'
            }
            //...need to tie, so pick sicssor
            if(result == 'Y') {
                mine = 'S'
            }
            //...need to win, so pick rock
            if(result == 'Z') {
                mine = 'R'
            }
        }
    }

    // Opponent A = rock    = 1
    // Opponent B = paper   = 2
    // Opponent C = scissor = 3

    // Result X = need to lose
    // Result Y = need to draw
    // Result Z = need to win

    private var opponent:Char = '*'
    private var result:Char = '*'
    private var mine:Char = '*'

    private fun opponentMove() : String {
        return when (opponent) {
            'A' -> "Rock"
            'B' -> "Paper"
            'C' -> "Scissor"
            else -> "INVALID"
        }
    }

    private fun finalResult():String {
        return when(result) {
            'X' -> "Need to lose"
            'Y' -> "Need to draw"
            'Z' -> "Need to win"
            else -> "INVALID"
        }
    }

    private fun myMove() : String {
        return when (mine) {
            'R' -> "Rock"
            'P' -> "Paper"
            'S' -> "Scissor"
            else -> "INVALID"
        }
    }

    // Outcome loss = 0
    // Outcome tie  = 3
    // outcome win  = 6

    // score = opponent X + outcome Y
    fun theScore() : Int {
        var score = when(mine) {
            'R' -> 1
            'P' -> 2
            'S' -> 3
            else -> -1
        }

        score += outcome(opponent, mine)

        return score
    }

    private fun outcome(opponent: Char, mine: Char) : Int {
        // rock vs rock = tie
        if((opponent == 'A') && (mine == 'R')) {
            return 3
        }
        // rock vs paper = win
        if((opponent == 'A') && (mine == 'P')) {
            return 6
        }
        // rock vs scissor = loss
        if((opponent == 'A') && (mine == 'S')) {
            return 0
        }

        // paper vs rock = loss
        if((opponent == 'B') && (mine == 'R')) {
            return 0
        }
        // paper vs paper = tie
        if((opponent == 'B') && (mine == 'P')) {
            return 3
        }
        // paper vs scissors = win
        if((opponent == 'B') && (mine == 'S')) {
            return 6
        }

        // scissor vs rock = win
        if((opponent == 'C') && (mine == 'R')) {
            return 6
        }
        // scissor vs paper = loss
        if((opponent == 'C') && (mine == 'P')) {
            return 0
        }
        // scissor vs scissor = tie
        if((opponent == 'C') && (mine == 'S')) {
            return 3
        }
        throw Exception("outside params! $opponent and $mine")
    }
}

fun processLine(line:String, turns: ArrayList<RockPaperScissorsTurn>) {
    val opponent = line[0]
    val result = line[2]

    turns.add(RockPaperScissorsTurn(opponent, result))
}