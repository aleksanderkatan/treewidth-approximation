package treewidth_approximation.logic.separator_finder;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.misc.PartitionExecutor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SeparatorFinder {
    public static class NoSeparatorExistsException extends Exception {}

    /**
     *  finds a balanced separator for W of size order or smaller
     */
    public static Set<Integer> findSeparatorIds(TAGraph graph, Set<Integer> W, int order) throws NoSeparatorExistsException {

        int third = (W.size()+2)/3;
        List<Integer> limits = List.of(2*third, 2*third, order);

        PartitionExecutor<Integer, List<Integer>> executor =
                new PartitionExecutor<>(new ArrayList<>(W), 3, limits, (partition) -> {
                    List<Integer> A = partition.get(0);
                    List<Integer> B = partition.get(1);
                    List<Integer> C = partition.get(2);

                    // check sizes
                    if (A.size() * 2 < B.size()) return null;
                    if (B.size() * 2 < A.size()) return null;
                    if (C.size() > order) return null;

                    // check if there are edges between sets
                    for (Integer vid : A) {
                        TAVertex v = graph.getVertexById(vid);
                        for (TAVertex n : v.getNeighbours()) {
                            if (B.contains(n.getId()))
                                return null;
                        }
                    }

                    Set<Integer> ASet = new HashSet<>(A);
                    Set<Integer> BSet = new HashSet<>(B);
                    Set<Integer> CSet = new HashSet<>(C);

                    TAGraph newGraph = graph.copyRestricting(CSet);
                    FlowNetwork network = new FlowNetwork(newGraph, ASet, BSet);
                    int maxSeparatorSize = order - C.size();
                    int maxFlow = network.increaseCurrentFlow(maxSeparatorSize + 1);
                    if (maxFlow > maxSeparatorSize) return null;
                    List<Integer> result = new ArrayList<>(network.getSeparatorIds());
                    result.addAll(C);

                    return result;
                });

        List<Integer> ids = executor.run();
        if (ids == null) throw new NoSeparatorExistsException();

        return new HashSet<>(ids);
    }
}
