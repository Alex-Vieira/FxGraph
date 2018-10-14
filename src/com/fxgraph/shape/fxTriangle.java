package com.fxgraph.shape;


import com.fxgraph.graph.Cell;
import javafx.scene.shape.Polygon;

public class fxTriangle extends Cell {

    public fxTriangle(String id, String name) {
        super(id, name);

        double width = 50;
        double height = 50;

        Polygon view = new Polygon(width / 2, 0, width, height, 0, height);
        view.getStyleClass().add("triangle");
        setView(view);
    }

}