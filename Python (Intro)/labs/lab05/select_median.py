"""
Sam Harris
Lab 05
"""

from time import perf_counter

from tools import sum_dist, read_file


def quick_select_version(file_name: str) -> (float, float, float):
    """
    Use Quick Select Algorithm To Find Median Of The List in filename

    :param file_name: Filename of Test Data
    :return: Average Runtime
    """

    # Sort List
    lst = read_file(file_name)

    # Start Timer
    start = perf_counter()

    if len(lst) % 2 == 0:
        median = (quick_select(lst, 0) + quick_select(lst, 1)) / 2
    else:
        median = quick_select(lst, 0)

    # Sum Distance
    sum_of_dists = sum_dist(median, lst)

    # End Timing
    total_time = (perf_counter() - start)

    return total_time, median, sum_of_dists


def quick_select(lst: list, k):
    """
    Uses The Quick Select Algorithm To Find The Kth Smallest Value``

    :param lst: The List
    :param k: kth Smallest Value To Find
    :return:
    """

    if lst and k <= len(lst):
        pivot = lst[len(lst) // 2]

        smaller_list, larger_list = [], []
        count = 0

        for i in lst:
            smaller_list.append(i) if pivot < i else larger_list.append(i)
            if pivot == i:
                count += 1

        m = len(smaller_list)

        if k <= m and k < m + count:
            return pivot

        if m > k:
            return quick_select(smaller_list, k)

        else:
            return quick_select(larger_list, k - m - count)


if __name__ == '__main__':

    # Open File & Extract List
    filename = input("Enter A Filename: ")

    # Run Tests
    try:

        runtime, median, sum_dist = quick_select_version(filename)

        print(f"Optimum new store location: {median}")
        print(f"Sum of distances to the new store: {sum_dist}")
        print(f"Quick Select Took {runtime} ms")

    except (FileNotFoundError, FileExistsError):
        print("File Could Not Be Found Or Opened")
