package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Card;
import model.ConcentrationModel;
import model.Observer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;


/**
 * The ConcentrationGUI application is the UI for Concentration.
 *
 * @author Sam Harris
 */
public class ConcentrationGUI extends Application implements Observer<ConcentrationModel, Object> {

    // General JavaFX Settings
    private final int GAP = 5; // HGAP / VGAP / Maybe Insets?
    private final int DIM = 4;
    private final String TITLE = "Concentration";
    public ConcentrationModel model;

    // Java Fx Instance Variables
    private GridPane bottomButtons;

    // General Instance Variables
    private VisualCard[][] cardTracker; // Keep Track of Buttons/Card Combo Objects
    private Stack<VisualCard> moves;
    private LinkedList<VisualCard> lastRound;
    private LinkedList<VisualCard> matched;
    private int turnCounter;
    private BorderPane borderPane;


    public ConcentrationGUI() {
        this.model = new ConcentrationModel();
        this.cardTracker = new VisualCard[DIM][DIM];
        this.lastRound = new LinkedList<>();
        this.moves = new Stack<>();
        this.matched = new LinkedList<>();
        this.turnCounter = 0;

        Button resetButton = new Button("Reset");
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                update(model, new otherInfo(false, false, true));
            }
        });

        Button cheatButton = new Button("Cheat");
        cheatButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                update(model, new otherInfo(true, false, false));
            }
        });

        Button undoButton = new Button("Undo");
        undoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                update(model, new otherInfo(false, true, false));
            }
        });

        GridPane bottomButtons = new GridPane();
        bottomButtons.add(resetButton, 0, 0);
        bottomButtons.add(undoButton, 1, 0);
        bottomButtons.add(cheatButton, 2, 0);

        this.bottomButtons = bottomButtons;
    }

    /**
     * main entry point launches the JavaFX GUI.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    // Create The GridPlane That Holds All The Buttons
    public GridPane generateGridPlane() {

        LinkedList<Card> cards = new LinkedList<>(model.getCards());
        Collections.shuffle(cards);

        // GridPlane
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(false);
        gridPane.setHgap(GAP);
        gridPane.setVgap(GAP);

        // Build The 2D Plane
        for (int row = 0; row < DIM; row++)
        {
            for (int col = 0; col < DIM; col++)
            {
                VisualCard vc = new VisualCard(row, col, this, cards.pop());
                gridPane.add(vc.button, col, row); // Invert (I,J) Cause JavaFx is Wacky
                this.cardTracker[row][col] = vc;
            }
        }
        return gridPane;
    }

    // Initialize a BorderPane, Embedding a GridPane & The Top/Bottom Text In Itself
    @Override
    public void start(Stage stage) throws Exception {

        // Build The Scene With GridPlane In The Center of a BorderPlane View, Ignore Left/Right,
        // Top/Bottom Will Be Used For Displaying Win Message / [Reset, Undo, Cheat]
        BorderPane borderPane = new BorderPane();

        // Top
        Label top = new Label("Select A Card");
        borderPane.setTop(top);
        BorderPane.setAlignment(top, Pos.CENTER_LEFT);

        // Bottom
        borderPane.setBottom(bottomButtons);
        bottomButtons.setAlignment(Pos.CENTER);

        // Center - GridPane
        GridPane gridPane = generateGridPlane();
        borderPane.setCenter(gridPane);
        BorderPane.setAlignment(gridPane, Pos.CENTER);

        // Build Scene
        this.borderPane = borderPane;
        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.setResizable(false); // Don't Let Users Mess With This
        stage.show();
    }

    private LinkedList<VisualCard> getDifferenceBetween(LinkedList<VisualCard> a, LinkedList<VisualCard> b) {
        LinkedList<VisualCard> uniqueCards = new LinkedList<VisualCard>();

        for (VisualCard vccA : a)
        {
            if (!b.contains(vccA))
            {
                uniqueCards.add(vccA);
            }
        }

        for (VisualCard vccB : b)
        {
            if (!a.contains(vccB))
            {
                uniqueCards.add(vccB);
            }
        }
        return uniqueCards;
    }

    private LinkedList<VisualCard> getVisualCardsAsList() {
        LinkedList<VisualCard> cards = new LinkedList<>();

        for (int i = 0; i < cardTracker.length; i++)
        {
            for (int j = 0; j < cardTracker[0].length; j++)
            {
                cards.add(cardTracker[i][j]);
            }
        }

        return cards;
    }

    // Returns Unmatched Face Up Cards
    private LinkedList<VisualCard> getFaceUpCards() {

        // Tracker
        LinkedList<VisualCard> cards = new LinkedList<>();

        // Get A Count Of The Face Up Cards
        for (int i = 0; i < cardTracker.length; i++)
        {
            for (int j = 0; j < cardTracker[0].length; j++)
            {
                if (cardTracker[i][j].card.isFaceUp())
                {
                    cards.add(cardTracker[i][j]);
                }
            }
        }

        return cards;
    }

    private LinkedList<VisualCard> getMatches(LinkedList<VisualCard> current) {

        LinkedList<VisualCard> matches = new LinkedList<>();

        for (VisualCard vccA : current)
        {
            for (VisualCard vccB : current)
            {
                if (vccA.card.realNumber == vccB.card.realNumber && !vccA.equals(vccB))
                {
                    matches.add(vccA);
                }
            }
        }

        return matches;
    }

    void undoMoveV2() {
        if (moves.size() >= 1)
        {
            VisualCard lastMove = moves.get(1);
            moves.remove(1);
            lastMove.setFaceDown();
        }
    }

    boolean winConditionMet() {
        for(VisualCard vcc : getVisualCardsAsList()) {
            if(!vcc.isFaceUp()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Update is Called As An Action On Every Button Press
     * Update Then Goes Through The Current Board And Unflips Cards If Two Non-Matching Cards Are Face Up
     * Otherwise it Keeps Going
     *
     * @param ConcentrationModel model
     * @param Object             o Either null or an instance of otherInfo, null is used by Buttons on the board, while every other button uses otherInfo
     */
    @Override
    public void update(ConcentrationModel model, Object o) {

        if(winConditionMet()) {
            this.borderPane.setTop(new Label("You Win!"));
        }

        // Normal Move
        if (o == null)
        {
            LinkedList<VisualCard> faceUp = getFaceUpCards();
            faceUp.removeAll(matched);

            // Look Through The Cards And Add All Matches To The Matched List
            var matches = getMatches(faceUp);
            matched.addAll(getMatches(faceUp));
            faceUp.removeAll(matches);

            moves.addAll(faceUp);
            lastRound = faceUp;

            while (faceUp.size() > 1) {
                undoMoveV2();
                faceUp.remove(1);
            }

            turnCounter++;
            // Call To Update Button?
        } else
        {
            otherInfo info = (otherInfo) o;

            // Reset
            if (info.reset)
            {
                for (VisualCard vcc : getVisualCardsAsList())
                {
                    vcc.setFaceDown();
                }
                borderPane.setTop(new Label("Select a Card"));
                lastRound = new LinkedList<>();
                moves = new Stack<>();
            }

            // Undo Move
            else if (info.undoMove)
            {
                VisualCard lastMove = moves.pop();
                lastMove.setFaceDown();
            } else if (info.cheatMode)
            {
                for (VisualCard vcc : getVisualCardsAsList())
                {
                    vcc.setFaceUp();
                }
            }
        }
    }


}

// More of a data Class / Strut and dumb anyways but it's gonna work so...
class otherInfo {
    boolean cheatMode;
    boolean undoMove;
    boolean reset;

    otherInfo(boolean cheat, boolean undo, boolean reset) {
        this.cheatMode = cheat;
        this.undoMove = undo;
        this.reset = reset;
    }
}