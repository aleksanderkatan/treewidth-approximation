package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.steiner.SubProblem;

import java.util.Set;

public class JoinNode extends NiceDecompositionNodeImpl {
    public JoinNode(Set<Integer> vertices) {
        super(vertices);
    }

    @Override
    public void computeSingular(SubProblem subProblem) {

    }

    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - JOIN";
    }
}
