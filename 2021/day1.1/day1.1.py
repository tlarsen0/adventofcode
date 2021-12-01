#!/usr/bin/python3

print("2021, Day 1.1 script starting!")

with open('test0') as depth_file:
    increases = 0
    previousDepth = 0
    currentDepth = 0
    linesRead = 0
    for line in depth_file:
        line = line.rstrip()
        linesRead = linesRead + 1
        currentDepth = line
        if previousDepth == 0:
            print("{0} (N/A - no previous measurement)".format(line))
            previousDepth = line
        elif previousDepth < currentDepth:
            increases = increases + 1
            print("{0} (increase {1})".format(line, increases))
        elif previousDepth == currentDepth:
            print("{0} (no change)".format(line))
        else:
            print("{0} (decrease)".format(line))
        previousDepth = currentDepth

    print("Lines read: {0}".format(linesRead))
    print("Number of increases: {0}".format(increases))

print("2021, Day 1.1 script complete!!!!")