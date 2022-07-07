package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.steiner.SubProblem;

import java.util.HashSet;
import java.util.Set;

public class LeafNode extends NiceDecompositionNodeImpl {
    public LeafNode() {
        super(new HashSet<>());
    }

    @Override
    public void computeSingular(SubProblem subProblem) {

    }

    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - LEAF";
    }
}
