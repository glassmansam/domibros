package gui;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class ToppingLabel extends Label {

    public ToppingLabel(String toppingName) {
        setText(toppingName);
        setStyle("-fx-border-style: solid solid solid solid;" +
                "-fx-border-color: #000000;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 5px;");
        setPadding(new Insets(3, 5, 3, 5));
        setFont(Font.font(12));
        setCursor(Cursor.DEFAULT);
    }
}
