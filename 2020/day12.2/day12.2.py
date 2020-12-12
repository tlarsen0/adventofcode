#!/usr/bin/python

print('Day 12, Part 2 scripting starting!')


class Ship:
    def __init__(self):
        self.east = 0
        self.north = 0
        self.way_point_east = 10
        self.way_point_north = 1

    def rotate_left(self, degree):
        # rotate_left is just a negative rotate_right
        self.rotate_right(360 - degree)
        # print('left, self.direction = {0}'.format(self.direction))

    def rotate_right(self, degree):
        if degree == 90:
            wp_hold = self.way_point_east
            self.way_point_east = self.way_point_north
            self.way_point_north = -wp_hold
        if degree == 180:
            self.way_point_east = -self.way_point_east
            self.way_point_north = -self.way_point_north
        if degree == 270:
            wp_hold = self.way_point_east
            self.way_point_east = -self.way_point_north
            self.way_point_north = wp_hold

    def move_forward(self, distance):
        self.east = self.east + (self.way_point_east * distance)
        self.north = self.north + (self.way_point_north * distance)
        # print('forward, east, north = {0}, {1}'.format(self.east, self.north))

    def run_command(self, command):
        print('run_command, command = {0}'.format(command))
        move = command[0]
        move_distance = int(command[1:])
        if move == 'N':
            self.way_point_north = self.way_point_north + move_distance
            # print('north, way_point_north = {0}', self.way_point_north)
        if move == 'S':
            self.way_point_north = self.way_point_north - move_distance
        if move == 'E':
            self.way_point_east = self.way_point_east + move_distance
        if move == 'W':
            self.way_point_east = self.way_point_east - move_distance
        if move == 'L':
            self.rotate_left(move_distance)
        if move == 'R':
            self.rotate_right(move_distance)
        if move == 'F':
            # print('forward by {0}'.format(move_distance))
            self.move_forward(move_distance)
        # print('check!! east, north = {1}, {0}, {2}'.format(self.north, self.east))

    def distance_manhattan(self):
        return abs(self.north) + abs(self.east)


with open('input') as steering_directions:
    ship = Ship()
    for steering_command in steering_directions:
        steering_command = steering_command.rstrip()
        ship.run_command(steering_command)

    print('Manhattan distance = {0}'.format(ship.distance_manhattan()))

print('script complete!!!')
