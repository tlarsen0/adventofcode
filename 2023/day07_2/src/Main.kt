import java.io.File

fun main(args: Array<String>) {
    println("AOC 2023, Day 7, Part 2 starting!!!!")

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

    println("AOC 2023, Day 7, Part 2 completed!!!")
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
        // Calculate J/Wildcards
        val jokerCount: Int
        if(matches.keys.contains('J')) {
            jokerCount = matches.getOrDefault('J', 1)
            // Skip the case where hand is JJJJJ
            if(jokerCount != 5) {
                val maxEntry = findMaxEntry(matches)
                if (matches.values.count { it == 1 } == 5) {
                    val maxCard = matches.entries.maxBy { cardValue(it.key) }.key
                    matches[maxCard] = maxEntry.value + jokerCount
                } else {
                    matches[maxEntry.key] = maxEntry.value + jokerCount
                }

                matches.remove('J')
            }
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

    private fun findMaxEntry(matches:HashMap<Char, Int>):Map.Entry<Char, Int> {
        val maxByValue = matches.entries.filter { it.key.compareTo('J') != 0 }.maxBy { it.value }
        val maxByCard = matches.entries.filter { it.value == maxByValue.value }.maxBy { cardValue(it.key) }
        return maxByCard
    }

    private fun cardValue(card:Char):Int {
        return when(card) {
            'J' -> 1
            '2' -> 2
            '3' -> 3
            '4' -> 4
            '5' -> 5
            '6' -> 6
            '7' -> 7
            '8' -> 8
            '9' -> 9
            'T' -> 10
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
