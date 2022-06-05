package treewidth_approximation.logic.prefuse;

import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.tree_decomposition.DecompositionNode;
import treewidth_approximation.logic.tree_decomposition.TreeDecomposition;

public class TreeDecompositionConverter {
    public static Graph convert(TreeDecomposition decomposition) {
        Table table = new Table();
        table.addColumn("color", Integer.class);
        table.addColumn("shape", Integer.class);
        table.addColumn("label", String.class);
        Graph res = new Graph(table, false);

        generateGraph(res, decomposition.getRoot());

        return res;
    }

    private static Node generateGraph(Graph graph, DecompositionNode decompositionNode) {
        Node node = graph.addNode();
        node.set("color", 0);
        node.set("shape", 0);
        node.set("label", decompositionNode.getLabel());

        for (DecompositionNode childNode : decompositionNode.getChildren()) {
            Node child = generateGraph(graph, childNode);
            graph.addEdge(node, child);
        }
        return node;
    }
}
