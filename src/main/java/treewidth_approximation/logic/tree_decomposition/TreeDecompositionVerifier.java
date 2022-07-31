package treewidth_approximation.logic.tree_decomposition;

import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TreeDecompositionVerifier {
    public static boolean verify(TreeDecomposition decomposition, TAGraph graph, int expectedBagSize, boolean verbose) {
        DecompositionNode node = verifyTreeWidth(decomposition.getRoot(), expectedBagSize);
        if (node != null) {
            if (verbose) {
                System.out.println("A bag with too many vertices (" + node.getVertices().size() + ")");
                System.out.println(node.getLabel());
            }
            return false;
        }

        Set<TAEdge> edges = new HashSet<>();
        for (TAVertex v : graph.getVertices()) {
            for (TAVertex n : v.getNeighbours()) {
                edges.add(new TAEdge(v.getId(), n.getId()));
            }
        }

        for (Integer v : graph.getVerticesIds()) {
            VertexState result = verifyVertex(decomposition.getRoot(), v);
            if (result == VertexState.FAILED || result == VertexState.NOT_INTRODUCED) {
                if (verbose) {
                    if (result == VertexState.FAILED) {
                        System.out.println("Vertex " + v + " has been forgotten multiple times.");
                    } else {
                        System.out.println("Vertex " + v + " has not been introduced.");
                    }
                }
                return false;
            }
        }

        verifyEdges(decomposition.getRoot(), edges);
        if (! edges.isEmpty()) {
            if (verbose) {
                System.out.println("Not all edges were considered");
                for (TAEdge edge : edges) {
                    System.out.println(edge.getFirst() + " " + edge.getSecond());
                }
            }
            return false;
        }

        if (verbose) {
            System.out.println("This is a correct decomposition");
        }

        return true;
    }

    private static DecompositionNode verifyTreeWidth(DecompositionNode node, int expectedBagSize) {
        if (node.getVertices().size() > expectedBagSize) return node;
        for (DecompositionNode child : node.getChildren()) {
            DecompositionNode result = verifyTreeWidth(child, expectedBagSize);
            if (result != null) return result;
        }
        return null;
    }

    private static void verifyEdges(DecompositionNode node, Set<TAEdge> remainingEdges) {
        for (DecompositionNode child : node.getChildren()) {
            verifyEdges(child, remainingEdges);
        }
        for (Integer a : node.getVertices()) {
            for (Integer b : node.getVertices()) {
                remainingEdges.remove(new TAEdge(a, b));
            }
        }
    }

    private enum VertexState {
        NOT_INTRODUCED, INTRODUCED, FORGOTTEN, FAILED
    }

    private static VertexState verifyVertex(DecompositionNode node, int vertex) {
        Map<VertexState, Integer> states = new HashMap<>();
        states.put(VertexState.NOT_INTRODUCED, 0);
        states.put(VertexState.INTRODUCED, 0);
        states.put(VertexState.FORGOTTEN, 0);
        states.put(VertexState.FAILED, 0);

        for (DecompositionNode child : node.getChildren()) {
            VertexState result = verifyVertex(child, vertex);
            states.put(result, states.get(result) + 1);
        }

        if (states.get(VertexState.FAILED) > 0) return VertexState.FAILED;
        // only first three cases remaining
        if (states.get(VertexState.FORGOTTEN) > 0) {
            if (states.get(VertexState.FORGOTTEN) > 1) return VertexState.FAILED;
            if (states.get(VertexState.INTRODUCED) > 0) return VertexState.FAILED;
            return VertexState.FORGOTTEN;
        }
        // only first two remaining
        if (states.get(VertexState.INTRODUCED) > 0) {
            if (node.getVertices().contains(vertex)) return VertexState.INTRODUCED;
            return VertexState.FORGOTTEN;
        }
        // only first
        if (node.getVertices().contains(vertex)) return VertexState.INTRODUCED;
        return VertexState.NOT_INTRODUCED;
    }
}
