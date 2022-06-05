package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import java.util.Set;

public class IntroduceNode extends NiceDecompositionNodeImpl{
    private final int node;

    public IntroduceNode(Set<Integer> vertices, int node) {
        super(vertices);
        this.node = node;
    }

    @Override
    public void compute() {

    }
}
