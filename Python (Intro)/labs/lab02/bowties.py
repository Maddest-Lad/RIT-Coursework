"""
Sam Harris
9/7/2020

Lab 02 - Recursively Draws Bowties
"""

import turtle


def draw_bowtie(size: float) -> None:
    """
    Draws a red bowtie per the final labs specifications

    Pre-Condition & Post-Condition: Starts and ends facing right at the center of the bowtie

    :param size: the side length used for each of the two triangles
    """

    # Pre-Config
    turtle.color("red")
    turtle.pendown()

    # Draw Bowtie Body
    turtle.right(30)
    turtle.forward(size)
    turtle.left(120)
    turtle.forward(size)
    turtle.left(120)
    turtle.forward(size * 2)
    turtle.right(120)
    turtle.forward(size)
    turtle.right(120)
    turtle.forward(size)

    draw_bowtie_circle(size / 4)

    turtle.left(60)


def draw_bowtie_circle(size: float) -> None:
    """
    Offsets the turtle and then draws a circle centered on the bowtie

    Pre-Condition: Starts facing directly right at the center of the bowtie
    Post-Condition: Heading is from the Lower Left to Upper Right Corners of Bowtie, In The Center Position

    :param size: the size of the circle to be drawn
    :return: None
    """
    turtle.penup()
    turtle.backward(size)  # Center The Circle On The Middle
    turtle.right(90)
    turtle.pendown()
    turtle.fillcolor("blue")
    turtle.begin_fill()
    turtle.circle(size)
    turtle.end_fill()
    turtle.penup()
    turtle.left(90)
    turtle.forward(size)


def recursive(depth, size):
    """
    recursively draws bowties until depth

    :param depth: what level of iteration we're on
    :param size: side length of the triangle
    :return: None
    """

    # Draw the Primary One For Each Level
    if depth > 0:
        draw_bowtie(size)

    # Start Drawing Children Where Necessary
    if depth > 1:
        # First of 4 Children
        turtle.forward(size * 2)
        recursive(depth - 1, size / 3)  # Start The Next Level
        turtle.right(30)  # Return to Previous Position
        turtle.backward(size * 2)

        # Second of 4 Children
        turtle.right(60)
        turtle.forward(size * 2)
        recursive(depth - 1, size / 3)  # Start The Next Level
        turtle.right(30)  # Return to Previous Position
        turtle.backward(size * 2)

        # Third of 4 Children
        turtle.right(120)
        turtle.forward(size * 2)
        recursive(depth - 1, size / 3)  # Start The Next Level
        turtle.right(30)  # Return to Previous Position
        turtle.backward(size * 2)

        # Final Child
        turtle.right(60)
        turtle.forward(size * 2)
        recursive(depth - 1, size / 3)  # Start The Next Level
        turtle.right(30)  # Return to Previous Position
        turtle.backward(size * 2)

        # Reset Angle Before Next Depth
        turtle.right(120)


if __name__ == "__main__":
    """
    precondition: the program hasn't ran yet
    postcondition: the turtle has finished drawing
    """

    depth = int(input("Enter Recursive Depth: "))

    turtle.speed(0)
    turtle.hideturtle()

    # Set Scale to 1/6 the Average of Height and Width
    x, y = turtle.screensize()
    size = (x + y) / 2 * (1.0 / 6.0)

    recursive(depth=depth, size=size)

    turtle.done()
