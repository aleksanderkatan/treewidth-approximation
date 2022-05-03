package treewidth_approximation;

import prefuse.*;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Graph;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;
import treewidth_approximation.graph.GraphConverter;
import treewidth_approximation.graph.TAGraph;
import treewidth_approximation.graph.TAGraphImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class Program {
    public static void main(String[] args) {
        TAGraph g = new TAGraphImpl();
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addVertex(5);
        g.addVertex(6);
        g.addVertex(7);
        g.addVertex(8);
        g.addEdge(g.getVertexById(0), g.getVertexById(1));
        g.addEdge(g.getVertexById(1), g.getVertexById(2));
        g.addEdge(g.getVertexById(2), g.getVertexById(3));
        g.addEdge(g.getVertexById(3), g.getVertexById(4));
        g.addEdge(g.getVertexById(4), g.getVertexById(5));
        g.addEdge(g.getVertexById(5), g.getVertexById(6));
        g.addEdge(g.getVertexById(6), g.getVertexById(7));
        g.addEdge(g.getVertexById(7), g.getVertexById(8));
        showGraph(g, "graph");
    }

    public static void showGraph(TAGraph g, String title) {
        // -- 1. get the data ------------------------------------------------
        Graph graph = GraphConverter.convert(g);

        SwingUtilities.invokeLater(() -> {
            // -- 2. the visualization --------------------------------------------
            Visualization vis = new Visualization();
            vis.add("graph", graph);
            vis.setInteractive("graph.edges", null, false);

            // -- 3. the renderers and renderer factory ---------------------------
            vis.setRendererFactory(new DefaultRendererFactory(new ShapeRenderer(10)));

            // -- 4. the processing actions ---------------------------------------
            int[] palette = new int[] {
                    ColorLib.rgb(191, 191, 191), ColorLib.rgb(255, 63, 63),
                    ColorLib.rgb(63, 255, 63), ColorLib.rgb(63,53,255),
                    ColorLib.rgb(255, 255, 63), ColorLib.rgb(255, 63, 255),
                    ColorLib.rgb(63,255,255), ColorLib.rgb(0,0,0),
            };
            DataColorAction fill = new DataColorAction("graph.nodes", "type",
                    Constants.NOMINAL, VisualItem.FILLCOLOR, palette);

            ColorAction edges = new ColorAction("graph.edges",
                    VisualItem.STROKECOLOR, ColorLib.gray(200));

            ActionList color = new ActionList();
            color.add(fill);
            color.add(edges);

            // create an action list with an animated layout
            ActionList layout = new ActionList(Activity.INFINITY);
            layout.add(new ForceDirectedLayout("graph"));
            layout.add(new RepaintAction());

            // add the actions to the visualization
            vis.putAction("color", color);
            vis.putAction("layout", layout);

            // -- 5. the display and interactive controls -------------------------
            Display display = new Display(vis);
            display.addControlListener(new DragControl());
            display.addControlListener(new PanControl());
            display.addControlListener(new ZoomControl());

            // -- 6. launch the visualization -------------------------------------
            JFrame frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(display);
            frame.pack();           // layout components in window
            frame.setVisible(true); // show the window

            vis.run("color");
            vis.run("layout");
        });
    }
}