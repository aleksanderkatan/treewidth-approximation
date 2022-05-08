package treewidth_approximation.logic.tree_decomposition;

public class TreeDecompositionImpl implements TreeDecomposition {
    private final DecompositionNode root;

    public TreeDecompositionImpl(DecompositionNode root) { this.root = root; }
    public DecompositionNode getRoot() { return root; }
}
