package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;

import java.util.Set;

public class ForgetNode extends NiceDecompositionNodeImpl {
    private final int forgotten;

    public ForgetNode(Set<Integer> vertices, int forgotten) {
        super(vertices);
        this.forgotten = forgotten;
    }

    @Override
    public void updateSubgraph(TAGraph subgraph) {
        inducedSubgraph = subgraph.copyRestricting(Set.of(forgotten));
    }

    @Override
    public SubSolution computeSingular(SubProblem subProblem) {
        return null;
    }

    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - FORGETS " + forgotten;
    }
}
