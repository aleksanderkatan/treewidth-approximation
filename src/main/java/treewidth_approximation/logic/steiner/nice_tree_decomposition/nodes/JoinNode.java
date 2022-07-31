package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.misc.Partition;
import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.NiceDecompositionNode;
import treewidth_approximation.logic.tree_decomposition.DecompositionNode;

import java.util.*;

public class JoinNode extends NiceDecompositionNodeImpl {
    @Override
    public void updateSubgraph(TAGraph subgraph) {
        inducedSubgraph = subgraph.copyRestricting(Set.of());
    }

    public JoinNode(Set<Integer> vertices) {
        super(vertices);
    }

    @Override
    public void compute() {
        // this defaults all SubSolutions to invalid
        super.compute();

        List<DecompositionNode> notNiceChildren = new ArrayList<>(getChildren());
        List<NiceDecompositionNode> children = List.of((NiceDecompositionNode) notNiceChildren.get(0), (NiceDecompositionNode) notNiceChildren.get(1));
        // X to possible partitions of first and second child
        Map<Set<Integer>, List<Set<SubProblem>>> valid = new HashMap<>();

        for (int i = 0; i< 2; i++) {
            NiceDecompositionNode currentChild = children.get(i);

            for (var entry : currentChild.getSolutions().getMap().entrySet()) {
                SubProblem subProblem = entry.getKey();
                SubSolution subSolution = entry.getValue();
                Set<Integer> X = subProblem.getX();

                if (subSolution.isValid()) {
                    if (!valid.containsKey(X)) {
                        valid.put(X, List.of(new HashSet<>(), new HashSet<>()));
                    }
                    Set<SubProblem> current = valid.get(X).get(i);
                    current.add(subProblem);
                }
            }
        }

        for (var entry : valid.entrySet()) {
            Set<Integer> X = entry.getKey();
            Set<SubProblem> problems1 = entry.getValue().get(0);
            Set<SubProblem> problems2 = entry.getValue().get(1);


            for (var problem1 : problems1) {
                for (var problem2 : problems2) {
                    SubSolution subSolution1 = children.get(0).getSolutions().getSolution(problem1);
                    SubSolution subSolution2 = children.get(1).getSolutions().getSolution(problem2);
                    Partition<Integer> partition1 = problem1.getPartition();
                    Partition<Integer> partition2 = problem2.getPartition();

                    Partition<Integer> partition = partition1.combine(partition2);
                    SubProblem subProblem = new SubProblem(X, partition);
                    // no edges in this bag are yet introduced
                    SubSolution subSolution = new SubSolution(subSolution1.getCost() + subSolution2.getCost(), null, List.of(subSolution1, subSolution2));

                    SubSolution currentSubSolution = getSolutions().getSolution(subProblem);
                    if (currentSubSolution.getCost() > subSolution.getCost()) {
                        getSolutions().putSolution(subProblem, subSolution);
                    }
                }
            }
        }
    }

    @Override
    public SubSolution computeSingular(SubProblem subProblem) {
        return SubSolution.getInvalidSolution();
    }

    @Override
    public String getLabel() {
        return StringUtilities.setToString(vertices) + " - JOIN";
    }
}
