package com.fxgraph.layout.random;

import com.fxgraph.graph.Cell;
import com.fxgraph.graph.Graph;
import com.fxgraph.layout.base.Layout;

import java.util.List;

public class PositionLayout extends Layout {

    Graph graph;


    double x;
    double y;

    public PositionLayout(Graph graph) {

        this.graph = graph;

    }

    public void execute() {

        List<Cell> cells = graph.getModel().getAllCells();

        for (Cell cell : cells) {

            x = cell.xProperty().getValue();
            y = cell.yProperty().getValue();
            cell.relocate(x, y);
            System.out.println("Cell layout: " + cell.getCellId() +" X :"+cell.getX()+" Y :"+cell.getY());
        }

    }

}