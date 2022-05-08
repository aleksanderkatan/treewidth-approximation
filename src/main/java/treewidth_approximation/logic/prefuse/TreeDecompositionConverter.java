package treewidth_approximation.logic.prefuse;

import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.tree_decomposition.DecompositionNode;
import treewidth_approximation.logic.tree_decomposition.TreeDecomposition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

        StringBuilder s = new StringBuilder();
        for (Integer id : decompositionNode.getVertices()) {
            s.append(id).append(", ");
        }
        s.delete(s.length()-3, s.length()-1);
        node.set("label", new String(s));

        for (DecompositionNode childNode : decompositionNode.getChildren()) {
            Node child = generateGraph(graph, childNode);
            graph.addEdge(node, child);
        }
        return node;
    }
}
