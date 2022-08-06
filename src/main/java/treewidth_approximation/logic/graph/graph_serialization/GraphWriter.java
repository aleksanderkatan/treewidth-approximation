package treewidth_approximation.logic.graph.graph_serialization;

import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.steiner.SteinerInstance;

import java.util.List;
import java.util.stream.Collectors;

public class GraphWriter {
    public static String writeGraph(TAGraph graph) {
        StringBuilder result = new StringBuilder();

        int vertices = graph.getVertexAmount();
        int edges = graph.getEdgeAmount();

        result.append(vertices).append(" ").append(edges).append("\n");

        List<Integer> sortedVertices = graph
                .getVertices()
                .stream()
                .map(TAVertex::getId)
                .sorted()
                .collect(Collectors.toList());

        for (Integer v : sortedVertices) {
            List<Integer> sortedNeighbours = graph
                    .getVertexById(v)
                    .getNeighbours()
                    .stream()
                    .map(TAVertex::getId)
                    .sorted()
                    .collect(Collectors.toList());
            for (Integer n : sortedNeighbours) {
                if (v < n) {
                    result.append(v).append(" ").append(n).append("\n");
                }
            }
        }

        return new String(result);
    }

    public static String writeSteinerInstance(SteinerInstance instance) {
        String graph = writeGraph(instance.getGraph());

        StringBuilder result = new StringBuilder();

        List<Integer> sortedTerminals = instance
                .getTerminals()
                .stream()
                .sorted()
                .collect(Collectors.toList());
        result.append(sortedTerminals.size()).append("\n");
        for (Integer v : sortedTerminals) {
            result.append(v).append("\n");
        }

        return graph + new String(result);
    }

    public static String writeEdgeList(List<TAEdge> edges) {
        StringBuilder result = new StringBuilder();

        result.append(edges.size()).append("\n");

        for (TAEdge edge : edges) {
            result.append(edge.getFirst()).append(" ").append(edge.getSecond()).append("\n");
        }

        return new String(result);
    }
}
