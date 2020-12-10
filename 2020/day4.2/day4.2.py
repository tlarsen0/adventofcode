#!/usr/bin/python

import re

print('script starting')


class Person:

    def __init__(self):
        # Birth Year
        self.byr = ''
        # Issue Year
        self.iyr = ''
        # Expiration Year
        self.eyr = ''
        # Height
        self.hgt = ''
        # Hair Color
        self.hcl = ''
        # Eye Color
        self.ecl = ''
        # Passport ID
        self.pid = ''
        # Country ID
        self.cid = ''

    def set_value(self, key, value):
        if key == 'byr':
            self.byr = value
        elif key == 'iyr':
            self.iyr = value
        elif key == 'eyr':
            self.eyr = value
        elif key == 'hgt':
            self.hgt = value
        elif key == 'hcl':
            self.hcl = value
        elif key == 'ecl':
            self.ecl = value
        elif key == 'pid':
            self.pid = value
        elif key == 'cid':
            self.cid = value

    def validate_birth_year(self):
        try:
            birth_year = int(self.byr)
            # print('birth_year = {0}'.format(birth_year))
            return 1920 <= birth_year <= 2002
        except ValueError:
            # Something bad: invalid literal trying to convert text to a number. Return False.
            return False

    def validate_issue_year(self):
        try:
            issue_year = int(self.iyr)
            # print('issue_year = {0}'.format(issue_year))
            return 2010 <= issue_year <= 2020
        except ValueError:
            # ValueError is can be generated when trying to covert text to number. Return False.
            return False

    def validate_expiration_year(self):
        try:
            expiration_year = int(self.eyr)
            # print('expiration_year = {0}'.format(expiration_year))
            return 2020 <= expiration_year <= 2030
        except ValueError:
            # ValueError is can be generated when trying to convert text to number. Return False.
            return False

    def validate_height(self):
        try:
            units = self.hgt[-2:]
            height = self.hgt[:-2]
            # print('self.hgt = {0}, units = {1}, height = {2}'.format(self.hgt, units, height))
            if units == 'cm':
                # between 150cm and 193cm
                return 150 <= int(height) <= 193
            elif units == 'in':
                # between 59in and 76in
                return 59 <= int(height) <= 76
            else:
                # Unit couldn't be read or missing or be determined. Return False for failing "cm or in" requirement.
                # print('could not read UNIT! Return False')
                return False
        except ValueError:
            # ValueError is can be generated when trying to convert text to number. Return False.
            return False

    def validate_hair_color(self):
        # print('validate_hair_color, self.hcl = {0}'.format(self.hcl))
        # Regex: match starting with #, characters 0 to 9 or a to f, only 6 times anchored right.
        return len(re.findall('#[0-9a-f]{6}$', self.hcl)) != 0

    def validate_eye_color(self):
        # print('validate_eye_color, self.ecl = {0}'.format(self.ecl))
        return self.ecl == 'amb' or self.ecl == 'blu' or self.ecl == 'brn' or self.ecl == 'gry' or self.ecl == 'grn' or self.ecl == 'hzl' or self.ecl == 'oth'

    def validate_passport_id(self):
        # print('validate_passport_id, self.pid = {0}, len = {1}'.format(self.pid, len(self.pid)))
        return len(re.findall('^[0-9]{9}$', self.pid)) != 0

    def check_valid(self):
        # Uncomment to debug:
        # print('-----')
        # print('self.byr = {0}->{3}, self.iyr = {1}->{4}, self.eyr = {2}->{5}'.format(self.byr, self.iyr, self.eyr, self.validate_birth_year(), self.validate_issue_year(), self.validate_expiration_year()))
        # print('self.hgt = {0}->{3}, self.hcl = {1}->{4}, self.ecl = {2}->{5}'.format(self.hgt, self.hcl, self.ecl, self.validate_height(), self.validate_hair_color(), self.validate_eye_color()))
        # print('self.pid = {0}->{2}, self.cid = {1}'.format(self.pid, self.cid, self.validate_passport_id()))
        # print('-----')
        if self.byr == '' or self.iyr == '' or self.eyr == '' or self.hgt == '' or self.hcl == '' or self.ecl == '' or self.pid == '':
            return False
        return self.validate_birth_year() and self.validate_issue_year() and self.validate_expiration_year() and self.validate_height() and self.validate_hair_color() and self.validate_eye_color() and self.validate_passport_id()


with open('input') as identity_file:
    person = Person()
    valid_passport_count = 0
    invalid_passport_count = 0
    total_read = 0
    for line in identity_file:
        if not line.lstrip():
            # Blank lines complete an identity.
            total_read += 1
            if person.check_valid():
                print('person {0} has a valid information'.format(total_read))
                valid_passport_count += 1
            else:
                print('person {0} does not have valid information'.format(total_read))
                invalid_passport_count += 1
            # blank/reset person to start over processing-check
            person = Person()
            continue
        line = line.rstrip()
        # print('line = {0}'.format(line))
        for component in line.split(' '):
            part = component.split(':')
            person.set_value(part[0], part[1])

print('valid passports  : {0}'.format(valid_passport_count))
print('invalid passports: {0}'.format(invalid_passport_count))
print('----------------------')
print('total            : {0}'.format(total_read))
print('script complete!!')
