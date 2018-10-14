package com.fxgraph.shape;

import com.fxgraph.graph.Cell;
import javafx.scene.shape.Rectangle;

public class fxSquare extends Cell {

    public fxSquare(String id, String name) {
        super(id, name);

        Rectangle view = new Rectangle();
        view.getStyleClass().add("rectangle");
        setView(view);
    }
}
