package treewidth_approximation.logic.prefuse;

import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import treewidth_approximation.logic.tree_decomposition.DecompositionNode;
import treewidth_approximation.logic.tree_decomposition.TreeDecomposition;

public class TreeDecompositionConverter {
    public static Graph convert(TreeDecomposition decomposition) {
        Graph res = GraphConverter.getGraphBase();

        generateGraph(res, decomposition.getRoot());

        return res;
    }

    private static Node generateGraph(Graph graph, DecompositionNode decompositionNode) {
        Node node = graph.addNode();
        // inverted because palettes
        node.set("node_colored", 1);
        node.set("node_crossed", 1);
        node.set("node_label", decompositionNode.getLabel());

        for (DecompositionNode childNode : decompositionNode.getChildren()) {
            Node child = generateGraph(graph, childNode);
            Edge e = graph.addEdge(node, child);
            // inverted since palettes are weird
            e.set("edge_highlighted", 1);
        }
        return node;
    }
}
