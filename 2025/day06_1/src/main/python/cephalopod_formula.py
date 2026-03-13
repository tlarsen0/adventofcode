from cephalopod_operation import CephalopodOperation


class CephalopodFormula:
    def __init__(self):
        self.params = []
        self.operation = CephalopodOperation.NONE

    def add_param(self, new_param: int):
        self.params.append(new_param)

    def __str__(self) -> str:
        return self.operation.value.join(str(p) for p in self.params)

    def do_homework(self) -> int:
        if not self.params:
            return 0

        result = self.params[0]
        for p in self.params[1:]:
            result = self.operation.calculate(result, p)
        return result