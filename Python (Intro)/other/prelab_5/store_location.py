"""
Sam Harris
Post PSS
"""

from quick_sort import quick_sort
from tools import sum_dist, get_median, read_file

if __name__ == '__main__':
    # Open File & Extract List
    filename = input("Enter A Filename: ")
    lst = read_file(filename)

    # Sort List
    quick_sort(lst)

    # Get Median And Sum of Distances
    median = get_median(lst, len(lst) // 2)
    sum_of_dists = sum_dist(median, lst)

    # Print Out Info
    print(f"Optimum new store location: {median}")
    print(f"Sum of distances to the new store: {sum_of_dists}")
