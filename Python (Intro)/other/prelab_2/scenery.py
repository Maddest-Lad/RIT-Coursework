import turtle
from math import pi
from random import randint, getrandbits

"""
Author: Sam Harris
Date: 8/30/2020

Lab 01
"""


def draw_ground() -> float:
    """
    draws 100 pixel long chunks of green ground

    :return: float representing the ink used
    """

    turtle.pendown()
    turtle.color("green")
    turtle.forward(100)
    turtle.penup()

    return 100


def draw_tree() -> float:
    """
    draws either:
        a pine tree containing a trunk between 50 and 200 pixels tall and a triangle whose sides are 60% of that size
        a maple tree containing a trunk between 50 and 150 pixels tall with a circle that is 40% of the height

    :return: float representing the ink used
    """

    # Reset For This Operation
    turtle.pendown()
    turtle.color("green")

    # Random Tree Choice
    choice = bool(getrandbits(1))

    # Drawing The Trunk
    turtle.left(90)
    random_height = randint(50, 200) if choice else randint(50, 150)
    turtle.forward(random_height)

    if choice:

        # Draw the ~Pine~ Triangle
        turtle.right(90)
        turtle.forward((random_height * 0.6) / 2)

        for i in range(2):
            turtle.left(120)
            turtle.forward(random_height * 0.6)

        turtle.left(120)
        turtle.forward((random_height * 0.6) / 2)

    else:

        # Draw the ~Maple~ Circle
        turtle.right(90)
        turtle.circle(random_height * 0.4)

    # Return The Turtle To Base Position
    turtle.right(90)
    turtle.forward(random_height)
    turtle.left(90)
    turtle.penup()

    treetop_ink = 3 * (random_height * 0.6) if choice else (random_height * 0.4) * pi
    return random_height + treetop_ink


def draw_house(house_color: str) -> float:
    """
    draws a house with 3 100 pixel walls and a roof consisting of a 45 45 90 triangle without the base

    :return: float representing the ink used
    """

    # Reset For This Operation
    turtle.pendown()
    turtle.color(house_color)

    # Draw The House
    turtle.forward(100)
    turtle.left(90)
    turtle.forward(100)
    turtle.left(45)
    turtle.forward(70.71068)
    turtle.left(90)
    turtle.forward(70.71068)
    turtle.left(45)
    turtle.forward(100)

    # Reset Position
    turtle.penup()
    turtle.left(90)
    turtle.forward(100)

    return 300 + 2 * 70.71068


if __name__ == '__main__':

    # Set Custom Window And Move Turtle to It's New Starting Position
    turtle.penup()
    turtle.setworldcoordinates(-500, -500, 500, 500)
    turtle.backward(500)

    # Trackers
    ink: float = 0.00

    # User Input
    house: bool = input("Is there a house in the forest (y/n)? ").lower() == 'y'
    position: int = int(input("At what position (1/2/3)? "))
    color: str = input("What color is the house? ")

    if house:
        if position == 3:
            ink += draw_ground()
            ink += draw_house(color)
            ink += draw_ground()
            ink += draw_tree()
            ink += draw_ground()
            ink += draw_tree()
            ink += draw_ground()

        if position == 2:
            ink += draw_ground()
            ink += draw_tree()
            ink += draw_ground()
            ink += draw_house(color)
            ink += draw_ground()
            ink += draw_tree()
            ink += draw_ground()

        else:
            ink += draw_ground()
            ink += draw_tree()
            ink += draw_ground()
            ink += draw_tree()
            ink += draw_ground()
            ink += draw_house(color)
            ink += draw_ground()
    else:
        ink += draw_ground()
        ink += draw_tree()
        ink += draw_ground()
        ink += draw_tree()
        ink += draw_ground()

    # Ending Outputs
    print("We used {ink} units of ink for the drawing.".format(ink=ink))
    print("Close the diagram to end the program.")

    turtle.done()
