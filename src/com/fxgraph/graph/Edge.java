/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fxgraph.graph;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Edge extends Group {

    private Cell source;
    private Cell target;

    private Line line;
    private Path path;
    private Polygon triangle;

    private DoubleProperty distanceAB = new SimpleDoubleProperty();
    private DoubleProperty calc = new SimpleDoubleProperty();
    private DoubleProperty vectorABX = new SimpleDoubleProperty();
    private DoubleProperty vectorABY = new SimpleDoubleProperty();
    private DoubleProperty vectorRadiusX = new SimpleDoubleProperty();
    private DoubleProperty vectorRadiusY = new SimpleDoubleProperty();

    private DoubleProperty dx = new SimpleDoubleProperty();
    private DoubleProperty dy = new SimpleDoubleProperty();
    private DoubleProperty angle = new SimpleDoubleProperty();

    private DoubleBinding calculoDistancia;

    public Edge(Cell source, Cell target, EdgeType type) {
        calculoDistancia = new DoubleBinding() {
            @Override
            protected double computeValue() {
                return source.getPosition().distance(target.getPosition());
            }
        };
        source.boundsInParentProperty().addListener((observable, oldValue, newValue) -> {
            calcProperty().setValue(source.getPosition().distance(target.getPosition()));
            angleProperty().setValue((Math.atan2(dyProperty().getValue(), dxProperty().getValue()) * 180 / Math.PI));

        });

        target.boundsInParentProperty().addListener((observable, oldValue, newValue) -> {
            calcProperty().setValue(source.getPosition().distance(target.getPosition()));
            angleProperty().setValue((Math.atan2(dyProperty().getValue(), dxProperty().getValue()) * 180 / Math.PI));
        });

        this.source = source;
        this.target = target;

        switch (type) {

            case LINE:
                source.addCellChild(target);
                target.addCellParent(source);

                line = new Line();
                line.getStyleClass().add("line");
                line.setStrokeWidth(2);

                HBox g = new HBox();
                triangle = new Polygon(-5, -4, -5, 4, 5, 0);

                line.startXProperty().bind(source.layoutXProperty().add(source.getBoundsInLocal().getMaxX() / 2).add(vectorRadiusXProperty()));
                line.startYProperty().bind(source.layoutYProperty().add(source.getBoundsInLocal().getMaxY() / 2).add(vectorRadiusYProperty()));
                line.endXProperty().bind(target.layoutXProperty().add(target.getBoundsInLocal().getMaxX() / 1.9).subtract(vectorRadiusXProperty()));
                line.endYProperty().bind(target.layoutYProperty().add(target.getBoundsInLocal().getMaxY() / 1.9).subtract(vectorRadiusYProperty()));


                triangle.layoutXProperty().bind(target.layoutXProperty().add(target.getBoundsInLocal().getMaxX() / 1.9).subtract(vectorRadiusXProperty()));
                triangle.layoutYProperty().bind(target.layoutYProperty().add(target.getBoundsInLocal().getMaxY() / 1.9).subtract(vectorRadiusYProperty()));

                dxProperty().bind(line.endXProperty().subtract(line.startXProperty()));
                dyProperty().bind(line.endYProperty().subtract(line.startYProperty()));


                triangle.rotateProperty().bind(angleProperty());
                getChildren().addAll(line, triangle);
                break;

            case ARC:
                source.addCellChild(target);
                target.addCellParent(source);

                path = new Path();
                ArcTo arc = new ArcTo();
                MoveTo moveTo = new MoveTo();
                path.setStrokeWidth(2);

                triangle = new Polygon(-5, -4, -5, 4, 5, 0);
                moveTo.xProperty().bind(source.layoutXProperty().add(source.getBoundsInLocal().getMaxX() / 2).add(vectorRadiusXProperty()));
                moveTo.yProperty().bind(source.layoutYProperty().add(source.getBoundsInLocal().getMaxY() / 2).add(vectorRadiusYProperty()));
                arc.xProperty().bind(target.layoutXProperty().add(target.getBoundsInLocal().getMaxX() / 1.9).subtract(vectorRadiusXProperty()));
                arc.yProperty().bind(target.layoutYProperty().add(target.getBoundsInLocal().getMaxY() / 1.9).subtract(vectorRadiusYProperty()));

                arc.radiusXProperty().bind(distanceAB.multiply(1.1));
                arc.radiusYProperty().bind(distanceAB.multiply(1.1));


                dxProperty().bind(arc.xProperty().subtract(moveTo.xProperty()));
                dyProperty().bind(arc.yProperty().subtract(moveTo.yProperty()));

                triangle.layoutXProperty().bind(target.layoutXProperty().add(target.getBoundsInLocal().getMaxX() / 1.9).subtract(vectorRadiusXProperty()));
                triangle.layoutYProperty().bind(target.layoutYProperty().add(target.getBoundsInLocal().getMaxY() / 1.9).subtract(vectorRadiusYProperty()));

                triangle.rotateProperty().bind(angleProperty());

                path.getElements().add(moveTo);
                path.getElements().add(arc);
                triangle.rotateProperty().bind(angleProperty());


                getChildren().addAll(path, triangle);
                setOnMousePressed(event -> path.setStroke(Color.RED));
                setOnMouseReleased(event -> path.setStroke(Color.BLACK));
                break;

            case LOOP:
                source.addCellChild(target);
                target.addCellParent(source);

                path = new Path();
                LineTo lineto = new LineTo();
                MoveTo moveTo2 = new MoveTo();
                path.setStrokeWidth(2);


                triangle = new Polygon(-5, -4, -5, 4, 5, 0);
                moveTo2.xProperty().bind(source.layoutXProperty().add(source.getBoundsInLocal().getMaxX() / 2).add(vectorRadiusXProperty()));
                moveTo2.yProperty().bind(source.layoutYProperty().add(source.getBoundsInLocal().getMaxY() / 2).add(vectorRadiusYProperty()));
                lineto.xProperty().bind(target.layoutXProperty().add(target.getBoundsInLocal().getMaxX() / 1.9).subtract(vectorRadiusXProperty()));
                lineto.yProperty().bind(target.layoutYProperty().add(target.getBoundsInLocal().getMaxY() / 1.9).subtract(vectorRadiusYProperty()));

                triangle.layoutXProperty().bind(target.layoutXProperty().add(target.getBoundsInLocal().getMaxX() / 1.9).subtract(vectorRadiusXProperty()));
                triangle.layoutYProperty().bind(target.layoutYProperty().add(target.getBoundsInLocal().getMaxY() / 1.9).subtract(vectorRadiusYProperty()));

                triangle.rotateProperty().bind(angleProperty());

                dxProperty().bind(lineto.xProperty().subtract(moveTo2.xProperty()));
                dyProperty().bind(lineto.yProperty().subtract(moveTo2.yProperty()));

                path.getElements().add(moveTo2);
                path.getElements().add(lineto);
                getChildren().addAll(path, triangle);




                break;
            default:
                throw new UnsupportedOperationException("Unsupported type: " + type);
        }

        distanceABProperty().bind(calcProperty());
        vectorABXProperty().bind(target.xProperty().subtract(source.xProperty()));
        vectorABYProperty().bind(target.yProperty().subtract(source.yProperty()));
        vectorRadiusXProperty().bind(calcProperty().add(vectorABXProperty().multiply(25.0)).divide(distanceABProperty()));
        vectorRadiusYProperty().bind(calcProperty().add(vectorABYProperty().multiply(25.0)).divide(distanceABProperty()));

        target.boundsInParentProperty().addListener((observable, oldValue, newValue) -> {
            calcProperty().setValue(source.getPosition().distance(target.getPosition()));
        });
        source.boundsInParentProperty().addListener((observable, oldValue, newValue) -> {
            calcProperty().setValue(source.getPosition().distance(target.getPosition()));
        });
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

    public DoubleProperty distanceABProperty() {
        return distanceAB;
    }

    public DoubleProperty calcProperty() {
        return calc;
    }

    public DoubleProperty dxProperty() {
        return dx;
    }

    public DoubleProperty getDx() {
        return dx;
    }

    public void setDx(DoubleProperty dx) {
        this.dx = dx;
    }

    public DoubleProperty dyProperty() {
        return dy;
    }

    public DoubleProperty getDy() {
        return dy;
    }

    public void setDy(DoubleProperty dy) {
        this.dy = dy;
    }

    public DoubleProperty angleProperty() {
        return angle;
    }

    public DoubleProperty getAngle() {
        return angle;
    }

    public void setAngle(DoubleProperty angle) {
        this.angle = angle;
    }


}