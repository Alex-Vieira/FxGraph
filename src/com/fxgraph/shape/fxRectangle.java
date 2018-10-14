package com.fxgraph.shape;


import com.fxgraph.graph.Cell;
import javafx.scene.shape.Rectangle;

public class fxRectangle extends Cell {

    public fxRectangle(String id, String name) {
        super(id, name);

        Rectangle view = new Rectangle(80, 50);
        view.getStyleClass().add("rectangle");
        setView(view);
    }

}

