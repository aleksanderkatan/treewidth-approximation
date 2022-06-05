package treewidth_approximation.logic.steiner.nice_tree_decomposition;

import org.w3c.dom.Node;
import treewidth_approximation.logic.steiner.SteinerInstance;
import treewidth_approximation.logic.tree_decomposition.DecompositionNode;
import treewidth_approximation.logic.tree_decomposition.TreeDecomposition;

public class NiceTreeDecompositionGenerator {

    public static NiceTreeDecomposition generate(TreeDecomposition decomposition, SteinerInstance instance) {
        // terminal to be added to every decomposition node
        int U = instance.getTerminals().iterator().next();

        // ensure there is a right amount of nodes
        decomposition.collapse();

        return new NiceTreeDecomposition(generate(decomposition.getRoot()));
    }

    private static NiceDecompositionNode generate(DecompositionNode node) {
//        if (node.getChildren().size() == 1) {
//            DecompositionNode
//        }
        return null;
    }
}
