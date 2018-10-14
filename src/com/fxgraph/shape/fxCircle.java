package com.fxgraph.shape;


import com.fxgraph.graph.Cell;
import javafx.scene.shape.Circle;

public class fxCircle extends Cell {
    public fxCircle(String id, String name) {
        super(id, name);

        Circle view = new Circle(25, 25, 25);
        view.getStyleClass().add("circle");
        setView(view);

//        setOnMouseClicked(t -> {
//            if (t.getClickCount() == 2) {
//                super.getTextField().setVisible(!super.getTextField().visibleProperty().getValue());
//            }
//        });

            xProperty().bind(layoutXProperty().add(getBoundsInLocal().getMaxX()).divide(2));
            yProperty().bind(layoutYProperty().add(getBoundsInLocal().getMaxY()).divide(2));



    }

}
