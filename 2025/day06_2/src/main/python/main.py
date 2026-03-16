#!/usr/bin/env python3
"""AOC 2025, Day 6, Part 2 - Cephalopod Math"""

from enum import Enum
from dataclasses import dataclass
from typing import Dict, List


class CephalopodOperation(Enum):
    ADDITION = "+"
    MULTIPLICATION = "*"
    NONE = "(none)"

    def calculate(self, left_val: int, right_val: int) -> int:
        if self == CephalopodOperation.ADDITION:
            return left_val + right_val
        elif self == CephalopodOperation.MULTIPLICATION:
            return left_val * right_val
        else:
            return 0


@dataclass
class Coord:
    x: int
    y: int


class CephalopodFormula:
    def __init__(self):
        self.params: List[int] = []
        self.operation = CephalopodOperation.NONE

    def add_param(self, new_param: int):
        self.params.append(new_param)

    def __str__(self) -> str:
        op_str = self.operation.value if self.operation != CephalopodOperation.NONE else "(none)"
        return op_str.join(str(p) for p in self.params)

    def do_homework(self) -> int:
        if not self.params:
            return 0
        result = self.params[0]
        for param in self.params[1:]:
            result = self.operation.calculate(result, param)
        return result


class CephalopodParser:
    def __init__(self, span_start: int, span_end: int, all_characters: Dict[Coord, str], max_y: int):
        self.span_start = span_start
        self.span_end = span_end
        self.all_characters = all_characters
        self.max_y = max_y

    def find_formula_in_span(self) -> CephalopodFormula:
        new_formula = CephalopodFormula()
        for x in range(self.span_start, self.span_end - 1):
            new_formula.add_param(self._get_cephalopod_number_at(x, self.max_y))
            if new_formula.operation == CephalopodOperation.NONE:
                new_formula.operation = self._get_cephalopod_operation(x, self.max_y)
        return new_formula

    def _get_cephalopod_number_at(self, x: int, max_y: int) -> int:
        result = 0
        for y in range(max_y + 1):
            the_char = self.all_characters.get(Coord(x, y))
            if the_char is not None and the_char.isdigit():
                result = result * 10 + int(the_char)
        return result

    def _get_cephalopod_operation(self, x: int, max_y: int) -> CephalopodOperation:
        for y in range(max_y + 1):
            the_char = self.all_characters.get(Coord(x, y))
            if the_char == '+':
                return CephalopodOperation.ADDITION
            elif the_char == '*':
                return CephalopodOperation.MULTIPLICATION
        return CephalopodOperation.NONE


def main(argv: List[str]):
    print("AOC 2025, Day 6, Part 2 starting!!!!")
    print("Details of problem are here: https://adventofcode.com/2025/day/6#part2")

    all_formulas: List[CephalopodFormula] = []
    all_characters: Dict[Coord, str] = {}

    with open(argv[0], 'r') as f:
        lines = f.readlines()

    max_x = 0
    y = 0
    for line in lines:
        line = line.rstrip('\n')
        max_x = max(max_x, len(line))
        x = 0
        for the_char in line:
            new_coord = Coord(x, y)
            all_characters[new_coord] = the_char
            x += 1
        y += 1

    formula_count = sum(1 for char in all_characters.values() if char == '*' or char == '+')
    print(f"Number of formulas: {formula_count}")

    formula_spans: List[int] = []
    max_y = y - 1
    print(f"Scanning row {max_y}, columns 0 to {max_x} to determine blocks to scan:")

    span_start = 0
    for scan_x in range(1, max_x + 1):
        coord = Coord(scan_x, max_y)
        the_char = all_characters.get(coord)
        if the_char is None:
            break
        if the_char == ' ' or span_start == scan_x:
            continue
        else:
            formula_spans.append(scan_x)
            span_start = scan_x

    formula_spans.append(max_x + 1)
    span_start = 0
    for span_end in formula_spans:
        cephalopod_parser = CephalopodParser(span_start, span_end, all_characters, max_y)
        all_formulas.append(cephalopod_parser.find_formula_in_span())
        span_start = span_end

    the_total = 0
    for formula in all_formulas:
        answer = formula.do_homework()
        formula_str = str(formula).ljust(30)
        print(f"Formula: {formula_str} = {answer}")
        the_total += answer

    # Correct answer: 10188206723429
    print(f"The total calculated using Cephalopod Math: {the_total}")

    print("AOC 2025, Day 6, Part 2 completed!!!")


if __name__ == "__main__":
    import sys
    main(sys.argv[1:])