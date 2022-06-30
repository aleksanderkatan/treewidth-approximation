package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.misc.StringUtilities;

import java.util.Set;

public class ForgetNode extends NiceDecompositionNodeImpl {
    private final int forgotten;

    public ForgetNode(Set<Integer> vertices, int forgotten) {
        super(vertices);
        this.forgotten = forgotten;
    }

    @Override
    public void compute() {

    }

    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - FORGETS " + forgotten;
    }
}
