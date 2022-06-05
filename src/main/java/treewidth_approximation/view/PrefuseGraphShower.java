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
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.prefuse.GraphConverter;
import treewidth_approximation.logic.prefuse.SteinerInstanceConverter;
import treewidth_approximation.logic.prefuse.TreeDecompositionConverter;
import treewidth_approximation.logic.steiner.SteinerInstance;
import treewidth_approximation.logic.tree_decomposition.TreeDecomposition;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class PrefuseGraphShower {
    public static void showSteinerInstance(SteinerInstance instance, String title) {
        Graph graph = SteinerInstanceConverter.convert(instance);
        showGraph(graph, true, title);
    }

    public static void showTreeDecomposition(TreeDecomposition decomposition, String title) {
        Graph graph = TreeDecompositionConverter.convert(decomposition);
        showGraph(graph, true, title);
    }

    public static void showGraphWithIds(TAGraph g, Set<Integer> coloredNodes, String title) {
        Graph graph = GraphConverter.convert(g, coloredNodes, new HashSet<>(), new HashSet<>());
        showGraph(graph, true, title);
    }

    public static void showGraphWithShapes(TAGraph g, Set<Integer> coloredNodes, Set<Integer> crossedNodes, String title) {
        Graph graph = GraphConverter.convert(g, coloredNodes, crossedNodes, new HashSet<>());
        showGraph(graph, false, title);
    }


    private static void showGraph(Graph graph, boolean writeNodeLabels, String title) {
        // special, default
        int[] baseShapes = new int[]
                { Constants.SHAPE_CROSS, Constants.SHAPE_ELLIPSE };

        // special, default
        int[] nodePalette = new int[]
                { ColorLib.rgb(255, 20, 20), ColorLib.gray(25) };

        // special, default
        int[] edgePalette = new int[]
                { ColorLib.rgb(255, 20, 20), ColorLib.gray(25) };

        SwingUtilities.invokeLater(() -> {
            Visualization vis = new Visualization();
            vis.add("graph", graph);
            vis.setInteractive("graph.edges", null, false);

            DefaultRendererFactory rendererFactory = new DefaultRendererFactory();
            EdgeRenderer edgeRenderer = new EdgeRenderer(Constants.EDGE_TYPE_LINE, Constants.EDGE_ARROW_NONE);
            edgeRenderer.setDefaultLineWidth(2);
            rendererFactory.setDefaultEdgeRenderer(edgeRenderer);

            if (writeNodeLabels) {
                LabelRenderer labelRenderer = new LabelRenderer("node_label");
                labelRenderer.setRoundedCorner(8, 8);
                rendererFactory.setDefaultRenderer(labelRenderer);
            }

            vis.setRendererFactory(rendererFactory);

            ActionList colors = new ActionList();

            DataColorAction nodeFill = new DataColorAction("graph.nodes", "node_colored",
                    Constants.NOMINAL, VisualItem.FILLCOLOR, nodePalette);

            DataColorAction edgeFill = new DataColorAction("graph.edges", "edge_highlighted",
                    Constants.NOMINAL, VisualItem.STROKECOLOR, edgePalette);

            colors.add(nodeFill);
            colors.add(edgeFill);

            if (writeNodeLabels) {
                ColorAction text = new ColorAction("graph.nodes",
                        VisualItem.TEXTCOLOR, ColorLib.gray(200));
                colors.add(text);
            } else {
                DataShapeAction shape = new DataShapeAction("graph.nodes", "node_crossed", baseShapes);
                colors.add(shape);
            }

            ActionList layout = new ActionList(Activity.INFINITY);
            layout.add(new ForceDirectedLayout("graph"));
            layout.add(new RepaintAction());

            vis.putAction("colors", colors);
            vis.putAction("layout", layout);

            Display display = new Display(vis);
            display.addControlListener(new DragControl());
            display.addControlListener(new PanControl());
            display.addControlListener(new ZoomControl());

            JFrame frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(display);
            frame.pack();
            frame.setVisible(true);

            vis.run("colors");
            vis.run("layout");
        });
    }
}
