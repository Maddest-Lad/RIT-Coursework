"""
Sam Harris
Practical Exam #1
"""

import turtle

HEIGHT = 1


# Draws a Rectangle of HEIGHT x width
def rectangle(width: int) -> int:
    turtle.pendown()
    turtle.forward(width / 2)
    turtle.right(90)
    turtle.forward(HEIGHT)
    turtle.right(90)
    turtle.forward(width)
    turtle.right(90)
    turtle.forward(HEIGHT)
    turtle.right(90)
    turtle.forward(width / 2)
    turtle.penup()

    return width * HEIGHT


# Recursively Draws a Pyramid
def draw_pyramid_rec(levels: int) -> int:
    if levels == 0:
        return 0

    else:
        # Reposition
        turtle.right(90)
        turtle.forward(HEIGHT)
        turtle.left(90)
        area = rectangle(levels)  # Draw Level

        return area + draw_pyramid_rec(levels - 1)  # Tail Recurse


# Iteratively Draws a Pyramid
def draw_pyramid_iter(levels: int) -> int:
    area = 0

    while levels > 0:
        area += rectangle(levels)

        # Position For Next Row
        turtle.right(90)
        turtle.forward(HEIGHT)
        turtle.left(90)

        # Decrement Levels
        levels -= 1

    return area


# Resets Turtle For Next Drawing
def reset(levels: int) -> None:
    turtle.reset()
    turtle.penup()
    turtle.setworldcoordinates(-1, -1, levels + 1, levels + 1)
    turtle.goto(x=levels / 2, y=0)
    turtle.setheading(180)
    turtle.speed(0)


if __name__ == "__main__":
    levels = int(input("Enter The Depth Of The Pyramid: "))  # Expect Levels > 0

    # Iterative
    reset(levels)
    iter_area = draw_pyramid_iter(levels)

    # Pause In The Middle
    input("Press Any Key to Continue ")

    # Recursive
    reset(levels)
    turtle.goto(x=levels / 2, y=-1)  # Little Bit of Fine Tuning, Mildly Ugly /Shrug
    rec_area = draw_pyramid_rec(levels)

    # Print Area
    print("Iterative Area Was {}".format(rec_area))
    print("Recursive Area Was {}".format(iter_area))

    # Block Main Thread Until Done
    input("Press Any Key To Quit ")