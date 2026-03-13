from enum import Enum


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