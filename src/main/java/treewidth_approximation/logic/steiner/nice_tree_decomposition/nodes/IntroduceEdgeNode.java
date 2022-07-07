package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.steiner.SubProblem;

import java.util.Set;

public class IntroduceEdgeNode extends NiceDecompositionNodeImpl {
    private final TAEdge edge;

    public IntroduceEdgeNode(Set<Integer> vertices, TAEdge edge) {
        super(vertices);
        this.edge = edge;
    }

    @Override
    public void computeSingular(SubProblem subProblem) {

    }


    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - INTRODUCES EDGE " + edge.getFirst() + " " + edge.getSecond();
    }
}
