package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;

import java.util.Set;

public class JoinNode extends NiceDecompositionNodeImpl {
    @Override
    public void updateSubgraph(TAGraph subgraph) {
        inducedSubgraph = subgraph.copyRestricting(Set.of());
    }

    public JoinNode(Set<Integer> vertices) {
        super(vertices);
    }

    @Override
    public SubSolution computeSingular(SubProblem subProblem) {
        return null;

    }

    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - JOIN";
    }
}
