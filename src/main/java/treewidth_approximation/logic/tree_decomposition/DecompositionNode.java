package treewidth_approximation.logic.tree_decomposition;

import java.util.Set;

public interface DecompositionNode {
    Set<Integer> getVertices();

    String getLabel();

    Set<DecompositionNode> getChildren();

    void addChild(DecompositionNode child);

    void removeChild(DecompositionNode child);
}
