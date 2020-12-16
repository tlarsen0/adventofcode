#!/usr/bin/python

print('Day 6, Part 2 starting.')


def calculate_count(the_group_survey, group_count):
    local_count = 0
    for v in the_group_survey.values():
        if v == group_count:
            local_count = local_count + 1
    return local_count


with open('input') as questions:
    group_survey = {}
    count_in_group = 0
    count_of_groups = 0
    total = 0
    for line in questions:
        line = line.rstrip()
        if not line:
            count = calculate_count(group_survey, count_in_group)
            print('group complete, check group_survey = {0}, count in group = {1}, got count = {2}'.format(group_survey, count_in_group, count))
            # total = total + calculate_count(group_survey, count_in_group)
            total = total + count
            group_survey = {}  # reset group_survey for a new group
            count_in_group = 0
            count_of_groups += 1
        else:
            # add all unique characters to dictionary.
            for c in line:
                if c not in group_survey:
                    group_survey[c] = 0
                group_survey[c] = group_survey[c] + 1
            count_in_group += 1
    # add in the last step?
    print('total = {0}'.format(total))
    print('total groups counted {0}'.format(count_of_groups))

print('scripting complete!!')
