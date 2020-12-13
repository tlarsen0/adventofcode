#!/usr/bin/python


def parse_schedule_line(the_schedule):
    all_busses = []
    for c in the_schedule.split(','):
        if c == 'x':
            continue
        # print('c = {0}'.format(int(c)))
        all_busses.append(int(c))
    return all_busses


print('Day 13, Part1 starting.')

with open('input') as schedule_notes:
    time_guess = int(schedule_notes.readline().rstrip())
    schedule_line = schedule_notes.readline().rstrip()

    busses = parse_schedule_line(schedule_line)
    print('the busses: {0}'.format(busses))
    all_estimates = {}
    for bus in busses:
        est_multiplier = time_guess / bus
        estimate = est_multiplier * bus  # this will calculate close but before target time.
        print('guess for bus {0}: {1}'.format(bus, estimate))
        all_estimates[bus] = estimate + bus
    
    # print('  sorted: {0}'.format(sorted(all_estimates.values())))
    best_time = sorted(all_estimates.values())[0]
    print('  best_time = {0}'.format(best_time))
    best_bus = 0
    for s in all_estimates:
        if all_estimates[s] == best_time:
            best_bus = s
            print('  best bus = {0}'.format(best_bus))
            break
    time_to_wait = best_time - time_guess
    print('Time to wait: {0}'.format(time_to_wait))
    print('Final Answer: {0}'.format(time_to_wait * best_bus))


print('script complete!!')
