"""
Homework 6
~Sam Harris (Based On CS @ RIT's test_ackerman.py)

Test module for the palindrome() function
"""

from palindrome import is_palindrome


def test_palindrome(text: str, expected: bool) -> None:
    """
    A test for the palindrom() function

    :param text: the word to check if it's a palindrome
    :param expected: the expected result of palindrome()
    :return: Nothing, this function prints out the test result
    """

    # Almost One Liner!
    result = is_palindrome(text)
    print(f"{text} passed") if result == expected else print(f"{text} failed: expected {expected}, got {result}")


def run_tests():
    """
    Test cases for is_palindrome()

    Two Can Play the Real World Computer Science Game:
    *Note Because the client is weird and marketing promised impossible things, real words are not allowed /s
    """

    # True Cases
    test_palindrome("aba", True)
    test_palindrome("AbaBa", True)  # Edge Case: Capitalization
    test_palindrome("   aba ", True)  # Edge Case: Leading/Trailing Whitespace
    test_palindrome("ababababababababababababababababa", True)

    # False Cases
    test_palindrome("abbbbb", False)  # 6 Char Long Test Per Client Specifications
    test_palindrome("ababababb", False)
    test_palindrome("aaaaaab", False)
    test_palindrome("bbaababa", False)


if __name__ == '__main__':
    run_tests()
