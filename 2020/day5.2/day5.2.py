#!/usr/bin/python

print("Day 5, Part 2 script starting!")


def calculate_first_7(the_line, min_range, max_range):
    new_min = min_range
    new_max = max_range
    count = 0
    c = ''
    for c in the_line:
        if count > 5:
            break
        count += 1
        diff = new_max - new_min
        if c == 'F':
            # new_min = new_min
            new_max = diff / 2 + new_min
            # print('  {2}: front/lower, current_answer = {0} to {1}'.format(new_min, new_max, c))
        if c == 'B':
            new_min = diff / 2 + new_min + 1
            # new_max = new_max
            # print('  {2}: back/upper, current_answer = {0} to {1}'.format(new_min, new_max, c))
    # print('check point!!! {0} to {1} and c = {2}'.format(new_min, new_max, c))
    if c == 'F':
        return new_min
    elif c == 'B':
        return new_max


def calculate_last_3(the_line, min_range, max_range):
    new_min = min_range
    new_max = max_range
    c = ''
    for c in the_line[-3:]:
        diff = new_max - new_min
        if c == 'L':
            new_max = diff / 2 + new_min
            # print('  {2}: left, current_answer = {0} to {1}'.format(new_min, new_max, c))
        if c == 'R':
            new_min = diff / 2 + new_min + 1
            # print('  {2}: right, current_answer = {0} to {1}'.format(new_min, new_max, c))
    # print('check point!! {0} to {1} and c = {2}'.format(new_min, new_max, c))
    if c == 'R':
        return new_min
    elif c == 'L':
        return new_max


with open('input') as seating_file:
    highest_seat_id = 0
    check = {}
    for line in seating_file:
        line = line.rstrip()
        first_7 = calculate_first_7(line, 0, 127)
        last_3 = calculate_last_3(line, 0, 7)
        answer = first_7 * 8 + last_3
        print('{0} is row {1}, column {2}, seat id {3}'.format(line, first_7, last_3, answer))
        # Sanity check: given the nature of the problem seats should be unique. This checks for uniqueness.
        if answer in check:
            print('ENCOUNTERED {0} before!!!'.format(answer))
            break
        check[answer] = 1
        if answer > highest_seat_id:
            highest_seat_id = answer
    print('final answer, highest seat id: {0}'.format(highest_seat_id))

    # find the missing seat??
    sorted_check = sorted(check)
    offset_seat_id = sorted_check[0]
    print('offset_seat_id = {0}'.format(offset_seat_id))
    for n in range(0, len(sorted_check)):
        print('n = {0}, offset_seat_id + n = {1}, sorted_check[n] = {2}'.format(n, n + offset_seat_id, sorted_check[n]))
        if sorted_check[n] != offset_seat_id + n:
            print('missing seat id at {0}'.format(sorted_check[n]))
            break

print('script complete!!!!')
