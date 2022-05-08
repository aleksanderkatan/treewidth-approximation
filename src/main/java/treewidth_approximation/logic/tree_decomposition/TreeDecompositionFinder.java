package treewidth_approximation.logic.tree_decomposition;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.separator_finder.SeparatorFinder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Result findDecomposition(int actualTreeWidth) {
        int bagSize = actualTreeWidth + 1;
        int maxBagSize = 4*bagSize;
        DecompositionNode root = new DecompositionNodeImpl(new HashSet<>());

        for (TAGraph subgraph : originalGraph.splitIntoConnectedComponents()) {
            List<Integer> vertices = new ArrayList<>(subgraph.getVerticesIds());
            vertices = vertices.subList(0, min(3 * bagSize, vertices.size()));
            Set<Integer> W = new HashSet<>(vertices);
            Result r = find(subgraph, W, bagSize, maxBagSize);
            if (!r.successful) return r;
            root.addChild(r.decomposition.getRoot());
        }
        Result r = new Result();
        r.successful = true;
        r.decomposition = new TreeDecompositionImpl(root);
        return r;
    }

    private Result find(TAGraph graph, Set<Integer> W, int maxSeparatorSize, int maxBagSize) {
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
            //increase W so it has maxSeparatorSize*3
            List<Integer> extraVertices = new ArrayList<>(newVertices);
            extraVertices.removeAll(newW);
            extraVertices = extraVertices.subList(0, min(extraVertices.size(), 3*maxSeparatorSize-newW.size()));
            newW.addAll(extraVertices);
            // !!!

            Result r = find(newGraph, newW, maxSeparatorSize, maxBagSize);
            if (! r.successful) {
                r.decomposition = null;
                return r;
            }

//            boolean verify = TreeDecompositionVerifier.verify(r.decomposition, newGraph, 20, true);
//            if (!verify) {
//                GraphShower.showGraphWithIds(newGraph, new ArrayList<>(), "failed graph");
//                GraphShower.showTreeDecomposition(r.decomposition, "failed decomposition");
//                r.successful = false;
//                return r;
//            }

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
