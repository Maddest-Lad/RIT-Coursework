"""
Sam Harris
Post PSS / Lab 05
"""

from time import perf_counter

from quick_sort import quick_sort
from tools import sum_dist, get_median, read_file


def quicksort_version(file_name: str) -> (float, float, float):
    """
    Quicksort Version of Find Median

    :param file_name: Filename of Test Data
    :return: Average Runtime
    """

    # Read File
    lst = read_file(file_name)

    # Start Timer
    start = perf_counter()

    # Sort The List
    quick_sort(lst)

    # Get Median And Sum of Distances
    median = get_median(lst, len(lst) // 2)
    sum_of_dists = sum_dist(median, lst)

    # End Timing
    total_time = (perf_counter() - start)

    return total_time, median, sum_of_dists


if __name__ == '__main__':

    # Open File & Extract List
    filename = input("Enter A Filename: ")

    # Run Tests
    try:

        runtime, median, sum_dist = quicksort_version(filename)

        print(f"Optimum new store location: {median}")
        print(f"Sum of distances to the new store: {sum_dist}")
        print(f"Store Location Took {runtime} ms")

    except (FileNotFoundError, FileExistsError):
        print("File Could Not Be Found Or Opened")
