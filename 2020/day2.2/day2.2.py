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
    # print('  check_character = {0}, the_password = {1}'.format(check_character, the_password))
    pos1_check = the_password[check_min - 1] == check_character
    # print('  the_password[...] = \'{0}\' == \'{1}\' then {2}'.format(the_password[check_min - 1], check_character, pos1_check))
    pos2_check = the_password[check_max - 1] == check_character
    # print('  the_password[...] = \'{0}\' == \'{1}\' then {2}'.format(the_password[check_max - 1], check_character, pos2_check))
    # if pos1_check:
    #     print('  pos1 is confirmed')
    # else:
    #     print('  pos1 is not confirmed!!')
    # if pos2_check:
    #     print('  pos2 is confirmed')
    # else:
    #     print('  pos2 is not confirmed!!')
    return (pos1_check or pos2_check) and (pos1_check != pos2_check)


with open('input') as password_file:
    valid_password_count = 0
    invalid_password_count = 0

    reader = csv.reader(password_file, delimiter=' ')
    for line in reader:
        good_check = parse_line_min_value(line[0])
        bad_check = parse_line_max_values(line[0])
        character = parse_line_get_character(line[1])

        if validate_password(good_check, bad_check, character, line[2]):
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
