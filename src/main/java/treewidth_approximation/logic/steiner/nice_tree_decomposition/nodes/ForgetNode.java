package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.misc.Partition;
import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.NiceDecompositionNode;

import java.util.*;

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
        Set<Integer> X = subProblem.getX();
        Partition<Integer> partition = subProblem.getPartition();
        NiceDecompositionNode child = (NiceDecompositionNode)children.iterator().next();

        // if subProblem does have a terminal in X, return invalid solution
        for (int t : instance.getTerminals()) {
            if (X.contains(t)) {
                return SubSolution.getInvalidSolution();
            }
        }

        // forgotten was either in a block of partition or in X
        List<SubSolution> candidates = new ArrayList<>();

        // in X (can't be a terminal)
        if (!instance.getTerminals().contains(forgotten)) {
            Set<Integer> childX = new HashSet<>(X);
            childX.add(forgotten);
            SubProblem childSubProblemInX = new SubProblem(childX, new Partition<>(partition.getElements()));
            SubSolution childSubSolutionInX = child.getSolutions().getSolution(childSubProblemInX);
            if (childSubSolutionInX.isValid())
                candidates.add(new SubSolution(childSubSolutionInX.getCost(), null, List.of(childSubSolutionInX)));
        }

        // in partition
        for (int i = 0; i< partition.getAmountOfSets(); i++) {
            Map<Integer, Integer> newMap = new HashMap<>(partition.getElements());
            newMap.put(forgotten, i);
            Partition<Integer> childPartitionWithForgotten = new Partition<>(newMap);
            SubProblem childSubProblemWithForgotten = new SubProblem(new HashSet<>(X), childPartitionWithForgotten);
            SubSolution childSubSolutionWithForgotten = child.getSolutions().getSolution(childSubProblemWithForgotten);
            if (childSubSolutionWithForgotten.isValid())
                candidates.add(new SubSolution(childSubSolutionWithForgotten.getCost(), null,
                        List.of(childSubSolutionWithForgotten)));
        }

        SubSolution subSolution = SubSolution.getInvalidSolution();
        for (SubSolution candidate : candidates) {
            if (candidate.getCost() < subSolution.getCost()) {
                subSolution = candidate;
            }
        }
        return subSolution;
    }

    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - FORGETS " + forgotten;
    }
}
