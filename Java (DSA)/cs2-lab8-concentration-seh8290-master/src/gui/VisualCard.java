package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Card;

public class VisualCard extends Button {

    // General Member Variables
    private static ConcentrationGUI cg;
    public int row;
    public int col;
    public Card card;
    public boolean faceUp;
    // Button Part
    public Button button;
    // Image States
    private ImageView back = new ImageView(new Image(VisualCard.class.getResourceAsStream("resources/facedown.png")));
    private ImageView front;

    public VisualCard(int i, int j, ConcentrationGUI cg, Card card) {
        VisualCard.cg = cg;
        this.row = i;
        this.col = j;
        this.card = card;
        this.front = new ImageView(new Image(VisualCard.class.getResourceAsStream("resources/" + card.realNumber + ".png")));

        // Initialize a Face Down Button
        this.button = new Button();
        setFaceDown();

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!isFaceUp())
                {
                    setFaceUp();
                    cg.update(cg.model, null);
                    card.toggleFace();
                }
            }
        });
    }

    public void setFaceUp() {
        this.button.setGraphic(front);
        this.faceUp = true;
    }

    public void setFaceDown() {
        this.button.setGraphic(back);
        this.faceUp = false;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public boolean containsCard(Card c) {
        return this.card.equals(c);
    }
}
