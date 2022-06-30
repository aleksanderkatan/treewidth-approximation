package treewidth_approximation.logic.steiner.nice_tree_decomposition;

import jdk.jshell.spi.ExecutionControl;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes.NiceDecompositionNodeImpl;
import treewidth_approximation.logic.tree_decomposition.TreeDecomposition;

public class NiceTreeDecomposition implements TreeDecomposition {
    private final NiceDecompositionNode root;

    public NiceTreeDecomposition(NiceDecompositionNode root) {
        this.root = root;
    }

    public NiceDecompositionNode getRoot() {
        return root;
    }

    @Override
    public TreeDecomposition copy() {
        throw new RuntimeException("Method copy not yet implemented");
    }
}
