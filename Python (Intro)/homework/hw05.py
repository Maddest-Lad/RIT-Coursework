"""
Sam Harris
9/~14/20
Homework 05
"""


import turtle

COLORS = ["red", "yellow", "green", "blue", "purple"]


def goto(x, y):
    """
    Custom Implementation of Turtle.goto() in case we're still not allowed to use it

    :param x: The X Coordinate To Traverse To
    :param y: The Y Coordinate To Traverse To
    """

    turtle.penup()

    turtle.setheading(90)
    turtle.forward(y - turtle.ycor())

    turtle.setheading(0)
    turtle.forward(x - turtle.xcor())


def square(color: str) -> None:
    """
    Draws A Filled In 10 Pixel Square At The Given Position Starting From The Lower Left Corner

    :param color: The Color That The Square Will Be
    """

    turtle.pendown()
    turtle.color(color)
    turtle.begin_fill()

    for i in range(4):
        turtle.forward(10)
        turtle.left(90)

    turtle.end_fill()
    turtle.penup()


def paint_line(line: str) -> None:
    """
    Paints Chars From A Line Different Colors Depending on The Integer Representation of The Char's Unicode Value

    :param line: The Line Of Text To Paint
    """

    for i, j in enumerate(line):
        if 0 <= ord(j) < 70:
            square(COLORS[0])
        elif 70 <= ord(j) < 100:
            square(COLORS[1])
        elif 100 <= ord(j) < 110:
            square(COLORS[2])
        elif 110 <= ord(j) < 122:
            square(COLORS[3])
        elif 122 <= ord(j):
            square(COLORS[4])

        turtle.forward(10)

    # Reposition For Next Row
    goto(-300, turtle.ycor() - 10)

    return None


def paint(filename: str) -> None:
    """
    Calls paint_line() for each line inside filename

    :return:
    """

    with open(filename, 'r') as file:
        lines = file.read().split("\n")

        for line in lines:
            paint_line(line.strip())


if __name__ == "__main__":
    # Setup For Painting
    turtle.penup()
    goto(-300, 300)
    turtle.tracer(0, 0)

    # Paint
    paint(input("Enter The Filename: "))

    # Keep Window Open
    turtle.done()
