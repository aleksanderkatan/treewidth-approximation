package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import org.javatuples.Pair;

import java.util.Set;

public class IntroduceEdgeNode extends NiceDecompositionNodeImpl {
    private Pair<Integer, IntroduceNode> edge;

    public IntroduceEdgeNode(Set<Integer> vertices, Pair<Integer, IntroduceNode> edge) {
        super(vertices);
        this.edge = edge;
    }

    @Override
    public void compute() {

    }
}
