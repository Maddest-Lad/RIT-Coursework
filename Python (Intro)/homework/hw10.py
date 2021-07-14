"""
Sam Harris
HW 10
"""

from cs_queue import Queue, enqueue, dequeue, make_empty_queue


class Droid:
    """
    TASK 1

    A Droid Class
    """

    def __init__(self, id: int):
        self.id = id
        self.arms = False
        self.legs = False
        self.head = False
        self.body = False

    def try_add_part(self, part_name) -> bool:
        if part_name == "arms" and not self.arms:
            self.arms = True
            return True

        if part_name == "legs" and not self.legs:
            self.legs = True
            return True

        if part_name == "head" and not self.head:
            self.head = True
            return True

        if part_name == "body" and not self.body:
            self.body = True
            return True

        return False

    def is_complete(self):
        return self.arms and self.legs and self.head and self.body


def read_file(filename: str) -> Queue:
    """
    TASK 2
    Reads filename into the ~CS Departments Special Queue~

    :param filename: File Being Read
    :return: Queue Consisting of File's Lines
    """

    parts = make_empty_queue()

    with open(filename, 'r') as file:
        for line in file.read().split("\n"):
            enqueue(parts, line)

    return parts


def assemble_droid(parts: Queue, serial_number: int):
    """
    TASK 3
    Assembles One Droid

    :param parts: Queue of parts
    :param serial_number: Droid Serial Number
    """
    droid = Droid(serial_number)

    print(f"Building a new droid with serial number {serial_number}")

    while not droid.is_complete():
        current = dequeue(parts)

        if droid.try_add_part(current):
            print(f"\t attaching {current}...")

        else:
            enqueue(parts, current)
            print(f"\t placing unneeded part back on belt: {current}")

    print(f"Droid {serial_number} has been assembled!")


def droid_factory(parts: Queue):
    print("Starting a shift at the droid factory!")

    serial_number = 10000

    while parts.size > 0:
        assemble_droid(parts, serial_number)
        serial_number += 1

    print("All of the droids have been assembled! Time to clock out and play Sabacc...")


if __name__ == '__main__':
    # Read The Parts File Into A Queue
    filename = input("Enter parts filename: ")
    parts: Queue = read_file(filename)

    # Assemble The Droids
    droid_factory(parts)
