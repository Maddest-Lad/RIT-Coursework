"""
CSCI-141: Testing & Debugging
Homework 6
Author: RIT CS, Modified By Sam Harris

A palindrome checker that had a logic error. (Fixed By Sam Harris)
"""


def is_palindrome(word):
    """
    A boolean function that tests whether a word is a palindrome or not. (recursion is bad in production)
    leading/trailing spaces and capitalization are ignored

    :param word: the word
    :return: whether it is a palindrome or not
    """

    word = word.strip().lower()
    return word == word[::-1]


def main():
    """
    The main prompts for a word and checks whether it is a palindrome or not.
    """
    word = input('Enter word: ')
    if is_palindrome(word):
        print(word, 'is a palindrome')
    else:
        print(word, 'is NOT a palindrome')


if __name__ == '__main__':
    main()
