"""
I Got Tired of The RIT CS Wrapper Class for Linked Lists, So I Wrote My Own
Sam Harris
Post PSS / Lab 8
"""


class Node:

    def __init__(self, name: str, data):
        self.name = name
        self.data = data
        self.next = None

    def __str__(self):
        return f"{self.name}({self.data})"


class BetterLinkedList():

    def __init__(self):
        self.head = None
        self.size = 0

    def add_end(self, name: str, data):
        new_node = Node(name, data)

        if self.head is None:
            self.head = new_node
            self.size += 1
            return

        node = self.head

        while node.next is not None:
            node = node.next

        node.next = new_node
        self.size += 1

    def pop(self) -> Node:
        node = self.head
        self.head = self.head.next
        self.size -= 1

        return node

    def __getitem__(self, index):
        if self.head is None:
            return

        node = self.head

        for _ in range(index):
            node = node.next

        return node
