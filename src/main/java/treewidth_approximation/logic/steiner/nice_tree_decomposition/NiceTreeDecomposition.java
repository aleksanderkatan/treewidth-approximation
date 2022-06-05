package treewidth_approximation.logic.steiner.nice_tree_decomposition;

import treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes.NiceDecompositionNodeImpl;
import treewidth_approximation.logic.tree_decomposition.TreeDecomposition;

public class NiceTreeDecomposition {
    private final NiceDecompositionNode root;

    public NiceTreeDecomposition(NiceDecompositionNode root) {
        this.root = root;
    }

    public NiceDecompositionNode getRoot() {
        return root;
    }
}
