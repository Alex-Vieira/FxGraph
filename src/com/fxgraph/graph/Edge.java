package com.fxgraph.graph;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class Edge extends Group {

    private Cell source;
    private Cell target;

    private Line line;
    private Path path;

    private DoubleProperty distanceAB = new SimpleDoubleProperty();
    private DoubleProperty calc = new SimpleDoubleProperty();
    private DoubleProperty vectorABX = new SimpleDoubleProperty();
    private DoubleProperty vectorABY = new SimpleDoubleProperty();
    private DoubleProperty vectorRadiusX = new SimpleDoubleProperty();
    private DoubleProperty vectorRadiusY = new SimpleDoubleProperty();

    public Edge(Cell source, Cell target, EdgeType type) {
        this.source = source;
        this.target = target;

        switch (type) {

            case LINE:
                source.addCellChild(target);
                target.addCellParent(source);

                line = new Line();
                line.getStyleClass().add("line");
                line.setStrokeWidth(2);

                line.startXProperty().bind(source.layoutXProperty().add(source.getBoundsInLocal().getMaxX() / 2).add(vectorRadiusXProperty()));
                line.startYProperty().bind(source.layoutYProperty().add(source.getBoundsInLocal().getMaxY() / 2).add(vectorRadiusYProperty()));
                line.endXProperty().bind(target.layoutXProperty().add(target.getBoundsInLocal().getMaxX() / 2).subtract(vectorRadiusXProperty()));
                line.endYProperty().bind(target.layoutYProperty().add(target.getBoundsInLocal().getMaxY() / 2).subtract(vectorRadiusYProperty()));

                getChildren().addAll(line);
                setOnMousePressed(event -> line.setStroke(Color.RED));
                setOnMouseReleased(event -> line.setStroke(Color.BLACK));
                break;

            case ARC:
                source.addCellChild(target);
                target.addCellParent(source);

                path = new Path();
                ArcTo arc = new ArcTo();
                MoveTo moveTo = new MoveTo();
                path.setStrokeWidth(2);

                moveTo.xProperty().bind(source.layoutXProperty().add(source.getBoundsInLocal().getMaxX() / 2).add(vectorRadiusXProperty()));
                moveTo.yProperty().bind(source.layoutYProperty().add(source.getBoundsInLocal().getMaxY() / 2).add(vectorRadiusYProperty()));
                arc.xProperty().bind(target.layoutXProperty().add(target.getBoundsInLocal().getMaxX() / 2).subtract(vectorRadiusXProperty()));
                arc.yProperty().bind(target.layoutYProperty().add(target.getBoundsInLocal().getMaxY() / 2).subtract(vectorRadiusYProperty()));

                arc.setRadiusX(130);
                arc.setRadiusY(100);

                path.getElements().add(moveTo);
                path.getElements().add(arc);

                getChildren().add(path);
                setOnMousePressed(event -> path.setStroke(Color.RED));
                setOnMouseReleased(event -> path.setStroke(Color.BLACK));
                break;

            case LOOP:

                break;

            default:
                throw new UnsupportedOperationException("Unsupported type: " + type);
        }

        distanceABProperty().bind(calcProperty());
        vectorABXProperty().bind(target.xProperty().subtract(source.xProperty()));
        vectorABYProperty().bind(target.yProperty().subtract(source.yProperty()));
        vectorRadiusXProperty().bind(calcProperty().add(vectorABXProperty().multiply(25.0)).divide(distanceABProperty()));
        vectorRadiusYProperty().bind(calcProperty().add(vectorABYProperty().multiply(25.0)).divide(distanceABProperty()));

        target.boundsInParentProperty().addListener((observable, oldValue, newValue) -> calcProperty().setValue(source.getPosition().distance(target.getPosition())));
        source.boundsInParentProperty().addListener((observable, oldValue, newValue) -> calcProperty().setValue(source.getPosition().distance(target.getPosition())));

    }

    public Cell getSource() {
        return source;
    }

    public Cell getTarget() {
        return target;
    }

    public DoubleProperty vectorABXProperty() {
        return vectorABX;
    }

    public DoubleProperty vectorABYProperty() {
        return vectorABY;
    }

    public DoubleProperty vectorRadiusXProperty() {
        return vectorRadiusX;
    }

    public DoubleProperty vectorRadiusYProperty() {
        return vectorRadiusY;
    }

    public DoubleProperty distanceABProperty() { return distanceAB; }

    public DoubleProperty calcProperty() {
        return calc;
    }

}