"""
Sam Harris
Homework 08
"""

from merge_sort import split, merge
from quick_sort import partition


def merge_quick_sort(_list: list) -> list:
    """
    Combination of MergeSort & Quick Sort Per The Homework's Specifications

    :param _list: Unsorted List
    :return: Sorted List
    """

    # Edge Case: Empty List
    if not _list:
        return []

    # Base Case: 1 Element List
    if len(_list) == 1:
        return _list

    sorted_sublists = []

    for i in split(_list):
        small, equal, large = partition(i[0], i)
        sorted_sublists.append(merge_quick_sort(small) + equal + merge_quick_sort(large))

    # (d) Merge the two sorted halves.
    return merge(*sorted_sublists)
