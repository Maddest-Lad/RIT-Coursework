"""
Sam Harris
Lab 8

Note:
    At first, it may look like I'm using a list for several operations besides the use of .split(),
    however, I've simply written my own linkedlist which implements python built-ins such as:

        [] : __getitem__
        in : __contains__

    and other operators. This was primarily done because the CS dept linkedlist felt more like a lazy node wrapper
    than a practical data structure, and I was too lazy to look through some of the other files until after I had
    reached a significant level of sunken cost
"""

import numbers

from better_linked_list import BetterLinkedList


class Tracker:
    """                                                                                                     f
    A useful class / wrapper so I didn't have to pass each linkedlist in each time I wanted to do something

    stations: BetterLinkedList: Nodes Contain The Name And Distance From The Previous Station
    train_cars: BetterLinkedList: Nodes that Contain the Cargo and Destination Station
    """

    def __init__(self):
        self.stations = BetterLinkedList()
        self.train_cars = BetterLinkedList()
        self.speed = 0

    def add_station(self, name: str, distance: float) -> bool:

        if distance not in self.stations:
            self.stations.add_end(name, distance)
            return True

        return False

    def set_speed(self, speed) -> bool:

        if isinstance(speed, numbers.Number):
            self.speed = speed
            return True

        return False

    def add_car(self, content: str, station: str):

        if station in self.stations:
            self.train_cars.add_front(content, station)
            return True

        return False

    def show_route(self) -> None:
        print(f"Train Speed {self.speed}")

        for index in range(self.stations.size):
            if index < self.stations.size - 1:
                print(self.stations[index], "---> ", end='')
            else:
                print(self.stations[index])

    def show_train(self) -> None:
        print(f"Train Cars:")

        for index in range(self.train_cars.size):
            if index < self.train_cars.size - 1:
                print(self.train_cars[index], "---> ", end='')
            else:
                print(self.train_cars[index])


def start(tracker: Tracker):
    stations, train_cars = tracker.stations, tracker.train_cars

    total_time = 0.0

    while stations.size > 0:
        current = stations.pop()
        print(f"Moving on to {current.name}")

        for i in range(train_cars.size):
            if train_cars[i] is not None and train_cars[i].data == current.name:
                print(f"Unloading {train_cars[i].name} in {current.name}")
                total_time += 0.5

        print(f"This segment took {current.data / tracker.speed} hours to travel.")
        total_time += current.data / tracker.speed

    print(f"Total time for trip was {total_time} hours.")


def help():
    print("""add_car <content> <station>
             set_speed <speed>
             add_station <station> <distance>
             show_route
             show_train
             start
             help
             quit""")


def quit() -> None:
    exit("Train Yard Simulation Ending")


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

        elif name == "start":
            start(tracker)

        elif name == "help":
            help()

        elif name == "quit":
            quit()

        else:
            print("Illegal Command Name")


if __name__ == '__main__':
    print("Welcome to The Train Yard")

    t = Tracker()
    process_command(t)

    # # Debug / Testing
    #
    # # Setup Stations
    # t.add_station("Tau", 0)  # Start
    # t.add_station("Air", 25)
    # t.add_station("Nix", 15)
    # t.add_station("Red", 10)
    #
    # # Setup Train Cars
    # t.add_car(("Prototypes", "Tau"))
    # t.add_car(("Beakers", "Air"))
    # t.add_car(("Feathers", "Red"))
    # t.add_car("Snacks", "Nix"))
    # t.add_car("Cats", "Tau")
    # t.add_car("Equipment", "Air")
    #
    # t.set_speed(60)
    #
    # t.show_train()
    # t.show_route()
    #
    # print(" ")