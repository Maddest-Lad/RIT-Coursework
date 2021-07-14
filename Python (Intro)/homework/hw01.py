"""
Author: Sam Harris
Date: 8/19/2020 4:07 PM

Description:
a program that prints 5 pointed stars using the turtle graphics library.

License:
This code can be freely copied, modified, altered, distributed without any attribution whatsoever.
"""

import turtle


def draw_star() -> None:
    """
    draws a 5 point star composed of 5 triangles
    """

    for i in range(5):
        # Degree Offset Before Each Triangle
        turtle.right(72)
        draw_triangle(15)


def draw_triangle(scale: int) -> None:
    """
    draws an equilateral triangle 72 degrees (ie 360/5) from the turtle's current position
    :param scale: multiplier for triangle size
    """

    # Draw Equilateral Triangle
    for i in range(3):
        turtle.forward(5 * scale)
        turtle.left(120)

    # Lateral Offset For Next Triangle
    turtle.forward(5 * scale)


def main():
    """
    main program draws a square and waits user to hit Enter key.
    """

    turtle.title("Five Point Star")
    draw_star()

    # Leave Window Open Per Assignment Instructions
    turtle.done()


if __name__ == '__main__':
    main()
