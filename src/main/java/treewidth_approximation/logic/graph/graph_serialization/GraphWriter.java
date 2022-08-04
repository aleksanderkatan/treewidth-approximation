package treewidth_approximation.logic.graph.graph_serialization;

import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.steiner.SteinerInstance;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class GraphWriter {
    public static String writeGraph(TAGraph graph) {
        StringBuilder result = new StringBuilder();

        int vertices = graph.getVertexAmount();
        int edges = graph.getEdgeAmount();

        result.append(vertices).append("\n");

        List<Integer> sortedVertices = graph
                .getVertices()
                .stream()
                .map(TAVertex::getId)
                .sorted()
                .collect(Collectors.toList());
        for (Integer v : sortedVertices) {
            result.append(v).append("\n");
        }

        result.append(edges).append("\n");
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

        List<TAEdge> selected = instance.getSelected().stream().sorted().collect(Collectors.toList());
        if (selected.size() > 0) {
            result.append(selected.size()).append("\n");
            for (TAEdge edge : selected) {
                result.append(edge.getFirst()).append(" ").append(edge.getSecond()).append("\n");
            }
        }

        return graph + new String(result);
    }
}
