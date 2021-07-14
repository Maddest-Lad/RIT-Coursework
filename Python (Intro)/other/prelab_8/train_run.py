"""
Sam Harris
Post PSS / Lab 8
"""

from better_linked_list import BetterLinkedList


class Tracker:
    """
    Stations is a LinkedList


    """

    def __init__(self):
        self.stations = BetterLinkedList()
        self.train_cars = {}
        self.speed = 0

    def add_station(self, name: str, distance: float):
        self.stations.add_end(name, distance)

    def set_speed(self, speed: float):
        self.speed = speed

    def add_car(self, content: str, station: str):
        self.train_cars[content] = station

    def show_route(self) -> None:
        print(f"Train Speed {self.speed}")

        for index in range(self.stations.size):
            if index < self.stations.size-1:
                print(self.stations[index], "---> ", end='')
            else:
                print(self.stations[index])

    def show_train(self) -> None:
        for key, value in self.train_cars.items():
            print(key, value)


def quit() -> None:
    exit("Train Yard Simulation Ending")


def start():
    return


def help():
    return


def process_command(tracker: Tracker):
    command = ""

    while command != "quit":
        command = input("> ")

        name = command.split(" ")[0]

        if name == "add_station":
            tracker.add_station(*command.split()[1:])

        elif name == "set_speed":
            tracker.set_speed(float(command.split(" ")[1]))

        elif name == "add_car":
            tracker.add_car(*command.split()[1:])

        elif name == "show_route":
            tracker.show_route()

        elif name == "show_train":
            tracker.show_train()

        elif name == "quit":
            quit()

        else:
            print("Illegal Command Name")


if __name__ == '__main__':
    print("Welcome to The Train Yard")

    t = Tracker()

    process_command(t)
