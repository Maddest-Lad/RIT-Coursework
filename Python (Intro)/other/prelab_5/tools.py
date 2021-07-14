"""
Sam Harris
PSS
"""

from re import sub


# ALL ONE LINERS !!!!!!!!!
# Very Sketchy But They Work - Don't Look at read_file too closely if you value your sanity


def sum_dist(best, store_locations): return sum([abs(loc - best) for loc in store_locations])
"""
Returns the sum of differences between best and each item in store_locations
best: Optimal Store Location
store_locations: List of Other Stores Locations
"""


def read_file(f_name): return [int(j) for j in [sub("[^0-9]", "", i) for i in open(f_name).read().split('\n')][:-1]]
"""
Reads numbers from the end of each line [of a file] into a list
f_name: (path to) + file name  
"""


def get_median(lst: list, mid): return (lst[mid + 1] + lst[mid]) / 2 if len(lst) % 2 == 0 else lst[mid]
"""
Gets the median of a sorted list

lst: list to get median from
mid: len(lst)/2, made it named parameter so this function could be a one-liner
"""

