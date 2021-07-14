"""
Sam Harris
Homework 06
"""


def ack(m: int, n: int) -> int:
    """
    Implements the Wilhelm Ackerman function

    :param m: of Ackerman Func
    :param n: of Ackerman Func
    :return: The Ackerman Value For m & n
    """

    # One Liner !!!!
    return n + 1 if m == 0 else ack(m - 1, 1) if m > 0 and n == 0 else ack(m - 1, ack(m, n - 1))

    # Normal Way For Reference
    # if m == 0:
    #     return n + 1
    # if m > 0 and n == 0:
    #     return ack(m - 1, 1)
    # if m > 0 and n > 0:
    #     return ack(m - 1, ack(m, n - 1))


def main():
    """
    Main Method
    """

    # Get User Input
    m, n = int(input("Enter m: ")), int(input("Enter n: "))
    print(f"ack({m},{n}) =", ack(m, n))


if __name__ == "__main__":
    main()
