#!/usr/bin/python

import csv


def parse_line_min_value(seg0):
    the_split = seg0.split('-')
    return int(the_split[0])


def parse_line_max_values(seg0):
    the_split = seg0.split('-')
    return int(the_split[1])


def parse_line_get_character(seg1):
    # abuse the character is always the first character of the segment?
    return seg1[:1]


def validate_password(check_min, check_max, check_character, the_password):
    match_count = the_password.count(check_character)
    # print('  match_count = {0}, min = {1} and max = {2}'.format(match_count, check_min, check_max))
    return (match_count >= check_min) and (match_count <= check_max)


with open('input') as password_file:
    valid_password_count = 0
    invalid_password_count = 0

    reader = csv.reader(password_file, delimiter=' ')
    for line in reader:
        min_range = parse_line_min_value(line[0])
        max_range = parse_line_max_values(line[0])
        character = parse_line_get_character(line[1])

        if validate_password(min_range, max_range, character, line[2]):
            valid_password_count += 1
            print('Password {0} is valid.'.format(line[2]))
        else:
            invalid_password_count += 1
            print('Password {0} is BAD!'.format(line[2]))

print('Final report:')
print('  Good passwords: {0}'.format(valid_password_count))
print('  Bad passwords : {0}'.format(invalid_password_count))
print('  Total         : {0}'.format(valid_password_count + invalid_password_count))

print('script complete!!')
