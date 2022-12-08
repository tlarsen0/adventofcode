import java.io.File

fun main(args: Array<String>) {
    println("AOC 2022, Day 6, Part 1 starting!!!")

    val packetMarker = ArrayDeque<Char>()
    var markerIndex = 0
    File(args[0]).forEachLine {
        for(ch in it.toCharArray()) {
            if(packetMarker.size < 4) {
                packetMarker.addFirst(ch)
            } else {
                // start adding more from the front but drop the last
                packetMarker.removeLast()
                packetMarker.addFirst(ch)
            }

            if(checkForMarker(packetMarker)) {
                markerIndex++
                println("Packet Marker starts at : $markerIndex.")
                println("Packet Marker looks like: $packetMarker")
                break
            }
            markerIndex++
        }
    }



    println("AOC 2022, Day 6, Part 1 completed!!")
}

fun checkForMarker(packetMarker:ArrayDeque<Char>):Boolean {
    if(packetMarker.size < 3) {
        return false
    }

    var count = 0
    for(ch in packetMarker) {
        count += packetMarker.count { it == ch }
    }

    // if this count is anything but 4 then there are duplicates
    return count == 4
}