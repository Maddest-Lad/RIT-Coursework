package puzzles.jam.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class CarButton extends Node {

    public Button button;
    public String color;
    public int x, y;
    private String ID;
    private JamModel model;

    public CarButton(String ID, JamModel model, int x, int y) {
        this.ID = ID;
        this.button = new Button();
        this.x = x;
        this.y = y;

        // Oh the fun semantics
        this.color = new Colors().colorMap.get((ID.toCharArray()[0]));

        button.setText(ID);
        button.setStyle(
                "-fx-font-size: " + 36 + ";" +
                        "-fx-background-color: " + color + ";" +
                        "-fx-font-weight: bold;");

        button.setMinHeight(100);
        button.setMaxHeight(100);
        button.setMinWidth(100);
        button.setMaxWidth(100);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                model.select(y, x);
            }
        });


    }


}
