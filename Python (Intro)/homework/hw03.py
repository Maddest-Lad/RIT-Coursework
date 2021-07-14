"""
Author Sam H
Not The Best Code, Was Kinda Lazy and Had A Headache
"""
import turtle

PEN_WIDTH = 2
RADIUS = 150


def init():
    turtle.setpos(0, 0)
    turtle.speed(4)
    turtle.pensize(PEN_WIDTH)
    turtle.down()


def draw_circles_1(radius):
    """
    function that draws a circle
    :param radius - radius of the circles to be drawn
    """

    turtle.circle(radius)


def draw_circles_2(radius, depth=1):
    """
    function that draws 2 circles in a horizontal pattern
    :param radius - radius of the circles to be drawn
    """
    turtle.circle(radius / depth, 90)
    turtle.circle(radius / depth / 2)
    turtle.circle(radius / depth, 180)
    turtle.circle(radius / depth / 2)


def draw_circles_3(radius, depth=1):
    """
    function that draws 2 circles in a vertical pattern
    :param radius - radius of the circles to be drawn
    """
    turtle.circle(radius / depth / 2, 90)
    turtle.circle(radius / depth / 4)
    turtle.circle(radius / depth / 2, 180)
    turtle.circle(radius / depth / 4)

    turtle.circle(radius / depth / 2, 90)
    turtle.right(90)
    turtle.back(radius / depth)
    turtle.left(90)

    turtle.circle(radius / depth / 2, 90)
    turtle.circle(radius / depth / 4)
    turtle.circle(radius / depth / 2, 180)
    turtle.circle(radius / depth / 4)


def draw_circles_rec(radius, depth):
    print(depth, radius, depth % 2 == 0)

    turtle.circle(radius)  # Only Really Needed On The First Run

    if depth > 0:
        if depth % 2 == 0:
            print("First")
            turtle.circle(radius, 90)  # Horizontal Offset
            turtle.circle(radius / 2)
            turtle.circle(radius, 180)
            turtle.circle(radius / 2)
            turtle.circle(radius, 180)

            draw_circles_rec(radius / 2, depth - 1)
            turtle.circle(radius / 2, 180)
            draw_circles_rec(radius / 2, depth - 1)

        else:
            print("Second")
            turtle.circle(radius, 270)  # Vertical Offset
            turtle.circle(radius / 2)
            turtle.circle(radius, 270)
            turtle.circle(radius / 2)
            turtle.circle(radius, 270)

            draw_circles_rec(radius / 2, depth - 1)
            turtle.circle(radius / 2, 90)
            draw_circles_rec(radius / 2, depth - 1)


def main():
    init()

    print("Drawing a depth-1 circles drawing.")
    draw_circles_1(RADIUS)

    input("Hit ENTER to proceed to depth 2:")
    draw_circles_2(RADIUS)

    input("Hit ENTER to proceed to depth 3:")
    draw_circles_3(RADIUS)

    input("Hit ENTER to proceed to recursive code:")
    turtle.clear()

    depth = int(input("depth? "))
    draw_circles_rec(RADIUS, depth)

    print("Close the window to end the program.")
    turtle.done()


if __name__ == '__main__':
    main()
