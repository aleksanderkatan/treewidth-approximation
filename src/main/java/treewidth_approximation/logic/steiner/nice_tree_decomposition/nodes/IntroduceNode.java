package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.misc.StringUtilities;

import java.util.Set;

public class IntroduceNode extends NiceDecompositionNodeImpl{
    private final int introduced;

    public IntroduceNode(Set<Integer> vertices, int introduced) {
        super(vertices);
        this.introduced = introduced;
    }

    @Override
    public void compute() {

    }

    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - INTRODUCES " + introduced;
    }
}
