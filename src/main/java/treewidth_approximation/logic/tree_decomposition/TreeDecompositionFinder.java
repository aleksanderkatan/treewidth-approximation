package treewidth_approximation.logic.tree_decomposition;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.separator_finder.SeparatorFinder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class TreeDecompositionFinder {
    public static class Result {
        public boolean successful;
        public TreeDecomposition decomposition;
        public Set<Integer> setWithoutSeparator;
    }

    private final TAGraph originalGraph;

    public TreeDecompositionFinder(TAGraph g) {
        this.originalGraph = g;
    }

    private void extendSet(Set<Integer> setToExtend, Set<Integer> setExtendWith, int size) {
        List<Integer> listExtendWith = new ArrayList<>(setExtendWith);
        listExtendWith.removeAll(setToExtend);
        listExtendWith = listExtendWith.subList(0, max(min(size - setToExtend.size(), listExtendWith.size()), 0));
        setToExtend.addAll(listExtendWith);
    }

    public Result findDecomposition(int actualTreeWidth) {
        int bagSize = actualTreeWidth + 1;
        int minWSize = bagSize + 2;
        DecompositionNode root = new DecompositionNodeImpl(new HashSet<>());

        for (TAGraph subgraph : originalGraph.splitIntoConnectedComponents()) {
            Set<Integer> W = new HashSet<>();
            extendSet(W, subgraph.getVerticesIds(), 3*bagSize);
            Result r = find(subgraph, W, bagSize, minWSize, 4*bagSize);
            if (!r.successful) return r;
            root.addChild(r.decomposition.getRoot());
        }
        Result r = new Result();
        r.successful = true;
        r.decomposition = new TreeDecompositionImpl(root);
        return r;
    }

    private Result find(TAGraph graph, Set<Integer> W, int maxSeparatorSize, int minWSize, int maxBagSize) {
        Result result = new Result();
        result.successful = false;
        result.decomposition = null;
        result.setWithoutSeparator = new HashSet<>();

        // if graph is small enough pack it into a single bag
        if (graph.getVertices().size() <= maxBagSize) {
            Set<Integer> bag = graph.getVerticesIds();
            DecompositionNode node = new DecompositionNodeImpl(bag);
            TreeDecomposition decomposition = new TreeDecompositionImpl(node);
            result.successful = true;
            result.decomposition = decomposition;
            return result;
        }

        Set<Integer> separator;
        try {
            separator = SeparatorFinder.findSeparatorIds(graph, W, maxSeparatorSize);
        } catch (SeparatorFinder.NoSeparatorExistsException e) {
            // if no separator exists, in the entire graph it will also not exist
            result.setWithoutSeparator = new HashSet<>(W);
            return result;
        }
        List<TAGraph> components = graph.copyRestricting(separator).splitIntoConnectedComponents();
        List<TreeDecomposition> decompositions = new ArrayList<>();

        for (TAGraph component : components) {
            // generate new graph = component + separator
            Set<Integer> newVertices = new HashSet<>(separator);
            newVertices.addAll(component.getVerticesIds());
            TAGraph newGraph = graph.subgraphInducedBy(newVertices);

            // generate new W = separator + (component * W)
            Set<Integer> newW = new HashSet<>(newVertices);
            newW.retainAll(W);
            newW.addAll(separator);

            // !!!
            //increase W so it has right size
            extendSet(W, newVertices, minWSize);
            // !!!

            Result r = find(newGraph, newW, maxSeparatorSize, minWSize, maxBagSize);
            if (! r.successful) {
                r.decomposition = null;
                return r;
            }

            decompositions.add(r.decomposition);
        }
        // new decomposition is a new bag consisting of (separator+W) connected to roots of other decompositions
        Set<Integer> rootBag = new HashSet<>(separator);
        rootBag.addAll(W);
        DecompositionNode node = new DecompositionNodeImpl(rootBag);
        for (TreeDecomposition decomposition : decompositions) {
            node.addChild(decomposition.getRoot());
        }
        result.successful = true;
        result.decomposition = new TreeDecompositionImpl(node);
        return result;
    }

}
