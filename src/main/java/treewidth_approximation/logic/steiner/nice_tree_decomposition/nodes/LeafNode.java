package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAGraphImpl;
import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;

import java.util.HashSet;

public class LeafNode extends NiceDecompositionNodeImpl {
    public LeafNode() {
        super(new HashSet<>());
    }

    @Override
    public void updateSubgraph(TAGraph subgraph) {
        inducedSubgraph = new TAGraphImpl();
        inducedSubgraph.addVertex(vertices.iterator().next());
    }

    @Override
    public SubSolution computeSingular(SubProblem subProblem) {
        return null;

    }

    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - LEAF";
    }
}
