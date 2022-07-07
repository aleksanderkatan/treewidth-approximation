package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;

import java.util.Set;

public class IntroduceNode extends NiceDecompositionNodeImpl{
    private final int introduced;

    public IntroduceNode(Set<Integer> vertices, int introduced) {
        super(vertices);
        this.introduced = introduced;
    }

    @Override
    public void updateSubgraph(TAGraph subgraph) {
        inducedSubgraph = subgraph.copyRestricting(Set.of());
        inducedSubgraph.addVertex(introduced);
    }

    @Override
    public SubSolution computeSingular(SubProblem subProblem) {
        return null;

    }

    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - INTRODUCES " + introduced;
    }
}
