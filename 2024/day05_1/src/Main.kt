import java.io.File

fun main(args: Array<String>) {
    println("AOC 2024, Day 5, Part 1 starting!!!!")

    val pageOrderRuleList = ArrayList<PageOrderRule>()
    val pageRunList = ArrayList<PageRun>()

    var readPageRules = true
    File(args[0]).forEachLine {
        if(readPageRules && it.isEmpty()) {
            readPageRules = false
            return@forEachLine
        }
        if(readPageRules) {
            val split =  it.split('|')
            pageOrderRuleList.add(PageOrderRule(split[0].toInt(), split[1].toInt()))
        } else {
            val newPageRun = PageRun()
            for (page in it.split(',')) {
                newPageRun.pageList.add(page.toInt())
            }
            pageRunList.add(newPageRun)
        }
    }

    println("Page Order Count: ${pageOrderRuleList.size}")
    println("Page Run Count  : ${pageRunList.size}")

    var middlePageTotal = 0
    for(pageRun in pageRunList) {
        if(pageRun.checkPages(pageOrderRuleList)) {
            val middleIndex = pageRun.pageList.size / 2
            middlePageTotal += pageRun.pageList[middleIndex]
            println("Pages ${pageRun.pageList} is valid, middle page is ${pageRun.pageList[middleIndex]}")
        }
    }

    println("Middle Page Total: $middlePageTotal")

    println("AOC 2024, Day 5, Part 1 completed!!!")
}

class PageOrderRule (val pageBefore:Int, val pageAfter:Int)

class PageRun {
    val pageList = ArrayList<Int>()

    fun checkPages(pageOrderRule: ArrayList<PageOrderRule>): Boolean {
        val combinedBeforeRules = ArrayList<PageOrderRule>()
        val combinedAfterRules = ArrayList<PageOrderRule>()

        for (page in pageList) {
            val beforePages = pageOrderRule.filter { it.pageBefore == page }
            val afterPages = pageOrderRule.filter { it.pageAfter == page }

            beforePages.forEach { combinedBeforeRules.add(it) }
            afterPages.forEach { combinedAfterRules.add(it) }
        }

        return validatePage(pageList, combinedBeforeRules, combinedAfterRules)
    }

    private fun validatePage(
        pageList: ArrayList<Int>,
        combinedBeforeRules: ArrayList<PageOrderRule>,
        combinedAfterRules: ArrayList<PageOrderRule>
    ): Boolean {
        for(pageIndex in 0 until pageList.size) {
            val pageCurrent = pageList[pageIndex]

            if(pageCurrent == pageList.first()) {
                // if page is the first in pageList, don't check before rule
                var afterValid = false
                val pageAfter = pageList[pageIndex + 1]
                combinedAfterRules.forEach {
                    if(it.pageBefore == pageCurrent && it.pageAfter == pageAfter) {
                        afterValid = true
                    }
                }
                if(!afterValid) return false
            } else if (pageCurrent == pageList.last()) {
                // if page is the last in page list, don't check after rule
                var localValid = false
                val pageBefore = pageList[pageIndex - 1]
                combinedBeforeRules.forEach {
                    if(it.pageBefore == pageBefore && it.pageAfter == pageCurrent) {
                        localValid = true
                    }
                }
                if(!localValid) return false
            } else {
                // check this page follows before and after.
                val pageBefore = pageList[pageIndex - 1]
                val pageAfter = pageList[pageIndex + 1]
                var beforeValid = false
                var afterValid = false
                combinedBeforeRules.forEach {
                    if (it.pageBefore == pageBefore && it.pageAfter == pageCurrent) {
                        beforeValid = true
                    }
                }
                combinedAfterRules.forEach {
                    if (it.pageBefore == pageCurrent && it.pageAfter == pageAfter) {
                        afterValid = true
                    }
                }
                if(!beforeValid || !afterValid) return false
            }

        }
        return true
    }
}