package com.fxgraph.shape;


import com.fxgraph.graph.Cell;
import javafx.scene.shape.Circle;

public class fxCircle extends Cell {
    public fxCircle(String id, String name) {
        super(id, name);

        Circle view = new Circle(25, 25, 25);
        view.getStyleClass().add("circle");
        setView(view);


            xProperty().bind(layoutXProperty().add(getBoundsInLocal().getMaxX()));
            yProperty().bind(layoutYProperty().add(getBoundsInLocal().getMaxY()));



    }

}
