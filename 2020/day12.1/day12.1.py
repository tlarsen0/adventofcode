#!/usr/bin/python

print('Day 12, Part 1 scripting starting!')


class Ship:
    def __init__(self):
        self.east = 0
        self.north = 0
        self.direction = 0

    def rotate_left(self, degree):
        self.direction = self.direction - degree
        if self.direction < 0:
            self.direction = 360 + self.direction
        # print('left, self.direction = {0}'.format(self.direction))

    def rotate_right(self, degree):
        self.direction = self.direction + degree
        if self.direction >= 360:
            self.direction = self.direction - 360
        # print('right, self.direction = {0}'.format(self.direction))

    def move_forward(self, distance):
        if self.direction == 0:    # east
            self.east = self.east + distance
        if self.direction == 180:  # west
            self.east = self.east - distance
        if self.direction == 270:  # north
            self.north = self.north + distance
        if self.direction == 90:   # south
            self.north = self.north - distance

    def run_command(self, command):
        print('run_command, command = {0}'.format(command))
        move = command[0]
        move_distance = int(command[1:])
        if move == 'N':
            # print('north by {0}'.format(move_distance))
            self.north = self.north + move_distance
        if move == 'S':
            self.north = self.north - move_distance
        if move == 'E':
            self.east = self.east + move_distance
        if move == 'W':
            self.east = self.east - move_distance
        if move == 'L':
            self.rotate_left(move_distance)
        if move == 'R':
            self.rotate_right(move_distance)
        if move == 'F':
            # print('forward by {0}'.format(move_distance))
            self.move_forward(move_distance)
        # print('check!! east, north, direction = {1}, {0}, {2}'.format(self.north, self.east, self.direction))

    def distance0x0(self):
        return abs(self.north) + abs(self.east)


with open('input') as steering_directions:
    ship = Ship()
    for steering_command in steering_directions:
        steering_command = steering_command.rstrip()
        ship.run_command(steering_command)

    print('Manhattan distance = {0}'.format(ship.distance0x0()))

print('script complete!!!')
