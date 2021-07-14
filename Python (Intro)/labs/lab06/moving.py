"""
Lab 06
Sam Harris
seh8290
"""
from dataclasses import dataclass
from random import randint

from typing import List


@dataclass
class Item:
    """
    Item Class, Contains: Name & Weight
    """

    def __init__(self, name: str, weight: int):
        self.name = name
        self.weight = weight

    def __repr__(self):
        return f"{self.name} of weight {self.weight}"


@dataclass
class Box:
    """
    Box Class

    :param name: The Name/ID of The Box - Randomly Generated
    :param size: How Much Room It Has
    :param remaining_capacity: How Much Room It Has Left
    :param items: A List of Items Currently In It
    """

    def __init__(self, size: int):
        self.name = randint(0, 256)
        self.size = size
        self.remaining_capacity = size
        self.items = []

    def has_room(self, item: Item) -> bool:
        return self.remaining_capacity >= item.weight

    def add_item(self, item: Item) -> None:
        self.items.append(item)
        self.remaining_capacity -= item.weight

    def __repr__(self):
        return f"Box ID: {self.name} of of weight capacity {self.size} contains {[i.__repr__() for i in self.items]}"


def roomiest(boxes: List[Box], items: List[Item]) -> (List[Box], List[Item]):
    """
    Sort all items by decreasing weight. Iterate through the items one by one, from largest weight to smallest.
    For each item, identify the box with the greatest remaining allowed weight that can support the item,
    and place the item in that box.

    :param items: The List of Items
    :param boxes: The List of Empty Boxes
    :return: The List of Filled Boxes
    """

    # Items By Decreasing Weight
    items.sort(key=lambda x: x.weight, reverse=True)

    # Greedy Algo #1
    for _ in range(len(boxes)):
        for item in items:
            for box in boxes:
                boxes.sort(key=lambda x: x.remaining_capacity, reverse=True)
                if box.has_room(item):
                    box.add_item(item)
                    items.remove(item)
                    break

    return boxes, items


def tightest_fit(boxes: List[Box], items: List[Item]) -> (List[Box], List[Item]):
    """
    Sort all items by decreasing weight. Iterate through the items one by one, from largest weight to smallest.
    For each item, identify the box with the least remaining allowed weight that can support the item,
    and place the item in that box.

    :param items: The List of Items
    :param boxes: The List of Empty Boxes
    :return: The List of Filled Boxes
    """

    # Items By Decreasing Weight
    items.sort(key=lambda i: i.weight, reverse=True)

    # Greedy Algo #2
    for _ in range(len(boxes)):
        for item in items:
            boxes.sort(key=lambda x: x.remaining_capacity)
            for box in boxes:
                if box.has_room(item):
                    box.add_item(item)
                    items.remove(item)
                    break

    return boxes, items


def one_at_a_time(boxes: List[Box], items: List[Item]) -> (List[Box], List[Item]):
    """
    Sort all items by decreasing weight.
    Fill the boxes one by one. For each box, iterate through all remaining items (not yet placed in a previously
    considered box) one by one. If there is room for an item to be placed in the current box, do so.

    :param items: The List of Items
    :param boxes: The List of Empty Boxes
    :return: The List of Filled Boxes
    """

    # Items By Decreasing Weight
    items.sort(key=lambda i: i.weight, reverse=True)

    # Greedy Algo #3
    for _ in range(len(boxes)):
        for box in boxes:
            for item in items:
                if box.has_room(item):
                    box.add_item(item)
                    items.remove(item)

    return boxes, items


def read_file(file_name: str):
    """
    Reads First Line of File To Create N Many Boxes, Then Creates a List of Items

    :param file_name:
    :return: Boxes & Items
    """

    with open(file_name, 'r') as file:
        lines = file.read().split("\n")

        boxes = [Box(int(i)) for i in lines.pop(0).split(" ")]
        items = []

        for line in lines:
            name, weight = line.split(" ")
            items.append(Item(name, int(weight)))

        file.close()

    return boxes, items


def print_result(boxes: List[Box], items: List[Item]) -> None:
    """
    Prints Out The Readouts For A Given Test

    :param boxes: List of [now] Filled Boxes
    :param items: List of Overflow Items
    :return:
    """
    print("All items successfully packed into boxes!") if len(items) > 0 else print("Unable to pack all items!")
    for box in boxes:
        print(box.__repr__())

    print("\n")


if __name__ == '__main__':
    boxes, items = read_file(input("enter a file: "))

    # TIL Calling list() on The Arguments Clones Them, Neat
    print("Results from Greedy Strategy 1")
    print_result(*roomiest(list(boxes), list(items)))

    print("Results from Greedy Strategy 2")
    print_result(*tightest_fit(list(boxes), list(items)))

    print("Results from Greedy Strategy 3")
    print_result(*one_at_a_time(list(boxes), list(items)))
