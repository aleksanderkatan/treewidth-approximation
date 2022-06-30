package treewidth_approximation.logic.tree_decomposition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TreeDecompositionImpl implements TreeDecomposition {
    private final DecompositionNode root;

    public TreeDecompositionImpl(DecompositionNode root) { this.root = root; }

    @Override
    public DecompositionNode getRoot() { return root; }

    @Override
    public TreeDecomposition copy() {
        return new TreeDecompositionImpl(copyNode(root));
    }

    private DecompositionNode copyNode(DecompositionNode node) {
        DecompositionNode result = new DecompositionNodeImpl(node.getVertices());
        for (DecompositionNode child : new ArrayList<>(node.getChildren())) {
            result.addChild(copyNode(child));
        }
        return result;
    }
}
