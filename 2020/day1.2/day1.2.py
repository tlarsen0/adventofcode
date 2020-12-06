#!/usr/bin/python

import csv


def check_for_answer(check_value, dictionary):
    new_dictionary = {}
    for k in dictionary:
        remainder_new = 2020 - check_value - k
        new_dictionary[k] = remainder_new

        if new_dictionary.has_key(remainder_new):
            print('This is a match!!! {0} * {1} * {2} = {3}'.format(k, check_value, remainder_new, k * check_value * remainder_new))
            # Return 0 to break, the other 2 answers same and moot.
            return 0


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

    print('reading complete!')
    # Loop over dictionary keys and check for answer.
    for keyValue in expenseDictionary:
        if check_for_answer(keyValue, expenseDictionary) == 0:
            break
print('script complete!!')


