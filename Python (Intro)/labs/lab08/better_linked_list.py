"""
I Got Tired of The RIT CS Wrapper Class for Linked Lists, So I Wrote My Own
Sam Harris
Post PSS / Lab 8
"""


class Node:

    def __init__(self, name: str, data):
        """
        Creates a Node

        Has Two Seperate Use Cases, But I Wanted One Node Data Class, So I'll Explain Them Here

        Train Car Node:
        :param name: Cargo/Contents
        :param data: Destination Station

        Train Station Node:
        :param name: Station Name
        :param data: Distance From Previous Station
        """

        self.name = name
        self.data = data
        self.next = None

    def __str__(self):
        return f"{self.name}({self.data})"


class BetterLinkedList:
    _DEFAULT_CAR = Node("", "")

    def __init__(self):
        self.head = None
        self.size = 0

    def add_front(self, name: str, data):
        new_node = Node(name, data)

        # Empty List
        if self.head is None:
            self.head = new_node

        # Populated List - Shift Head Back
        else:
            temp = self.head
            self.head = new_node
            new_node.next = temp

        self.size += 1

    def add_front_node(self, node: Node):

        # Empty List
        if self.head is None:
            self.head = node

        # Populated List - Shift Head Back
        else:
            temp = self.head
            self.head = node
            node.next = temp

        self.size += 1

    def add_end(self, name: str, data):
        new_node = Node(name, data)

        # Empty List
        if self.head is None:
            self.head = new_node
            self.size += 1
            return

        # Populated List
        node = self.head

        while node.next is not None:
            node = node.next

        node.next = new_node
        self.size += 1

    # Removes First Node
    def pop(self) -> Node:
        node = self.head
        self.head = self.head.next
        self.size -= 1

        return node

    # Removes Node at Index
    def remove_at(self, index):

        # Empty List
        if index < 0 or index > self.size:
            return

        # First Element
        elif index == 0:
            node = self.head
            self.head = self.head.next

        # Last Element
        elif index == self.size:
            node = self.__getitem__(index)
            self.__getitem__(index - 1).next = None
            return node

        # Everything In-Between
        else:
            node = self.__getitem__(index)
            self.__getitem__(index - 1).next = self.__getitem__(index + 1)

        self.size -= 1
        return node

    # Allows the Use of [] Operators
    def __getitem__(self, index):

        # Edge Cases: Empty List, Out of Bounds Index
        if self.head is None or index < 0 or index > self.size:
            return self._DEFAULT_CAR

        node = self.head

        for _ in range(index):
            node = node.next

        return node

    # Allows the Use of "in"(contains) Operator
    def __contains__(self, item):

        # Edge Case: Empty List
        if self.head is None:
            return False

        node = self.head

        for _ in range(self.size):

            if node.name == item:
                return True

            node = node.next

        return False
