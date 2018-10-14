package application;

import com.fxgraph.graph.EdgeType;
import com.fxgraph.graph.Graph;
import com.fxgraph.graph.Model;
import com.fxgraph.layout.base.Layout;
import com.fxgraph.layout.random.PositionLayout;
import com.fxgraph.shape.fxCircle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private Graph graph = new Graph();
    private Model model;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        graph = new Graph();
        root.setCenter(graph.getScrollPane());
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("Application.css").toExternalForm());

        stage.setScene(scene);
        stage.show();

        model = graph.getModel();
        graph.beginUpdate();


        fxCircle circleA = new fxCircle("A", "Cell A");
        fxCircle circleB = new fxCircle("B", "Cell B");
        fxCircle circleC = new fxCircle("C", "Cell C");
        fxCircle circleD = new fxCircle("D", "Cell D");

        model.addCell(circleA);
        model.addCell(circleB);
        model.addCell(circleC);
        model.addEdge(circleA.getCellId(), circleB.getCellId(), EdgeType.LINE);
        model.addEdge(circleB.getCellId(), circleC.getCellId(), EdgeType.ARC);
        model.addEdge(circleC.getCellId(), circleA.getCellId(), EdgeType.LINE);


        root.getChildren().addAll(circleA, circleB);
        graph.endUpdate();
        Layout layout = new PositionLayout(graph);
        layout.execute();

    }

}
