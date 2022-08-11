package treewidth_approximation.logic.tree_decomposition;

import java.util.ArrayList;

public class BasicTreeDecomposition implements TreeDecomposition {
    private final DecompositionNode root;

    public BasicTreeDecomposition(DecompositionNode root) {this.root = root;}

    @Override
    public DecompositionNode getRoot() {return root;}

    @Override
    public TreeDecomposition copy() {
        return new BasicTreeDecomposition(copyNode(root));
    }

    private DecompositionNode copyNode(DecompositionNode node) {
        DecompositionNode result = new BasicDecompositionNode(node.getVertices());
        for (DecompositionNode child : new ArrayList<>(node.getChildren())) {
            result.addChild(copyNode(child));
        }
        return result;
    }
}
