import sys
from cephalopod_formula import CephalopodFormula
from cephalopod_operation import CephalopodOperation


def main():
    print("AOC 2025, Day 6, Part 1 starting!!!!")
    print("Details of problem are here: https://adventofcode.com/2025/day/6#part1")

    all_formulas = []
    first_line = True

    with open(sys.argv[1]) as f:
        for line in f:
            the_split = line.split()
            split_counter = 0
            for split in the_split:
                if not split:
                    continue

                if first_line:
                    new_formula = CephalopodFormula()
                    new_formula.add_param(int(split))
                    all_formulas.append(new_formula)
                elif split == "+" or split == "*":
                    formula = all_formulas[split_counter]
                    if split == "+":
                        formula.operation = CephalopodOperation.ADDITION
                    elif split == "*":
                        formula.operation = CephalopodOperation.MULTIPLICATION
                else:
                    formula = all_formulas[split_counter]
                    formula.add_param(int(split))

                split_counter += 1

            first_line = False

    total = 0
    for formula in all_formulas:
        answer = formula.do_homework()
        print(f"Formula: {str(formula).ljust(30)} = {answer}")
        total += answer

    print(f"The total calculated using Cephalopod Math: {total}")

    print("AOC 2025, Day 6, Part 1 completed!!!")


if __name__ == "__main__":
    main()