"""
Author: Sam Harris
Date: 8/28/2020

Description:
a program that A, implements a divisibility and tester functions
and B, determines whether 3 integers are a valid triangle
"""


def divisible(a: int, b: int) -> bool or None:
    """
    Function For Determining Whether The Max of A, B Is Divisible By The Min of A, b

    :param a: First Integer For Comparisons
    :param b: Second Integer For Comparisons

    :return: None: When Numbers Are Not Positive
    :return: Bool: Returns True When Numbers Are Equivalent or Evenly Divisible, False Otherwise

    """
    if a < 1 and b < 1:
        return None

    if a is b:
        return True

    return max(a, b) % min(a, b) == 0  # Quite Proud of This One


def run_divisible():
    """
    Function Which Tests divisible() By Requesting 2 Integers From The User and Nicely Parsing the Results For Printing

    :return: None
    """

    print("Getting input for divisible:")

    # Get User Input, Should be of Type() Int
    a = int(input("enter an integer: "))
    b = int(input("enter another integer: "))

    # Get Results and Assign Max/Min For Ease of Printing
    result = divisible(a, b)
    maximum = max(a, b)
    minimum = min(a, b)

    if result is None:
        print("Inputs must be positive integers!")  # Precondition Not Met

    elif a is b:
        print("{0} equals {1}!".format(a, b))  # Equivalent

    elif result:
        print("{0} is evenly divisible by {1}".format(maximum, minimum))  # Evenly Divisible

    else:
        print("{0} is not evenly divisible by {1}".format(maximum, minimum))  # Probably Not Divisible


def is_triangle(a: int, b: int, c: int) -> bool or None:
    """
    Function Which determines if 3 positive sides of a potential triangle form a valid geometry

    :return None: If Any of The Inputs Are Negative
    :return: Bool: If the Triangle is Valid/Invalid
    """

    if a < 0 or b < 0 or c < 0:
        return None

    # Should Cover Corner Case
    return (a + b >= c) or (b + c >= a) or (c + a >= b)


def run_is_triangle():
    # Get User Input, Should be of Type() Int
    a = int(input("Enter A: "))
    b = int(input("Enter B: "))
    c = int(input("Enter C: "))

    result = is_triangle(a, b, c)

    if result is None:
        print("Triangles require sides of positive length!")

    elif result:
        print("{0}, {1} and {2} can form a triangle".format(a, b, c))

    else:
        print("{0}, {1} and {2} can not form a triangle".format(a, b, c))


if __name__ == "__main__":
    run_divisible()
    run_is_triangle()
