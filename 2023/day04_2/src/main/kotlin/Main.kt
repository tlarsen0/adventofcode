import java.io.File

fun main(args: Array<String>) {
    println("AOC 2023, Day 4, Part 2 starting!!!")

    val allCards = ArrayList<Card>()
    val matchedCards = HashMap<Int, Int>()
    File(args[0]).forEachLine {
        allCards.add(parseLine(it))
    }

    allCards.stream().forEach {
        val matches = it.countMatches()
        val nextCard = it.cardNumber + 1
        println("For Card ${it.cardNumber}, matches = $matches")
        for(x in 0 ..(matchedCards[it.cardNumber] ?: 0)) {
            for (n in nextCard..(nextCard + (matches - 1))) {
                matchedCards[n] = matchedCards[n]?.plus(1) ?: 1
            }
        }
    }

    // For any cards that doesn't have a matches just increment.
    // Otherwise get the matches + 1.
    var finalScore = 0
    for(card in allCards) {
        if(matchedCards.contains(card.cardNumber)) {
            finalScore += (matchedCards[card.cardNumber]!! + 1)
        } else {
            finalScore++
        }
    }


    println("Final score = $finalScore")

    println("AOC 2023, Day 4, Part 2 completed!!")
}

class Card(line:String) {
    val rightSideNumbers:List<Int> = getRightSideNumbers(line)
    val leftSideNumbers:List<Int> = getLeftSideNumbers(line)
    val cardNumber:Int = getCardNumber(line)

    fun countMatches():Int {
        var matches:Int = 0
        for(n in leftSideNumbers) {
            if(rightSideNumbers.contains(n))
                matches++
        }
        return matches
    }
}

fun parseLine(line:String):Card {
    return Card(line)
}

fun getCardNumber(line:String):Int {
    return line.split(':')[0].substring(5).trim().toInt()
}

fun getLeftSideNumbers(line:String):List<Int> {
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

fun getRightSideNumbers(line: String):List<Int> {
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