package treewidth_approximation.view;

import com.google.common.graph.Network;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.layout.algorithms.LayoutAlgorithm;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.layout.algorithms.FRLayoutAlgorithm;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.jung.GraphConverter;
import treewidth_approximation.logic.jung.JungEdge;
import treewidth_approximation.logic.jung.JungVertex;

import javax.swing.*;
import javax.xml.transform.Transformer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JungGraphShower {
    public static void showGraphWithIds(TAGraph g, String title) {
        Graph<JungVertex, JungEdge> graph = GraphConverter.convert(g);

//        // The Layout<V, E> is parameterized by the vertex and edge types
//        Layout<Integer, String> layout = new CircleLayout(g);
//        layout.setSize(new Dimension(300,300));
//        BasicVisualizationServer<Integer,String> vv =
//                new BasicVisualizationServer<Integer,String>(layout);
//        vv.setPreferredSize(new Dimension(350,350));
//        // Setup up a new vertex to paint transformer...
//        Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
//            public Paint transform(Integer i) {
//                return Color.GREEN;
//            }
//        };
//        // Set up a new stroke Transformer for the edges
//        float dash[] = {10.0f};
//        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
//                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
//        Transformer<String, Stroke> edgeStrokeTransformer =
//                new Transformer<String, Stroke>() {
//                    public Stroke transform(String s) {
//                        return edgeStroke;
//                    }
//                };
//        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
//        vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
//        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
//        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
//        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
//
//        JFrame frame = new JFrame("Simple Graph View 2");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(vv);
//        frame.pack();
//        frame.setVisible(true);

    }
}
