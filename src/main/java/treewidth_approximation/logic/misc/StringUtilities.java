package treewidth_approximation.logic.misc;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.tree_decomposition.DecompositionNode;

import java.util.Set;

public class StringUtilities {
    public static String setToString(Set<Integer> set) {
        StringBuilder s = new StringBuilder();
        for (Integer id : set) {
            s.append(id).append(", ");
        }
        s.delete(s.length()-3, s.length()-1);
        return new String(s);
    }

    public static String getNodeLabel(DecompositionNode node) {
        return setToString(node.getVertices());
    }

    public static String graphToString(TAGraph g) {
        StringBuilder s = new StringBuilder();

        for (TAVertex v : g.getVertices()) {
            s.append(v.getId()).append(" : ");
            s.append(setToString(v.getNeighboursIds()));
            s.append("\n");
        }

        return new String(s);
    }
}
