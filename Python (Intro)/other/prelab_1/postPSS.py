"""
Author: Sam Harris
Post Problem Solving Exercises
"""


import turtle


def draw_bowtie(size: int):
    """
    Draws a red bowtie per the final labs specifications

    :param size: the side length used for each of the two triangles
    """

    turtle.color("red")

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
    turtle.left(30)

    draw_bowtie_circle(size)


def draw_bowtie_circle(size: int):
    """
    offsets and draws a circle in the center of the bowtie
    :param size: the size of the circle to be drawn
    :return:
    """
    turtle.penup()
    turtle.backward(size / 4)  # Center The Circle On The Middle
    turtle.right(90)
    turtle.pendown()
    turtle.fillcolor("blue")
    turtle.begin_fill()
    turtle.circle(size / 4)
    turtle.end_fill()


if __name__ == "__main__":
    draw_bowtie(100)
    turtle.hideturtle()
    turtle.done()
