"""
Sam Harris
Homework 08
"""

from sys import setrecursionlimit, getrecursionlimit
from time import perf_counter
from random import shuffle

from merge_quick_sort import merge_quick_sort
from quick_sort import quick_sort
from merge_sort import merge_sort


def efficiency(_list: list, algorithm: int, randomize=False) -> float:
    """
    Returns the mili-seconds it takes to sort the list

    :param randomize: Whether or not to randomize the list
    :param _list: list to be timed
    :param algorithm: (0 :  quick sort, 1 : merge sort, 2 : quick merge sport)
    :return: elapsed time
    """

    if randomize:
        shuffle(_list)

    start_time = perf_counter()

    if algorithm == 0:
        quick_sort(_list)

    if algorithm == 1:
        merge_sort(_list)

    if algorithm == 2:
        merge_quick_sort(_list)

    return round(perf_counter() - start_time, 10)


def main():
    """
    Tester For Comparing Sorting Algorithms
    """

    _list = [i for i in range(int(input("How Many Elements Do You Want: ")))]

    if len(_list) > getrecursionlimit():
        setrecursionlimit(len(_list) + 10)

    # Test Output
    print(f"List Size: {len(_list)}")

    try:

        print(f"quick_sort (sorted) elapsed time: {efficiency(_list, 0)}")
        print(f"merge_sort (sorted) elapsed time: {efficiency(_list, 1)}")
        print(f"merge_quick_sort (sorted) elapsed time: {efficiency(_list, 2)}")

        print(f"quick_sort (random) elapsed time: {efficiency(_list, 0, randomize=True)}")
        print(f"merge_sort (random) elapsed time: {efficiency(_list, 1, randomize=True)}")
        print(f"merge_quick_sort (random) elapsed time: {efficiency(_list, 2, randomize=True)}")

    except RecursionError:
        print("Maximum Recursion Depth Exceeded")


if __name__ == "__main__":
    main()
