package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.misc.StringUtilities;

import java.util.Set;

public class JoinNode extends NiceDecompositionNodeImpl {
    public JoinNode(Set<Integer> vertices) {
        super(vertices);
    }

    @Override
    public void compute() {

    }


    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - JOIN";
    }
}
