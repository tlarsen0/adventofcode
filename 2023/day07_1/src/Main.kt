import java.io.File

fun main(args: Array<String>) {
    println("AOC 2023, Day 7, Part 1 starting!!!!")

    val hands = ArrayList<CamelCardHand>()
    File(args[0]).forEachLine {
        val split = it.split(' ')
        hands.add(CamelCardHand(split[0], split[1].toInt()))
    }

    val sortedHands = hands.stream().sorted { o1, o2 -> o1.compareTo(o2) }

    var answer = 0L
    var rank = 1L
    sortedHands.forEach {
        answer += it.bid * rank
        rank++
    }

    println("Total Winnings: $answer")

    println("AOC 2023, Day 7, Part 1 completed!!!")
}

class CamelCardHand (private val hand:String, val bid:Int) {
    fun compareTo(otherHand:CamelCardHand):Int {
        val leftType = calculateType()
        val rightType = otherHand.calculateType()
        if(leftType == rightType) {
            // if there are both the same type, check hand rank
            return compareByRank(otherHand)
        }
        if(leftType > rightType) return 1
        return -1
    }

    private fun compareByRank(otherHand:CamelCardHand):Int {
        for(i in 0..5) {
            val leftChar = hand.toCharArray()[i]
            val rightChar = otherHand.hand.toCharArray()[i]
            if(leftChar.compareTo(rightChar) == 0) {
                continue
            }
            if(cardValue(leftChar) > cardValue(rightChar)) {
                return 1
            }
            return -1
        }
        // shouldn't get here but for completeness
        return 0
    }

    private fun calculateType():HandType {
        val matches = HashMap<Char, Int>()
        for(c in hand.toCharArray()) {
            matches[c] = matches.getOrDefault(c,0) + 1
        }

        if(matches.values.stream().filter { it == 5 }.count() == 1L) {
            return HandType.FIVE_KIND
        }
        if(matches.values.stream().filter { it == 4 }.count() == 1L) {
            return HandType.FOUR_KIND
        }
        if((matches.values.stream().filter { it == 3 }.count() == 1L) && (matches.values.stream().filter { it == 2 }.count() == 1L)) {
            return HandType.FULL_HOUSE
        }
        if(matches.values.stream().filter { it == 3 }.count() == 1L) {
            return HandType.THREE_KIND
        }
        if(matches.values.stream().filter { it == 2 }.count() == 2L) {
            return HandType.TWO_PAIR
        }
        if (matches.values.stream().filter { it == 2}.count() == 1L) {
            return HandType.ONE_PAIR
        }
        if (matches.values.stream().filter { it == 1 }.count() == 5L) {
            return HandType.HIGH_CARD
        }

        return HandType.HIGH_CARD
    }

    private fun cardValue(card:Char):Int {
        return when(card) {
            '2' -> 2
            '3' -> 3
            '4' -> 4
            '5' -> 5
            '6' -> 6
            '7' -> 7
            '8' -> 8
            '9' -> 9
            'T' -> 10
            'J' -> 11
            'Q' -> 12
            'K' -> 13
            'A' -> 14
            else -> 0
        }
    }

    enum class HandType {
        HIGH_CARD,
        ONE_PAIR,
        TWO_PAIR,
        THREE_KIND,
        FULL_HOUSE,
        FOUR_KIND,
        FIVE_KIND
    }

    override fun toString():String {
        return "hand: $hand, bid $bid"
    }
}
