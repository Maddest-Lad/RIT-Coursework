"""
Sam Harris
Lab 07
Didn't Realize cs_stack was a thing, would've been nice lol
"""
from dataclasses import dataclass
from random import randint

from cs_queue import Queue, make_empty_queue, enqueue, dequeue, front


@dataclass
class Deck:
    """
    Basically A Wrapper For A Queue From CS_Queue, Which Itself is More of a Linked List Than Anything Else
    """

    def __init__(self, size: int, empty: bool = False):
        cards: Queue = make_empty_queue()

        # Fill Queue From [1, Size]
        if not empty:
            [enqueue(cards, i) for i in range(1, size)]

        # Set Class Var
        self.cards: Queue = cards

    def is_empty(self):
        """
        Wrapper for CS Queue Is Empty, I really wish the CS queue could just be a class with normal methods instead
        of having a bunch of static methods in the file. Bad practice
        """

        return self.cards.size == 0

    def peek(self):
        """
        Wrapper For CS Queue Peak
        """

        return front(self.cards) if self.cards.size > 0 else -1


def try_ascending_place(deck: Deck, card) -> bool:
    """
    Tries to place the current card on the victory pile

    :param deck: victory_pile deck
    :param card: the card
    :return: whether or not it succeeded
    """

    # Edge Case, Victory is Empty
    if deck.is_empty() and card == 1:
        enqueue(deck.cards, card)
        return True

    if not deck.is_empty() and deck.peek() + 1 == card:
        enqueue(deck.cards, card)
        return True

    return False


def try_move_discard_to_victory(victory: Deck, discard_a: Deck, discard_b: Deck) -> bool:
    """
    Tries to move cards from either of the discard piles to the victory pile

    :param victory: Victory Pile Deck
    :param discard_a: Discard Pile Deck
    :param discard_b:  Discard Pile Deck
    :return:
    """

    if not discard_a.is_empty() and try_ascending_place(victory, discard_a):
        print(f"Moved {victory.peek()} From Discard Pile A To The Victory Pile")
        return True

    if not discard_b.is_empty() and try_ascending_place(victory, discard_b):
        print(f"Moved {victory.peek()} From Discard Pile B To The Victory Pile")
        return True

    return False


def optimal_discard_placement(discard_a: Deck, discard_b: Deck, card):
    """
    Places a card onto a discard pile in ascending order where possible
    """

    if try_ascending_place(discard_a, card):
        return

    if try_ascending_place(discard_b, card):
        return

    # Randomly Place Otherwise
    enqueue(discard_a.cards, card) if randint(0, 1) == 0 else enqueue(discard_b.cards, card)


def random_draw(queue: Queue) -> int:
    """
    Draws a random card from the deck
    """

    # Arbitrary Number of Runs
    for _ in range(randint(0, queue.size)):
        enqueue(queue, dequeue(queue))

    return dequeue(queue)


def play_game(deck: Deck):
    """
    Play game according to my strategy, which tries to maintain ascending cards in the discard piles
    so that I can chain place them onto the victory pile
    """

    discard_a, discard_b, victory = Deck(0, empty=True), Deck(0, empty=True), Deck(0, empty=True)

    while not deck.is_empty():
        current = random_draw(deck.cards)

        for _ in range(7):
            try_ascending_place(victory, current)
            try_move_discard_to_victory(victory, discard_a, discard_b)

        optimal_discard_placement(discard_a, discard_b, current)

    return victory.cards.size


if __name__ == '__main__':
    size = int(input("How Many Cards Do You Want To Use: "))
    runs = int(input("How Many Games Do You Want To Play: "))

    games = [play_game(Deck(size)) for _ in range(runs)]

    print(f"Worst Game Was {min(games)}\n"
          f"Best Game Was {max(games)}\n"
          f"Average Game Was {float(sum(games)) / len(games)}")
