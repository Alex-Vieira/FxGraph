package com.fxgraph.graph;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

public class Cell extends StackPane {

    private String cellId;
    private Label label = new Label("");
    private TextField textField = new TextField();
    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();
    private Point2D position;

    private List<Cell> children = new ArrayList<>();
    private List<Cell> parents = new ArrayList<>();

    private Node view;

    public Cell(String cellId, String name) {
        this.cellId = cellId;
        this.label.setText(name);

        textField.textProperty().bindBidirectional(label.textProperty());

        textField.prefHeightProperty().bind(label.heightProperty());
        textField.prefWidthProperty().bind(label.widthProperty());
        textField.setAlignment(Pos.CENTER);
        textField.setVisible(false);

        boundsInParentProperty().addListener((observable, oldValue, newValue) -> {
            position = new Point2D(xProperty().getValue(), yProperty().getValue());
        });

    }


    public TextField getTextField() {
        return textField;
    }

    public Label getLabel() {
        return label;
    }

    public void addCellChild(Cell cell) {
        children.add(cell);
    }

    public List<Cell> getCellChildren() {
        return children;
    }

    public void addCellParent(Cell cell) {
        parents.add(cell);
    }

    public List<Cell> getCellParents() {
        return parents;
    }

    public void removeCellChild(Cell cell) {
        children.remove(cell);
    }

    public void setView(Node view) {
        this.view = view;
        this.getChildren().addAll(view, label, textField);
    }

    public Node getView() {
        return this.view;
    }

    public String getCellId() {
        return cellId;
    }

    public DoubleProperty xProperty(){
        return getX();
    }
    public DoubleProperty getX() {
        return x;
    }

    public void setX(DoubleProperty x) {
        this.x = x;
    }

    public DoubleProperty yProperty(){
        return getY();
    }

    public DoubleProperty getY() {
        return y;
    }

    public void setY(DoubleProperty y) {
        this.y = y;
    }


    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }



}