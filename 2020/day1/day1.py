#!/usr/bin/python

import csv

with open('input') as csvFile:
    reader = csv.reader(csvFile)
    expenseDictionary = {}
    answer = 0
    print('before loop:')
    for row in reader:
        if row:
            intValue = int(row[0])
            remainder2020 = 2020 - intValue

            expenseDictionary[intValue] = remainder2020
            if expenseDictionary.has_key(remainder2020):
                answer = intValue * remainder2020
                print('This is a match!!! The answer is: {0} * {1} = {2}'.format(intValue, remainder2020, answer))
                break

    print('done!')
