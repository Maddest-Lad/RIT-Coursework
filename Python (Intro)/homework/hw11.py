"""
Sam Harris
Homework 11
"""

from linked_list_type import LinkedList
from node_types import FrozenNode, MutableNode
from typing import Union


def node_at(list: LinkedList, index: int) -> MutableNode:
    """
    Gets a node at index of a linkedlist

    :param list: the linked list
    :param index: the index of the node to return
    :return: node at index of list
    """

    current = list.head

    for _ in range(0, index):
        current = current.next

    return current


def immutable_to_mutable(head: FrozenNode) -> MutableNode:
    new_head = MutableNode(head.value, next=None)
    current = new_head

    for _ in range(size_of(head)-1):
        current.next = MutableNode(head.next.value, next=None)
        current, head = current.next, head.next

    return new_head


def mutable_to_immutable(head: MutableNode) -> FrozenNode:
    new_head = FrozenNode(head.value, next=None)
    current = new_head

    for _ in range(size_of(head)-1):
        current.next = FrozenNode(head.next.value, next=None)
        current, head = current.next, head.next

    return new_head


# Must Be Recursive Cause CS Department is Sadistic
def size_of(head: (FrozenNode or MutableNode)) -> int:
    """
    Returns the size of a linked list given the root node

    :param head: head node of a linked list
    :return: length of the list
    """

    if head is not None:
        return 1 + size_of(head.next)

    return 0


# Must Be Recursive Cause CS Department is Sadistic
def extend(head_a: FrozenNode, head_b: FrozenNode, previous=None, flag=False) -> FrozenNode:
    """
    Concatenates two lists together

    :param first: toggle bool for stupid recursion
    :param head_a: Head node of first list
    :param head_b: Head node of second list
    :return: Frozen node representing the head node of the concatenation
    """

    # head_a = immutable_to_mutable(head_a)
    # head_b = immutable_to_mutable(head_b)
    #
    # head_a.next = head_b

    return None


# Recursion Isn't Allowed, Not Sure Why I'd Want to Use It Here To Begin With
def reverse(list: LinkedList) -> None:
    """
    Reverses the LinkedList that head references

    :param list: list to be reversed
    """

    previous = None
    current = list.head

    while current:
        next = current.next
        current.next = previous
        previous = current
        current = next

    list.head = previous

    return


def remove_index(list: LinkedList, index: int) -> None:
    """
    Removes the node at index

    :param list: LinkedList being passed in
    :param index: index of node to remove
    :return:
    """

    # Edge Cases
    if index < 0 or index >= list.size:
        raise IndexError("Index Out Of Bounds")

    # First Node
    if index == 0:
        list.head = list.head.next
        list.size -= 1

    # Last Node
    elif index == list.size:
        node_at(list, index - 1).next = None
        list.size -= 1

    # Normal Nodes
    else:
        previous = node_at(list, index - 1)
        next = node_at(list, index + 1)
        previous.next = next
        list.size -= 1

    return None


if __name__ == '__main__':
    l = LinkedList(MutableNode('A', MutableNode('B', MutableNode('C', MutableNode('D', MutableNode('E', None))))), 5)

    for i in range(l.size - 1, 0, -1):
        print(node_at(l, i))
