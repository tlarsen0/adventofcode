#!/usr/bin/python3

print("AOC 2021, Day 1.2 script starting!!!!")

with open('input') as depth_file:
    linesRead = 0
    linesList = []
    for line in depth_file:
        line = line.rstrip()
        linesList.append(int(line))

    listLength = len(linesList)
    listN = 0

    tripList = list()

    for j in range(0, listLength - 2):
        tripTotal = 0
        for i in range(listN, listN + 3):
            tripTotal = tripTotal + linesList[i]
        tripList.append(tripTotal)
        listN = listN + 1

    # now do what we did before in day1.1
    increases = 0
    previousDepth = 0
    currentDepth = 0
    for tripletLine in tripList:
        currentDepth = tripletLine
        if previousDepth == 0:
            print("{0} (N/A - no previous measurement)".format(tripletLine))
            previousDepth = tripletLine
        elif previousDepth < currentDepth:
            increases = increases + 1
            print("{0} (increase {1})".format(tripletLine, increases))
        elif previousDepth == currentDepth:
            print("{0} (no change)".format(tripletLine))
        else:
            print("{0} (decrease)".format(tripletLine))
        previousDepth = currentDepth

    print("Lines read: {0}".format(linesRead))
    print("Number of increases: {0}".format(increases))

print("AOC 2021, Day 1.2 scripting complete!")
