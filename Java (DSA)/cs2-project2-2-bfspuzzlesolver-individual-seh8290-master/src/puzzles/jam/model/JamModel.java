package puzzles.jam.model;

import javafx.scene.control.Button;
import puzzles.common.Observer;
import puzzles.jam.gui.JamGUI;
import puzzles.jam.solver.Jam;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class JamModel {
    /**
     * the collection of observers of this model
     */
    private final List<Observer<JamModel, JamClientData>> observers = new LinkedList<>();
    public boolean carIsSelected;
    public Button selected;
    /**
     * the current configuration
     */
    private JamNode currentConfig;
    private String currentBoard;
    private int row, col;
    private JamGUI gui;

    public JamModel(JamNode currentConfig, String currentBoard) {
        this.currentConfig = currentConfig;
        this.currentBoard = currentBoard;
        this.carIsSelected = false;
        this.gui = null;
    }

    public JamModel(JamNode currentConfig, String currentBoard, JamGUI gui) {
        this.currentConfig = currentConfig;
        this.currentBoard = currentBoard;
        this.carIsSelected = false;
        this.gui = gui;
    }

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<JamModel, JamClientData> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(JamClientData data) throws IOException {
        for (var observer : observers)
        {
            observer.update(this, data);
        }
    }

    public JamNode getCurrentConfig() {
        return currentConfig;
    }

    public void setCurrentConfig(JamNode currentConfig) {
        this.currentConfig = currentConfig;
        cleanUp();
    }

    public void hint() {
        System.out.println("Next Step");
        this.setCurrentConfig((JamNode) getCurrentConfig().nextStep());
        cleanUp();
    }

    public void load(String filename) throws IOException {
        try
        {
            currentConfig = Jam.readFileToGame(filename);
            currentBoard = filename;
            cleanUp();
            System.out.println("Loaded " + filename);
        } catch (IOException error)
        {
            System.out.println(error.getMessage());
        }
    }

    public void reset() throws IOException {
        load(currentBoard);

    }

    public Optional<MoveStatus> select(int row, int col) {
        if (carIsSelected)
        {
            MoveStatus status = getCurrentConfig().Move(this.row, this.col, row, col);
            carIsSelected = false;
            System.out.println("Attempted Move from (" + this.row + "," + this.col + ") to (" + row + "," + col + ") : Status : " + status);
            cleanUp();
            return Optional.ofNullable(status);
        } else
        {
            System.out.println("Selected (" + row + "," + col + ")");
            this.row = row;
            this.col = col;
            carIsSelected = true;
        }
        return Optional.empty();
    }

    public void printBoard() {
        StringBuilder sb = new StringBuilder();

        sb.append("  ");
        // Print Top Numbers
        for (int row = 0; row <= getCurrentConfig().rows - 1; row++)
        {
            sb.append(row).append(" ");
        }
        sb.append("\n");
        int col = 0;

        for (String[] row : getCurrentConfig().getBoard())
        {
            sb.append(col).append(" ");
            for (String value : row)
            {
                sb.append(value).append(" ");
            }
            sb.append("\n");
            col++;
        }

        System.out.println(sb.toString());
    }

    public void cleanUp() {
        if (gui != null)
        {
            System.out.println("something happened");
            gui.update(this, new JamClientData(""));
        }
    }
}
