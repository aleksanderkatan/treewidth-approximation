package treewidth_approximation.logic.tree_decomposition;

public interface TreeDecomposition {
    DecompositionNode getRoot();
    void collapse();
}
