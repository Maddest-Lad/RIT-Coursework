"""
141 Tree Lab - Derp the Interpreter

The Derp interpreter parses and evaluates prefix integer expressions
containing basic arithmetic operators (*,//,-,+). It performs arithmetic with
integer operands that are either literals or variables (read from a
symbol table).  It dumps the symbol table, prints the infix expression with
parentheses to denote order of operation, and evaluates the expression to
print the result.

Author: CS@RIT.EDU

Author: Sam Harris
"""

from derp_types import *  # dataclasses for the Derp interpreter

SUPPORTED_OPERATIONS = ["+", "-", "//", "*"]


def file_to_dict(filename: str) -> dict:
    """
    Reads a file which contains variables and their values, 1 per line


    :param filename: name of file to open
    :return: dictionary (var->val)
    """
    variables = {}

    with open(filename, 'r') as f:
        for i in f.readlines():
            var, value = i.split(" ")
            variables[var] = int(value)

    return variables


def gen_right_node(value: any):
    """
    Tries to generate the correct node type based on the input,
    uses short circuiting to prevent errors from unknown types

    :param value: value to be parsed into a node
    :return: the resulting node, assuming it can be made
    """

    if isinstance(value, str):

        if value in SUPPORTED_OPERATIONS:
            return MathNode(value)

        elif value.isnumeric():
            return LiteralNode(int(value))

        elif value.isalpha():
            return VariableNode(value)

    return None


##############################################################################
# parse
##############################################################################

def parse(tokens: list):
    """parse: list(String) -> Node
    From a prefix stream of tokens, construct and return the tree,
    as a collection of Nodes, that represent the expression.
    precondition: tokens is a non-empty list of strings
    """

    if len(tokens) == 1:
        return gen_right_node(tokens[0])

    current = gen_right_node(tokens.pop(0))

    if isinstance(current, MathNode):
        current.left = parse(tokens)
        current.right = parse(tokens)

    return current


##############################################################################
# infix
##############################################################################

def infix(node):
    """infix: Node -> String
    Perform an inorder traversal of the node and return a string that
    represents the infix expression.
    precondition: node is a valid derp tree node
    """

    if node is None:
        return ''
    else:
        if isinstance(node, MathNode):
            return "(" + infix(node.left) + str(node.operator) + infix(node.right) + ")"
        else:
            return str(node.val)


##############################################################################
# evaluate
##############################################################################

def evaluate(node, vars):
    """evaluate: Node * dict(key=String, value=int) -> int
    Return the result of evaluating the expression represented by node.
    Precondition: all variable names must exist in sym_tbl
    precondition: node is a valid derp tree node
    """

    if node is None:
        return 0

    else:
        if isinstance(node, MathNode):
            if node.operator == "*":
                return evaluate(node.left, vars) * evaluate(node.right, vars)

            if node.operator == "//":
                return evaluate(node.left, vars) // evaluate(node.right, vars)

            if node.operator == "+":
                return evaluate(node.left, vars) + evaluate(node.right, vars)

            if node.operator == "-":
                return evaluate(node.left, vars) - evaluate(node.right, vars)

        elif isinstance(node, LiteralNode):
            return node.val

        else:
            return vars[node.val]


##############################################################################
# main
##############################################################################


def main():
    """main: None -> None
    The main program prompts for the symbol table file, and a prefix
    expression.  It produces the infix expression, and the integer result of
    evaluating the expression"""

    print("Hello Herp, welcome to Derp v1.0 :)")
    filename = input("Herp, enter symbol table file: ")

    # STUDENT: CONSTRUCT AND DISPLAY THE SYMBOL TABLE HERE
    symbol_table = file_to_dict(filename)
    print(symbol_table)

    print("Herp, enter prefix expressions, e.g.: + 10 20 (ENTER to quit)...")

    # input loop prompts for prefix expressions and produces infix version
    # along with its evaluation
    while True:
        prefix_exp = input("derp> ")
        if prefix_exp == "":
            break

        # STUDENT: GENERATE A LIST OF TOKENS FROM THE PREFIX EXPRESSION
        tokens = prefix_exp.split(" ")

        # STUDENT: CALL parse WITH THE LIST OF TOKENS AND SAVE THE ROOT OF
        # THE PARSE TREE.
        root = parse(tokens)
        print(type(root), root)

        # STUDENT: GENERATE THE INFIX EXPRESSION BY CALLING infix AND SAVING
        # THE STRING.
        infix_string = infix(root)

        # STUDENT: MODIFY THE print STATEMENT TO INCLUDE RESULT.
        print(f"Derping the infix expression: {infix_string}")

        # STUDENT: EVALUTE THE PARSE TREE BY CALLING evaluate AND SAVING THE
        # INTEGER RESULT.
        evaluation = evaluate(root, symbol_table)

        # STUDENT: MODIFY THE print STATEMENT TO INCLUDE RESULT.
        print(f"Derping the evaluation: {evaluation}")

    print("Goodbye Herp :(")


if __name__ == "__main__":
    main()
