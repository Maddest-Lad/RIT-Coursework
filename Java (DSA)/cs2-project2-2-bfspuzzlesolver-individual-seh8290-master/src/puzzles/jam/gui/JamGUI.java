package puzzles.jam.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.common.Observer;
import puzzles.jam.model.BackgroundButton;
import puzzles.jam.model.CarButton;
import puzzles.jam.model.JamClientData;
import puzzles.jam.model.JamModel;
import puzzles.jam.solver.Jam;

import java.io.File;
import java.io.IOException;

public class JamGUI extends Application implements Observer<JamModel, JamClientData> {

    private final static int BUTTON_FONT_SIZE = 20;
    private final static int ICON_SIZE = 75;
    // General JavaFX Fun
    private final int GAP = 0;
    private final String TITLE = "Rush Hour";
    // Instance Vars
    public JamModel model;
    public Stage stage;
    private GridPane bottomButtons;
    private BorderPane borderPane;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void init() throws IOException {
        generateBottomButtons();
    }

    public GridPane regenGridPane() {

        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(false);
        gridPane.setHgap(GAP);
        gridPane.setVgap(GAP);

        var board = model.getCurrentConfig().getBoard();

        for (int row = 0; row < model.getCurrentConfig().rows; row++)
        {

            for (int col = 0; col < model.getCurrentConfig().cols; col++)
            {
                if(!board[row][col].equals(Jam.EMPTY_SPACE)) {
                    gridPane.add(new CarButton(board[row][col], model, row, col).button, col, row);
                } else {
                    gridPane.add(new BackgroundButton(row, col, model).button, col, row);
                }
            }
        }

        return gridPane;
    }

    public void generateBottomButtons() throws IOException {

        Button loadButton = new Button("Load");
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try
                {
                    FileChooser fileChooser = new FileChooser();
                    File selectedFile = fileChooser.showOpenDialog(stage);
                    model.load(selectedFile.toString());

                } catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try
                {
                    model.reset();
                } catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        Button hintButton = new Button("Hint");
        hintButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                model.hint();
            }
        });

        GridPane bottomButtons = new GridPane();
        bottomButtons.add(loadButton, 0, 0);
        bottomButtons.add(resetButton, 1, 0);
        bottomButtons.add(hintButton, 2, 0);

        this.bottomButtons = bottomButtons;
    }

    @Override
    public void start(Stage stage) throws Exception {
        String filename = getParameters().getRaw().get(0);
        model = new JamModel(Jam.readFileToGame(filename), filename, this);
        BorderPane borderPane = new BorderPane();

        // Top
        Label top = new Label("Have Fun :)");
        borderPane.setTop(top);
        BorderPane.setAlignment(top, Pos.CENTER_LEFT);

        // Bottom
        borderPane.setBottom(bottomButtons);
        bottomButtons.setAlignment(Pos.CENTER);

        // Center - GridPane
        GridPane gridPane = regenGridPane();
        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(gridPane, Pos.CENTER);

        // Build Scene
        this.borderPane = borderPane;
        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.setResizable(false); // Don't Let Users Mess With This
        stage.show();
        this.stage = stage;
    }

    // I Know I Didn't Really Use The Observer Part of MVC, But Everything Mostly Works So ¯\_(ツ)_/¯
    public void update(JamModel jamModel, JamClientData jamClientData) {
        borderPane.setCenter(regenGridPane());
        borderPane.getScene().getWindow().setWidth(stage.getWidth());
    }
}
