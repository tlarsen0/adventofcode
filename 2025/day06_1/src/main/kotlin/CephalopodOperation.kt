package org.tlarsen.adventofcode2025

interface CephalopodCalculation {
    fun calculate(leftVal: Long, rightVal: Long): Long
}

enum class CephalopodOperation : CephalopodCalculation {
    ADDITION {
        override fun calculate(leftVal: Long, rightVal: Long): Long {
            return leftVal + rightVal
        }

        override fun toString(): String {
            return "+"
        }
    },
    MULTIPLICATION {
        override fun calculate(leftVal: Long, rightVal: Long): Long {
            return leftVal * rightVal
        }

        override fun toString(): String {
            return "*"
        }
    },
    NONE {
        override fun calculate(leftVal: Long, rightVal: Long): Long {
            return 0L
        }

        override fun toString(): String {
            return "(none)"
        }
    }
}