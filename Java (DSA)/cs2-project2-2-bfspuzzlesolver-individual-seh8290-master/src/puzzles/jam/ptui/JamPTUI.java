package puzzles.jam.ptui;

import puzzles.common.Observer;
import puzzles.jam.model.JamClientData;
import puzzles.jam.model.JamModel;
import puzzles.jam.model.JamNode;
import puzzles.jam.solver.Jam;

import java.io.IOException;
import java.util.Scanner;

public class JamPTUI implements Observer<JamModel, JamClientData> {

    private JamModel model;

    // Controller
    public static void main(String[] args) throws IOException {
        if (args.length != 1)
        {
            System.out.println("Usage: java JamPTUI filename");
        } else
        {
            JamNode root = Jam.readFileToGame(args[0]);
            JamModel model = new JamModel(root, args[0]);
            System.out.println("Loaded: " + args[0]);
            model.printBoard();
            Scanner scanner = new Scanner(System.in);

            for (; ; )
            {
                System.out.print("Enter Command: ");
                String input = scanner.next().toLowerCase();

                int row, col;

                switch (input)
                {
                    case "h":
                        model.hint();
                        break;

                    case "l":
                        model.load(scanner.next());
                        break;

                    case "r":
                        model.reset();
                        break;

                    case "s":
                        row = scanner.nextInt();
                        col = scanner.nextInt();
                        model.select(col, row);
                        break;

                    case "q":
                        System.exit(-1);

                    default:
                        help();
                }
                model.printBoard();
            }
        }
    }

    private static void help() {
        System.out.println("h(int)              -- hint next move\n" +
                "l(oad) filename     -- load new puzzle file\n" +
                "s(elect) r c        -- select cell at r, c\n" +
                "q(uit)              -- quit the game\n" +
                "r(eset)             -- reset the current game");
    }


    @Override
    public void update(JamModel jamModel, JamClientData jamClientData) {
        jamModel.printBoard();

        if (jamModel.getCurrentConfig().isGoal())
        {
            System.out.println("You Win!");
        }

    }
}
