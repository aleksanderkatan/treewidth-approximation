package treewidth_approximation.logic.tree_decomposition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TreeDecompositionImpl implements TreeDecomposition {
    private final DecompositionNode root;

    public TreeDecompositionImpl(DecompositionNode root) { this.root = root; }
    public DecompositionNode getRoot() { return root; }

    @Override
    public void collapse() {
        collapseNode(root);
    }

    private void collapseNode(DecompositionNode node) {
        for (DecompositionNode child : new ArrayList<>(node.getChildren())) {
            collapseNode(child);

            if (node.getVertices().containsAll(child.getVertices())) {
                node.removeChild(child);
                for (DecompositionNode child2 : child.getChildren()) {
                    node.addChild(child2);
                }
            }
        }
    }
}
