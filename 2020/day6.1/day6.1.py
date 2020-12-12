#!/usr/bin/python

print('Day 6, Part 1 starting.')

# people_answers = []

with open('input') as questions:
    group_survey = {}
    total = 0
    for line in questions:
        line = line.rstrip()
        if not line:
            # people_answers.append(group_survey)
            total = total + len(group_survey.keys())
            group_survey = {}  # reset group_survey for a new group
        else:
            # add all unique characters to dictionary.
            for c in line:
                group_survey[c] = 1
    # add in the last step?
    total = total + len(group_survey.keys())
    # print('check!! people_answers = {0}'.format(people_answers))
    print('total = {0}'.format(total))

print('scripting complete!!')
