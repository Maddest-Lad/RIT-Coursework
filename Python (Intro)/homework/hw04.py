"""
Sam Harris
Homework 04
9/11/2020

Recursively and Iteratively Find The GCD + ~Psuedo~ Unit Tests
"""


def gcd_rec(a: int, b: int) -> int:  # !! ONE LINER !! Returns The GCD of A and B. Precondition -> Both Ints
    return a if not b else gcd_rec(b, a % b)


def gcd_rec_test() -> None:
    """
    Tester Function For gcd_rec(a, b)
    """

    # (a, b, gcd)
    test_values = [(64, 8, 8), (8, 12, 4), (-2048, 2048, 2048), (19, 7, 1), (27, 3, 3), (14, 182, 14)]

    for i, j, k in test_values:

        try:
            assert gcd_rec(i, j) == k

        except (RecursionError, AssertionError):
            print("Test Failed, GCD({i}, {j}) Did Not Equal {k}".format(i=i, j=j, k=k))


def gcd_iter(a: int, b: int) -> int:
    while b > 0:
        temp = a % b
        a = b
        b = temp

    return a


def gcd_iter_test() -> None:
    """
    Tester Function For gcd_iter(a, b)
    """

    # (a, b, gcd)
    test_values = [(64, 8, 8), (8, 12, 4), (-2048, 2048, 2048), (19, 7, 1), (-27, 3, 3), (14, 182, 14)]

    for i, j, k in test_values:

        try:
            assert gcd_iter(i, j) == k

        except (RecursionError, AssertionError):
            print("Test Failed, GCD({i}, {j}) Did Not Equal {k}".format(i=i, j=j, k=k))


if __name__ == "__main__":
    # ~Pseudo~ Unit Tests
    gcd_rec_test()
    gcd_iter_test()

    # Human Version
    function = int(input("Select a GCD Function \n 1: Iterative \n 2: Recursive \n"))

    a = int(input("Enter Value A: "))
    b = int(input("Enter Value B: "))

    # I Forgot How Much Fun Ternary Statements Were In Python
    print("GCD of {a} & {b} is {c}".format(a=a, b=b, c=gcd_rec(a, b) if function else gcd_iter(a, b)))

