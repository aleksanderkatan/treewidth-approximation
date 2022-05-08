package treewidth_approximation.logic.tree_decomposition;

import java.util.Set;

public interface DecompositionNode {
    Set<Integer> getVertices();

    Set<DecompositionNode> getChildren();
    void addChild(DecompositionNode child);
}
