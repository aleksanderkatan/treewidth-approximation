package treewidth_approximation.view;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.assignment.DataShapeAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Graph;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;
import treewidth_approximation.logic.graph.GraphConverter;
import treewidth_approximation.logic.graph.TAGraph;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GraphShower {
    public static void showGraph(TAGraph g, List<Set<Integer>> nodeColors, List<Set<Integer>> nodeShapes, String title) {
        if (nodeColors == null) nodeColors = new ArrayList<>();
        if (nodeShapes == null) nodeShapes = new ArrayList<>();
        // -- 1. setup graph and palette ------------------------------------------------
        Graph graph = GraphConverter.convert(g, nodeColors, nodeShapes);
        int[] basePalette = new int[] {
                ColorLib.rgb(0,0,0),  ColorLib.rgb(255, 63, 63),
                ColorLib.rgb(63, 255, 63), ColorLib.rgb(63,53,255),
                ColorLib.rgb(255, 255, 63), ColorLib.rgb(255, 63, 255),
                ColorLib.rgb(63,255,255), ColorLib.rgb(191, 191, 191),
        };
        int[] palette = new int[nodeColors.size()+1];
        for (int i = 0; i< nodeColors.size()+1; i++) {
            if (i < basePalette.length) {
                palette[i] = basePalette[i];
            } else {
                palette[i] = ColorLib.rgb(1 << (i), 1 << (2*i), 1 << (3*i));
            }
        }

        int[] baseShapes = new int[]
                { Constants.SHAPE_ELLIPSE, Constants.SHAPE_CROSS, Constants.SHAPE_DIAMOND,
                  Constants.SHAPE_HEXAGON, Constants.SHAPE_STAR, Constants.SHAPE_TRIANGLE_DOWN };
        int[] shapes = new int[nodeShapes.size()+1];
        for (int i = 0; i< nodeShapes.size()+1; i++) {
            if (i < baseShapes.length) {
                shapes[i] = baseShapes[i];
            } else {
                shapes[i] = Constants.SHAPE_RECTANGLE;
            }
        }


        SwingUtilities.invokeLater(() -> {
            // -- 2. the visualization --------------------------------------------
            Visualization vis = new Visualization();
            vis.add("graph", graph);
            vis.setInteractive("graph.edges", null, false);

            // -- 3. the renderers and renderer factory ---------------------------
//            ShapeRenderer nodeRenderer = new ShapeRenderer(10);
            EdgeRenderer edgeRenderer = new EdgeRenderer(Constants.EDGE_TYPE_LINE, Constants.EDGE_ARROW_NONE);
//            EdgeRenderer edgeR = new EdgeRenderer(prefuse.Constants.EDGE_TYPE_CURVE, prefuse.Constants.EDGE_ARROW_FORWARD);

            DefaultRendererFactory rendererFactory = new DefaultRendererFactory();
//            rendererFactory.setDefaultRenderer(nodeRenderer);
            rendererFactory.setDefaultEdgeRenderer(edgeRenderer);
            vis.setRendererFactory(rendererFactory);

            // -- 4. the processing actions ---------------------------------------
            DataColorAction fill = new DataColorAction("graph.nodes", "color",
                    Constants.NOMINAL, VisualItem.FILLCOLOR, palette);

            ColorAction edges = new ColorAction("graph.edges",
                    VisualItem.STROKECOLOR, ColorLib.gray(200));

            DataShapeAction shape = new DataShapeAction("graph.nodes", "shape", shapes);

            ActionList actionList = new ActionList();
            actionList.add(fill);
            actionList.add(edges);
            actionList.add(shape);

            // create an action list with an animated layout
            ActionList layout = new ActionList(Activity.INFINITY);
            layout.add(new ForceDirectedLayout("graph"));
            layout.add(new RepaintAction());

            // add the actions to the visualization
            vis.putAction("actionList", actionList);
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

            vis.run("actionList");
            vis.run("layout");
        });
    }
}
