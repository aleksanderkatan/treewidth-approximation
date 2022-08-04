package treewidth_approximation.logic.graph.graph_serialization;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class GraphWriter {
    public static String writeGraph(TAGraph graph) {
        StringBuilder result = new StringBuilder();

        int vertices = graph.getVertexAmount();
        int edges = graph.getEdgeAmount();

        result.append(vertices).append(" ");
        result.append(edges).append("\n");

        List<Integer> sortedVertices = graph
                .getVertices()
                .stream()
                .map(TAVertex::getId)
                .sorted()
                .collect(Collectors.toList());
        for (Integer v : sortedVertices) {
            result.append(v).append("\n");
        }
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
}
