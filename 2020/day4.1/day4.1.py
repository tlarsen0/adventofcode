#!/usr/bin/python

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

    def check_valid(self):
        # exact rule: if all fields except cid were set then return True
        if self.byr and self.iyr and self.eyr and self.hgt and self.hcl and self.ecl and self.pid and self.cid == '':
            return True
        # general rule: all fields must be set otherwise invalid and False
        if self.byr == '' or self.iyr == '' or self.eyr == '' or self.hgt == '' or self.hcl == '' or self.ecl == '' or self.pid == '' or self.cid == '':
            return False

        return True


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
