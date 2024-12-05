import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {
    println("AOC 2024, Day 2, Part 2 starting!!!!")

    val allReports = AllReports()

    File(args[0]).forEachLine {
        val newReport = Report()
        for (s in it.split(' ')) {
            newReport.levels.add(s.toInt())
        }
        allReports.reports.add(newReport)
    }

    val safeCount = allReports.calculateSafeReports()

    println("Safe reports: $safeCount")


    println("AOC 2024, Day 2, Part 2 completed!!!")
}

class AllReports {
    val reports = ArrayList<Report>()

    fun calculateSafeReports():Int {
        return reports.count { it.safeLevels(it.levels, false) }
    }
}

class Report {
    val levels = ArrayList<Int>()

    fun safeLevels(lvl:ArrayList<Int>, triggered:Boolean):Boolean {
        var previousLevel = lvl.first()
        var alwaysDecreasing = false
        var alwaysIncreasing = false
        var unsafeTrigger = false
        val unsafeLevels = ArrayList<Int>()

        for(l in lvl.drop(1)) {
            if(l == previousLevel) {
                // the levels aren't increasing or decreasing, break and return false
                unsafeTrigger = true
            }

            if(l < previousLevel) {
                alwaysDecreasing = true
            }
            if(l > previousLevel) {
                alwaysIncreasing = true
            }

            // If any time it these are both true, then break and return false.
            if(alwaysDecreasing && alwaysIncreasing) {
                unsafeTrigger = true
            }

            // If increasing or decreasing more than 3, record it
            if(abs(l - previousLevel) > 3) {
                unsafeTrigger = true
            }

            if(unsafeTrigger) {
                unsafeLevels.add(l)
            }

            previousLevel = l
        }


        if(unsafeLevels.isEmpty()) {
            return true
        } else if(unsafeTrigger && !triggered) {
            for(i in 0..<lvl.size) {
                val shrunkLevel = ArrayList<Int>(lvl)
                shrunkLevel.removeAt(i)
                if(safeLevels(shrunkLevel, true)) {
                    return true
                }
            }
        }

        // if executed to here, return false/unsafe.
        return false
    }
}