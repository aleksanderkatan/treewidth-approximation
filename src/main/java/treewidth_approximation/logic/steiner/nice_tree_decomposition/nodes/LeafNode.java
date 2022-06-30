package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.misc.StringUtilities;

import java.util.HashSet;
import java.util.Set;

public class LeafNode extends NiceDecompositionNodeImpl {
    public LeafNode() {
        super(new HashSet<>());
    }

    @Override
    public void compute() {

    }

    @Override
    public String getLabel() {
        return "LEAF";
    }
}
