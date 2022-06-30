package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import org.javatuples.Pair;
import treewidth_approximation.logic.misc.StringUtilities;

import java.util.Set;

public class IntroduceEdgeNode extends NiceDecompositionNodeImpl {
    private final Pair<Integer, IntroduceNode> edge;

    public IntroduceEdgeNode(Set<Integer> vertices, Pair<Integer, IntroduceNode> edge) {
        super(vertices);
        this.edge = edge;
    }

    @Override
    public void compute() {

    }


    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - INTRODUCES EDGE " + edge.getValue0() + " " + edge.getValue1();
    }
}
