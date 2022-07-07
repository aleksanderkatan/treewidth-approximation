package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;

import java.util.Set;

public class IntroduceEdgeNode extends NiceDecompositionNodeImpl {
    private final TAEdge edge;

    public IntroduceEdgeNode(Set<Integer> vertices, TAEdge edge) {
        super(vertices);
        this.edge = edge;
    }

    @Override
    public SubSolution computeSingular(SubProblem subProblem) {
        return null;

    }

    @Override
    public void updateSubgraph(TAGraph subgraph) {
        inducedSubgraph = subgraph.copyRestricting(Set.of());
        inducedSubgraph.addEdge(edge.getFirst(), edge.getSecond());
    }

    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - INTRODUCES EDGE " + edge.getFirst() + " " + edge.getSecond();
    }
}
