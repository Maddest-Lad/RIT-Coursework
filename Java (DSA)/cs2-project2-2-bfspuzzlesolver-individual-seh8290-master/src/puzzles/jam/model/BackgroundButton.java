package puzzles.jam.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class BackgroundButton {

    public Button button;
    int row, col;

    public BackgroundButton(int row, int col, JamModel model) {
        this.row = row;
        this.col = col;

        button = new Button();
        button.setText("");
        button.setMinHeight(100);
        button.setMaxHeight(100);
        button.setMinWidth(100);
        button.setMaxWidth(100);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                model.select(col, row);
            }
        });

    }

}
