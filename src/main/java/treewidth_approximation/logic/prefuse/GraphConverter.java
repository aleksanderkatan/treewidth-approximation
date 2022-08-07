package treewidth_approximation.logic.prefuse;

import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GraphConverter {
    public static Graph convert(TAGraph tagraph,
                                Set<Integer> coloredVertices, Set<Integer> crossedVertices,
                                Set<TAEdge> highlightedEdges) {
        Graph res = getGraphBase();

        Node[] nodes = new Node[tagraph.getVertices().size()];
        for (int i = 0; i < tagraph.getVertices().size(); i++) {
            nodes[i] = res.addNode();
        }

        List<TAVertex> originalVertices = new ArrayList<>(tagraph.getVertices());
        for (TAVertex originalVertex : originalVertices) {
            Node node = nodes[originalVertex.getId()];
            // inverted since palettes are weird
            node.set("node_colored", coloredVertices.contains(originalVertex.getId()) ? 0 : 1);
            node.set("node_crossed", crossedVertices.contains(originalVertex.getId()) ? 0 : 1);
            node.set("node_label", Integer.toString(originalVertex.getId()));
        }

        for (TAVertex v : originalVertices) {
            for (TAVertex w : v.getNeighbours()) {
                int id1 = v.getId();
                int id2 = w.getId();
                if (id1 < id2) {
                    res.addEdge(id1, id2);
                    Edge e = res.getEdge(nodes[id1], nodes[id2]);
                    //                    // inverted since palettes are weird
                    e.set("edge_highlighted", highlightedEdges.contains(new TAEdge(id1, id2)) ? 0 : 1);
                    e.set(Graph.DEFAULT_SOURCE_KEY, id1);
                    e.set(Graph.DEFAULT_TARGET_KEY, id2);
                }
            }
        }
        return res;
    }

    public static Graph getGraphBase() {
        Table nodeTable = new Table();

        //        nodeTable.addColumn(Graph.DEFAULT_NODE_KEY, int.class );
        nodeTable.addColumn("node_colored", Integer.class);
        nodeTable.addColumn("node_crossed", Integer.class);
        nodeTable.addColumn("node_label", String.class);

        Table edgeTable = new Table();

        edgeTable.addColumn(Graph.DEFAULT_SOURCE_KEY, int.class);
        edgeTable.addColumn(Graph.DEFAULT_TARGET_KEY, int.class);
        edgeTable.addColumn("edge_highlighted", Integer.class);

        return new Graph(nodeTable, edgeTable, false);
    }
}
