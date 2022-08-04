package treewidth_approximation.logic.steiner.extended_nice_tree_decomposition.nodes;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.misc.Partition;
import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;
import treewidth_approximation.logic.steiner.extended_nice_tree_decomposition.NiceDecompositionNode;

import java.util.HashSet;
import java.util.List;
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
        Set<Integer> X = subProblem.getX();
        Partition<Integer> partition = subProblem.getPartition();
        NiceDecompositionNode child = (NiceDecompositionNode)children.iterator().next();

        // if subProblem does have a terminal in X, return invalid solution
        for (int t : instance.getTerminals()) {
            if (X.contains(t)) {
                return SubSolution.getInvalidSolution();
            }
        }

        if (!X.contains(introduced)) {
            int setIndex = partition.getSetIndexOfElement(introduced);
            if (partition.getSet(setIndex).size() > 1) {
                // no edges are yet attached to introduced, therefore if it's in partition, it must be its own separate block
                return SubSolution.getInvalidSolution();
            } else {
                // introduced is in his set in partition
                Partition<Integer> childPartition = partition.copyRestrictingElement(introduced);
                SubProblem childSubProblem = new SubProblem(X, childPartition);
                SubSolution childSubSolution = child.getSolutions().getSolution(childSubProblem);
                return new SubSolution(childSubSolution.getCost(), null, List.of(childSubSolution));
            }
        }
        // introduced is not in partition
        Set<Integer> childX = new HashSet<>(X);
        childX.remove(introduced);
        SubProblem childSubProblem = new SubProblem(childX, partition);
        SubSolution childSubSolution = child.getSolutions().getSolution(childSubProblem);
        if (childSubSolution.isValid())
            return new SubSolution(childSubSolution.getCost(), null, List.of(childSubSolution));
        else
            return SubSolution.getInvalidSolution();
    }

    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - INTRODUCES " + introduced;
    }
}
