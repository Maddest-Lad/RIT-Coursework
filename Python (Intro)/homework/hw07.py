"""
Sam Harris
Homework 07

1.  In what kind of test case does insertion sort perform better than selection sort?
    Clearly describe the test case.

    Insertion Sort performs better on both smaller inputs, and somewhat sorted lists,
    it additionally can sort without needing to read the full list into memory, which
    would make it better for data where it is unable for all of it to be loaded into memory

    It is worthwhile to note though, that selection sort does perform better on larger inputs


2.  Why does selection sort perform worse than insertion sort in that test case?

    Selection sort performs worse when dealing with somewhat sorted lists because it
    needs to traverses the entire list before swapping, but it reduces the total number
    of swaps needed to sort the list, which is why it performs better on large inputs


    Small Input Test:
    Insertion Sort Took 2.09808e-05 Seconds For an Input Size of 10
    Selection Sort Took 2.21729e-05 Seconds For an Input Size of 10

    Large Input Test:
    Insertion Sort Took 6.3096680641 Seconds For an Input Size of 10000
    Selection Sort Took 3.5902302265 Seconds For an Input Size of 10000
"""

import time
from random import randint


def insertion_sort(collection) -> list:
    """
    sorts collection with the insertion sort algorithm

    :param collection [to be sorted]
    :return: sorted list
    """

    for index in range(len(collection) - 1):
        while index > -1 and collection[index] > collection[index + 1]:
            collection[index], collection[index + 1] = collection[index + 1], collection[index]
            index -= 1

    return collection


def selection_sort(collection) -> list:
    """
    sorts collection with the selection sort algorithm

    :param collection [to be sorted]
    :return: sorted list
    """

    for index in range(len(collection)):

        min_index = index

        for compare_index in range(index, len(collection)):

            if collection[compare_index] < collection[min_index]:
                min_index = compare_index

        collection[index], collection[min_index] = collection[min_index], collection[index]

    return collection


def efficiency_tester(collection, algorithm: bool) -> float:
    """
    Returns the mili-seconds it takes to sort collection

    :param collection: [to be measured sorting]
    :param algorithm: true for selection, false for insertion
    :return: elapsed time
    """
    start_time = time.time()

    insertion_sort(collection) if algorithm else selection_sort(collection)

    end_time = time.time()

    return round(end_time - start_time, 10)


def main():
    # Read User Input Line-By-Line Into A List
    user_list = [int(line.strip()) for line in open(input("Enter a Filename: "), 'r').read().split('\n')]

    # Print Before Sorting
    print("Original List : ", *user_list)
    print("Sorted List   :", *insertion_sort(user_list))

    # # Efficiency Comparison
    # small_list, large_list = [randint(-1000, 1000) for _ in range(10)], [randint(-1000, 1000) for _ in range(10000)]
    #
    # # Small Input Comparison
    # print("\nSmall Input Test:")
    # print(f"Insertion Sort Took {efficiency_tester(small_list, True)} Seconds For an Input Size of 10")
    # print(f"Selection Sort Took {efficiency_tester(small_list, False)} Seconds For an Input Size of 10")
    #
    # # Large Input Comparison
    # print("\nLarge Input Test:")
    # print(f"Insertion Sort Took {efficiency_tester(large_list, True)} Seconds For an Input Size of 10000")
    # print(f"Selection Sort Took {efficiency_tester(large_list, False)} Seconds For an Input Size of 10000")


if __name__ == "__main__":
    main()
