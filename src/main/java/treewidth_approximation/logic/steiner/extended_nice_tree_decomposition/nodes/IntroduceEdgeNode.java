package treewidth_approximation.logic.steiner.extended_nice_tree_decomposition.nodes;

import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.misc.Partition;
import treewidth_approximation.logic.misc.SubsetExecutor;
import treewidth_approximation.logic.misc.serialization.StringWriter;
import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;
import treewidth_approximation.logic.steiner.extended_nice_tree_decomposition.NiceDecompositionNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IntroduceEdgeNode extends NiceDecompositionNodeImpl {
    private final TAEdge edge;

    public IntroduceEdgeNode(Set<Integer> vertices, TAEdge edge) {
        super(vertices);
        this.edge = edge;
    }

    @Override
    public SubSolution computeSingular(SubProblem subProblem) {
        Set<Integer> X = subProblem.getX();
        Partition<Integer> partition = subProblem.getPartition();
        NiceDecompositionNode child = (NiceDecompositionNode) children.iterator().next();
        int u = edge.getFirst();
        int v = edge.getSecond();

        // if subProblem does have a terminal in X, return invalid solution
        for (int t : instance.getTerminals()) {
            if (X.contains(t)) {
                return SubSolution.getInvalidSolution();
            }
        }

        // if either is in X
        // or both are in separate blocks of partition
        // edge cannot be used, return result from child
        if (X.contains(u) || X.contains(v) || (partition.getSetIndexOfElement(u) != partition.getSetIndexOfElement(v))) {
            // subProblem is exactly the same
            SubSolution childSubSolution = child.getSolutions().getSolution(subProblem);
            return new SubSolution(childSubSolution.getCost(), null, List.of(childSubSolution));
        }
        // they are in the same partition
        // either we use new edge, or we do not

        List<SubSolution> candidates = new ArrayList<>();

        // did not use
        SubSolution childSubSolution = child.getSolutions().getSolution(subProblem);
        if (childSubSolution.isValid()) {candidates.add(new SubSolution(childSubSolution.getCost(), null, List.of(childSubSolution)));}

        // did use
        // for each subset of block \ {u, v}, check partition u+subset, v+rest
        Set<Integer> rest = new HashSet<>(partition.getSet(partition.getSetIndexOfElement(u)));
        rest.remove(u);
        rest.remove(v);
        SubsetExecutor<Integer, Boolean> subsetExecutor = new SubsetExecutor<>(new ArrayList<>(rest), subset -> {
            Set<Integer> USet = new HashSet<>(subset);
            USet.add(u);
            Set<Integer> VSet = new HashSet<>(rest);
            VSet.removeAll(USet);
            VSet.add(v);

            Partition<Integer> temp = partition.copyRestrictingSet(partition.getSetIndexOfElement(u));
            Map<Integer, Integer> newMap = temp.getElements();
            int uIndex = temp.getAmountOfSets();
            for (int uElem : USet) {
                newMap.put(uElem, uIndex);
            }
            int vIndex = uIndex + 1;
            for (int vElem : VSet) {
                newMap.put(vElem, vIndex);
            }
            Partition<Integer> childPartition = new Partition<>(newMap);
            SubProblem childSubProblem = new SubProblem(X, childPartition);
            SubSolution childSubSolution1 = child.getSolutions().getSolution(childSubProblem);
            if (childSubSolution1.isValid()) {candidates.add(new SubSolution(childSubSolution1.getCost() + instance.getEdgeWeight(edge), edge, List.of(childSubSolution1)));}
            return null;
        });

        subsetExecutor.run();
        SubSolution subSolution = SubSolution.getInvalidSolution();
        for (SubSolution candidate : candidates) {
            if (candidate.getCost() < subSolution.getCost()) {
                subSolution = candidate;
            }
        }
        return subSolution;
    }

    @Override
    public void updateSubgraph(TAGraph subgraph) {
        inducedSubgraph = subgraph.copyRestricting(Set.of());
        inducedSubgraph.addEdge(edge.getFirst(), edge.getSecond());
    }

    @Override
    public String getLabel() {
        return StringWriter.writeSet(vertices) + " - INTRODUCES EDGE " + edge.getFirst() + " " + edge.getSecond();
    }
}
