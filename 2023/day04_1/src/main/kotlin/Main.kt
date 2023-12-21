import java.io.File

fun main(args: Array<String>) {
    println("AOC 2023, Day 4, Part 1 starting!!!")

    val games = HashMap<Int, Int>()

    File(args[0]).forEachLine {
        parseLine(it,games)
    }

    var finalScore = 0
    games.forEach { (_, u) ->  finalScore += u}

    println("Final score = $finalScore")

    println("AOC 2023, Day 4, Part 1 completed!!")
}

fun parseLine(line:String, games:HashMap<Int, Int>) {
    val cardNumber = getCardNumber(line)
    val winningDraws = getWinningDraws(line)
    val myCard = getMyCard(line)
    var wins = 0
    myCard.stream().forEach {
        if (winningDraws.contains(it)) {
            wins++
        }
    }

    games[cardNumber] = countScore(wins)
}

fun countScore(wins:Int):Int {
    if(wins == 0) {
        return 0
    }
    if(wins == 1) {
        return 1
    }
    return 2 * countScore(wins - 1)
}

fun getCardNumber(line:String):Int {
    return line.split(':')[0].substring(5).trim().toInt()
}

fun getWinningDraws(line:String):List<Int> {
    val list = ArrayList<Int>()
    val str = line.split(':')[1].split('|')[0]
    for(strNumber in str.split(' ')) {
        if(strNumber.isBlank()) {
            continue
        }
        list.add(strNumber.toInt())
    }
    return list
}

fun getMyCard(line: String):List<Int> {
    val list = ArrayList<Int>()
    val str = line.split(':')[1].split('|')[1]
    for(strNumber in str.split(' ')) {
        if(strNumber.isBlank()) {
            continue
        }
        list.add(strNumber.toInt())
    }
    return list
}