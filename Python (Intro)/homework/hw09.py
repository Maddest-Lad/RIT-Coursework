"""
Sam Harris
HW 09
"""

from collections import defaultdict


def lexical_order(word: str): return "".join(sorted(word))


def add_word_to_dict(word_dict: defaultdict, word: str) -> None:
    word_dict.setdefault(lexical_order(word), []).append(word)


if __name__ == '__main__':
    word_dict = defaultdict(list)

    # Add All The Words To The Dictionary (Step 1)
    with open("american-english.txt", encoding="utf-8") as file:
        for line in file.read().split("\n"):
            add_word_to_dict(word_dict, line)

    while True:
        # Get User Input (Step 2)
        x = input("Enter input string (hit enter key to go to task 3): ")

        if x == "": break

        if lexical_order(x) in word_dict:
            print(f"Corresponding words: {word_dict[lexical_order(x)]}")
        else:
            print(f"No words can be formed from: {x}")

    print("\n")

    while True:
        # Get User Input (Step 3)
        x = input("Enter word length (hit enter key to go to task 4): ")

        if x == "": break

        x = int(x)

        values = list(reversed(sorted([i for i in word_dict.values() if len(i[0]) == x], key=len)))

        print(f"Max anagrams for length {x}: {len(values[0])}")
        print(f"Anagram list: {values[0]}")

    print("\n")

    while True:
        # Get User Input (Step 4)
        x = int(input("Enter word length (hit enter key to quit): "))

        if x == "": break

        values = sorted([i for i in word_dict.values() if len(i[0]) == x and len(i) == 1], key=len)

        print(f"Number of jumble usable words of length {x}: {len(values)}")
