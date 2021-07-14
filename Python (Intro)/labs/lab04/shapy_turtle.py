"""
Sam Harris
Lab 04
This One was Rather "Fun"
"""

import turtle

# Constants
SINGLE_ARG_CMD = ['<', '>', 'S', 'T', 'C', 'F', 'B', 'Z']
DUAL_ARG_CMD = ['R', 'P', 'G']
NO_ARG_CMD = ['U', 'D']
ALL_CMDS = [*SINGLE_ARG_CMD, *DUAL_ARG_CMD, *NO_ARG_CMD]

# Really Really Lazy Way To Make All Indexes After 4 Black
COLORS = ["red", "blue", "green", "yellow", "brown", *["black" for i in range(10000)]]


def get_number_mono_arg(text: str) -> int:
    """
    Gets the numerical argument for SINGLE_ARG_COMMANDS

    :param text: The String To Parse For The Number
    :return: the command's argument
    """

    result = ""

    for i in range(len(text)):
        if text[i].isnumeric():
            result += text[i]

        elif text[i] in ALL_CMDS:
            break

    return int(result)


def get_number_dual_arg(text: str) -> (int, int):
    """
    Gets the numerical arguments for DUAL_ARG_COMMANDS

    :param text: The String To Parse For The Number
    :return: the command's argument
    """

    result_1, result_2 = "", ""
    flag: bool = False  # Should Allow First Arg to Read, Then Second Arg, Then Break Out of The Loop

    for i in range(len(text)):
        if text[i].isnumeric():
            if not flag:
                result_1 += text[i]
            else:
                result_2 += text[i]

        elif text[i].isalpha() and flag:
            return int(result_1), int(result_2)

        elif text[i].isalpha():
            flag = True


def process_command(text: str) -> list:
    """
    Processes user input string, using different methods based on the number of arguments

    :param text: the user input
    :return: list of tuples of (command letter, *args)
    """
    commands = []

    for i in range(len(text)):

        try:

            if text[i] in SINGLE_ARG_CMD:
                commands.append((text[i], get_number_mono_arg(text[i + 1:])))

            elif text[i] in DUAL_ARG_CMD:
                commands.append((text[i], get_number_dual_arg(text[i + 1:])))

            elif text[i] in NO_ARG_CMD:
                commands.append(text[i], )

        # This is Awful Way to Error Handle, Sorry
        except Exception:
            print("An Error Occurred, Exiting")
            exit(SystemExit)

    print(commands)
    return commands


def commands_to_turtle(commands: list) -> None:
    """
    Takes the command list and then calls the relevant turtle functions in order

    :param commands: List of Command Letter and it's Arguments in a tuple
    """

    for h in commands:
        print(h)

        command_type = len_of_collection(h)
        i = h[0]

        if command_type > 1:
            j = h[1]

        if command_type == 1:

            # Pen Up
            if i == 'U':
                turtle.penup()

            # Pen Down
            if i == 'D':
                turtle.pendown()

        elif command_type == 2:

            # Left
            if i == '<':
                turtle.left(j)

            # Right
            if i == '>':
                turtle.right(j)

            # Square
            if i == 'S':
                for _ in range(4):
                    turtle.right(90)
                    turtle.forward(j)

            # Triangle
            if i == 'T':
                for _ in range(3):
                    turtle.right(60)
                    turtle.forward(j)

            # Circle
            if i == 'C':
                turtle.circle(j)

            # Forwards
            if i == 'F':
                turtle.forward(j)

            # Backwards
            if i == 'B':
                turtle.backward(j)

            # Color
            if i == 'Z':
                turtle.color(COLORS[j])

        elif command_type == 3:

            # Rectangle
            if i == 'R':
                for _ in range(2):
                    turtle.right(90)
                    turtle.forward(j[0])
                    turtle.right(90)
                    turtle.forward(j[1])

            # Polygon
            if i == 'P':
                for _ in range(j[0]):
                    turtle.right(360 / j[0])
                    turtle.forward(j[1])

            # Goto
            if i == 'G':
                turtle.goto(j[0], j[1])

        else:
            print("Something Went Horribly Wrong")
            exit(SystemExit)


def len_of_collection(collection: list or tuple) -> int:
    """
    Recursively find the length of a collection of n nested lists/tuples
    Mainly did this one for fun

    :param collection: root list/tuple
    :return: total elements
    """

    count = 0

    for item in collection:
        if type(item) is tuple or type(item) is list:
            count += len_of_collection(item)
        else:
            count += 1

    return count


def main():
    # User Input
    text = input("Enter Your Command String: ").replace(',', '')

    # Split The Input Into Tuples of (Command_Var & Args)
    command_map = process_command(text)  # Technically Not a Map, But Same Idea Just With Tuple and Indeterminate *Args

    # Activate The Commands For Turtle
    commands_to_turtle(command_map)

    # Keep Screen Open
    turtle.done()


if __name__ == "__main__":
    main()
