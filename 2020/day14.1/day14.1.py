#!/usr/bin/python3

print('Day 14, Part 1 scripting starting!')


class DockingMachine:

    def __init__(self):
        self.mask = ''
        self.memory = {}

    def set_mask(self, new_mask):
        self.mask = new_mask[::-1]

    def load_operation(self, memory_command, operation):
        print('self.mask = {0}, memory_location = {2}, operation = {1}'.format(self.mask, operation, memory_command))
        # each character in self.mask, reversed
        
        # for s in self.mask[::-1]:
        #    print('  s = {0}'.format(s))

        memory_location = memory_command[4:-1]
        val = 0
        op = int(operation)
        # for pos in range(0, 36):
        for pos in range(len(self.mask)):
            # print('mask[{1}] = {0}'.format(self.mask[pos], pos))
            if self.mask[pos] == 'X':
                # copy the value from operation forward
                val = val + (op & pow(2, pos))
            if self.mask[pos] == '0':
                val = val + (0 & pow(2, pos))
            if self.mask[pos] == '1':
                val = val + pow(2, pos)
            # print('val = {0}'.format(val))

        print('storing at {0} the value {1}'.format(memory_location, val))
        self.memory[memory_location] = val

    def run_operations(self):
        return sum(self.memory.values())


with open('input') as instructions:
    program = DockingMachine()
    for line in instructions:
        if not line:
            continue
        # 'mask' starts the group
        line = line.rstrip()
        # print('line = {0}'.format(line))
        line_parts = line.split(' = ')
        # print('line_parts = {0}'.format(line_parts))
        if line_parts[0] == 'mask':
            program.set_mask(line_parts[1])
        elif line_parts[0][:3] == 'mem':
            program.load_operation(line_parts[0], line_parts[1])
    answer = program.run_operations()
    print('answer = {0}'.format(answer))


print('scripting complete!!!')
