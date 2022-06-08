package treewidth_approximation.logic.tree_decomposition;

public interface TreeDecomposition {
    DecompositionNode getRoot();

    /**
     * Reduces amount of bags to be O(kn), where k is the bag size.
     * Does not necessarily return irreducible decomposition.
     */
    void collapse();
}
