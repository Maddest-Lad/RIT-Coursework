"""
Sam Harris
9/14/2020
Lab 03
"""

import turtle
from math import sqrt
from random import randrange, random

# Various Global Vars
BOUNDING_BOX = (-200, 200)  # For Both The X and Y Axes
MAX_FIGURES = 500
MAX_DISTANCE = (1, 30)
MAX_SIZE = (1, 30)
MAX_ANGLE = (-30, 30)


def stay_in_bounds():  # ONE LINER !!!!
    """
    Method to pre-emptively prevent the turtle from going outside the (-200, 200) x/y Range
    Uses a Turtle "turtle.towards()" which is basically a wrapper for atan2, to point itself back towards the   origin
    when it gets close to the edge
    """

    turtle.setheading(turtle.towards(0, 0)) if abs(turtle.xcor()) > 150 or abs(turtle.ycor()) > 150 else None


def draw_triangle(side_length: int) -> float:
    """
    Draws an Equilateral Triangle With A Random Side Length, Randomly Selects A Color

    :return: A Float Representing the Area of the Triangle
    """

    # Randomize Parameters
    turtle.color(random(), random(), random())

    # Colors Are Cool
    turtle.begin_fill()

    # Draw The Triangle
    turtle.pendown()

    for i in range(3):
        turtle.forward(side_length)
        turtle.left(120)

    # Reset For Movement
    turtle.end_fill()
    turtle.penup()

    return eq_tri_area(side_length)


def draw_figures_rec(elements: int) -> float:
    """
    Recursively & Randomly Draws Triangles Within The Window, Slightly Adjusting Direction, Distance and Size With
    Each New Triangle

    :param elements: How Many Triangles to Draw
    :return: The Area Of All The Triangles It Drew
    """

    if elements > 0:
        # Draw Triangle
        turtle.penup()

        count = draw_triangle(randrange(*MAX_SIZE))

        # Relocate
        stay_in_bounds()
        turtle.forward(randrange(*MAX_DISTANCE))
        turtle.setheading(turtle.heading() + randrange(*MAX_ANGLE))

        # Tail Recurse, Sorta Sketchy Way To Sum It, But It Works
        return count + draw_figures_rec(elements - 1)

    # Not Returning None Cause That'd Probably Cause Problems : Perimeter (PSS) / Area (Lab)
    return 0


def draw_figures_iter(elements: int) -> float:
    """
    Iteratively & Randomly Draws Triangles Within The Window, Slightly Adjusting Direction, Distance and Size With
    Each New Triangle

    :param elements: How Many Triangles to Draw
    :return: The Area Of All The Triangles It Drew
    """

    total = 0.0

    for i in range(elements):
        # Draw Triangle
        turtle.penup()

        total += draw_triangle(randrange(*MAX_SIZE))

        # Reposition
        stay_in_bounds()
        turtle.forward(randrange(*MAX_DISTANCE))
        turtle.setheading(turtle.heading() + randrange(*MAX_ANGLE))

    return total


def eq_tri_area(side_length: int) -> float:
    """
    Implementation of Heron's Formula

    :param side_length: The Side Length of All Sides of the Given Triangle
    :return: The Area of the Triangle
    """
    p = (3 * side_length) / 2
    return sqrt(p * (3 * (p - side_length)))


if __name__ == '__main__':

    # Set Some General Parameters
    turtle.speed(0)
    turtle.hideturtle()
    turtle.setworldcoordinates(-200, -200, 200, 200)

    num_arrows = int(input("Arrows (0-500): "))

    if num_arrows > 500 or num_arrows < 0:
        print("Arrows must be between 0 and 500 inclusive.")

    else:

        # Recursive
        print("The total area painted is {units} units.".format(units=draw_figures_rec(num_arrows)))

        # Switch to Other Implementation
        input("Hit enter to continue...")  # Lazy Way - Block The Main Loop

        turtle.clear(), turtle.home()

        # Iterative
        print("The total area painted is {units} units.".format(units=draw_figures_iter(num_arrows)))

        # Finalize Things
        print("Close the canvas window to quit.")
        turtle.done()  # Didn't Realize Turtle.done() Blocked The Main Thread - Makes Sense Though
