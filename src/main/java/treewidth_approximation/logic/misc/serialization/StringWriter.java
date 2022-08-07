package treewidth_approximation.logic.misc.serialization;

import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.steiner.SteinerInstance;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StringWriter {
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

    public static String writeSet(Set<Integer> set) {
        if (set.isEmpty()) {return "EMPTY";}
        StringBuilder s = new StringBuilder();
        for (Integer id : set) {
            s.append(id).append(", ");
        }
        s.delete(s.length() - 2, s.length() - 1);
        return new String(s);
    }
}
