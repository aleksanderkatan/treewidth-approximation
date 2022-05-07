package treewidth_approximation.view;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
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
import prefuse.render.DefaultRendererFactory;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.VisualItem;
import treewidth_approximation.logic.graph.GraphConverter;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;

import javax.swing.*;
import java.util.*;

public class GraphShower {
    public static void showGraph(TAGraph g, List<Set<Integer>> highlighted, String title) {
        if (highlighted == null) highlighted = new ArrayList<>();
        // -- 1. setup graph and palette ------------------------------------------------
        Graph graph = GraphConverter.convert(g, highlighted);
        int[] basePalette = new int[] {
                ColorLib.rgb(0,0,0),  ColorLib.rgb(255, 63, 63),
                ColorLib.rgb(63, 255, 63), ColorLib.rgb(63,53,255),
                ColorLib.rgb(255, 255, 63), ColorLib.rgb(255, 63, 255),
                ColorLib.rgb(63,255,255), ColorLib.rgb(191, 191, 191),
        };
        int[] palette = new int[highlighted.size()+1];
        for (int i = 0; i< highlighted.size()+1; i++) {
            if (i < basePalette.length) {
                palette[i] = basePalette[i];
            } else {
                palette[i] = ColorLib.rgb(1 << (i), 1 << (2*i), 1 << (3*i));
            }
        }


        SwingUtilities.invokeLater(() -> {
            // -- 2. the visualization --------------------------------------------
            Visualization vis = new Visualization();
            vis.add("graph", graph);
            vis.setInteractive("graph.edges", null, false);

            // -- 3. the renderers and renderer factory ---------------------------
            vis.setRendererFactory(new DefaultRendererFactory(new ShapeRenderer(10)));

            // -- 4. the processing actions ---------------------------------------

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
