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
import prefuse.render.LabelRenderer;
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
    public static void showGraphWithIds(TAGraph g, List<Set<Integer>> nodeColors, String title) {
        showGraph(g, nodeColors, null, title);
    }

    public static void showGraphWithShapes(TAGraph g, List<Set<Integer>> nodeColors, List<Set<Integer>> nodeShapes, String title) {
        if (nodeShapes == null) nodeShapes = new ArrayList<>();
        showGraph(g, nodeColors, nodeShapes, title);
    }

    private static void showGraph(TAGraph g, List<Set<Integer>> nodeColors, List<Set<Integer>> nodeShapes, String title) {
        if (nodeColors == null) nodeColors = new ArrayList<>();
        // no nodeShapes => write labels instead
        final boolean writeLabels = nodeShapes == null;
        if (nodeShapes == null) {
            nodeShapes = new ArrayList<>();
        }

        // -- 1. setup graph and palette ------------------------ ------------------------
        Graph graph = GraphConverter.convert(g, nodeColors, nodeShapes);
        int[] palette = getPalette(nodeColors.size()+1);
        int[] shapes = getShapes(nodeShapes.size()+1);

        SwingUtilities.invokeLater(() -> {
            // -- 2. the visualization --------------------------------------------
            Visualization vis = new Visualization();
            vis.add("graph", graph);
            vis.setInteractive("graph.edges", null, false);

            // -- 3. the renderers and renderer factory ---------------------------
            DefaultRendererFactory rendererFactory = new DefaultRendererFactory();
            EdgeRenderer edgeRenderer = new EdgeRenderer(Constants.EDGE_TYPE_LINE, Constants.EDGE_ARROW_NONE);
            rendererFactory.setDefaultEdgeRenderer(edgeRenderer);

            if (writeLabels) {
                LabelRenderer labelRenderer = new LabelRenderer("label");
                labelRenderer.setRoundedCorner(8, 8);
                rendererFactory.setDefaultRenderer(labelRenderer);
            }

            vis.setRendererFactory(rendererFactory);

            // -- 4. the processing actions ---------------------------------------
            ActionList colors = new ActionList();

            DataColorAction fill = new DataColorAction("graph.nodes", "color",
                    Constants.NOMINAL, VisualItem.FILLCOLOR, palette);
            colors.add(fill);

            ColorAction edges = new ColorAction("graph.edges",
                    VisualItem.STROKECOLOR, ColorLib.gray(200));
            colors.add(edges);

            if (writeLabels) {
                ColorAction text = new ColorAction("graph.nodes",
                        VisualItem.TEXTCOLOR, ColorLib.gray(200));
                colors.add(text);
            } else {
                DataShapeAction shape = new DataShapeAction("graph.nodes", "shape", shapes);
                colors.add(shape);
            }

            ActionList layout = new ActionList(Activity.INFINITY);
            layout.add(new ForceDirectedLayout("graph"));
            layout.add(new RepaintAction());

            vis.putAction("colors", colors);
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

            vis.run("colors");
            vis.run("layout");
        });
    }

    private static int[] getPalette(int size) {
        int[] basePalette = new int[] {
                ColorLib.rgb(0,0,0),  ColorLib.rgb(255, 63, 63),
                ColorLib.rgb(63, 255, 63), ColorLib.rgb(63,53,255),
                ColorLib.rgb(255, 255, 63), ColorLib.rgb(255, 63, 255),
                ColorLib.rgb(63,255,255), ColorLib.rgb(191, 191, 191),
        };
        int[] palette = new int[size];
        for (int i = 0; i< size; i++) {
            if (i < basePalette.length) {
                palette[i] = basePalette[i];
            } else {
                palette[i] = ColorLib.rgb(1 << (i), 1 << (2*i), 1 << (3*i));
            }
        }
        return palette;
    }

    private static int[] getShapes(int size) {
        int[] baseShapes = new int[]
                { Constants.SHAPE_ELLIPSE, Constants.SHAPE_CROSS, Constants.SHAPE_DIAMOND,
                        Constants.SHAPE_HEXAGON, Constants.SHAPE_STAR, Constants.SHAPE_TRIANGLE_DOWN };
        int[] shapes = new int[size];
        for (int i = 0; i< size; i++) {
            if (i < baseShapes.length) {
                shapes[i] = baseShapes[i];
            } else {
                shapes[i] = Constants.SHAPE_RECTANGLE;
            }
        }
        return shapes;
    }
}
