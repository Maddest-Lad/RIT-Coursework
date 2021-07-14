"""
Data Types for Derp the Interpreter

The Derp interpreter parses and evaluates prefix integer expressions
containing basic arithmetic operators (*,//,-,+). It performs arithmetic with
integer operands that are either literals or variables (read from a
symbol table).  It dumps the symbol table, prints the infix expression with
parentheses to denote order of operation, and evaluates the expression to
print the result.

Author: CS@RIT.EDU
Editor: Sam Harris
"""


class LiteralNode:

    def __init__(self, val: int):
        self.val = val


class VariableNode:

    def __init__(self, val: str):
        self.val = val


class MathNode:
    """
        Represents a mathematical operation
        left:  reference to a LiteralNode, a VariableNode, or another MathNode
        operator: string in the set { '+', '-', '*', '//' }
        right: reference to a LiteralNode, a VariableNode, or another MathNode
    """

    def __init__(self, left, operator: str, right):
        self.left = left
        self.operator = operator
        self.right = right
