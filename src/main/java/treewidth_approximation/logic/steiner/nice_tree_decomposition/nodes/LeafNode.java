package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAGraphImpl;
import treewidth_approximation.logic.misc.Partition;
import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;

import java.util.*;

import static java.util.Map.entry;

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
        // this is never used, compute is overriden
        return null;
    }

    @Override
    public void compute() {
        int U = vertices.iterator().next();

        Partition<Integer> partition = new Partition<>(Map.ofEntries(
                entry(U, 0)
        ));
        Set<Integer> X = Set.of();
        SubProblem subProblem = new SubProblem(X, partition);
        SubSolution subSolution = new SubSolution(0, null, List.of());

        solution.putSolution(subProblem, subSolution);

        partition = new Partition<>(Map.ofEntries());
        X = Set.of(U);
        subProblem = new SubProblem(X, partition);
        subSolution = SubSolution.getInvalidSolution();

        solution.putSolution(subProblem, subSolution);
    }

    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - LEAF";
    }
}
