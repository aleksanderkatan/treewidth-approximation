package treewidth_approximation.logic.misc.serialization;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.steiner.SteinerInstance;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class StringScanner {
    public static TAGraph scanGraph(String graph, Supplier<TAGraph> graphSupplier) {
        TAGraph result = graphSupplier.get();

        String[] input = graph.split("\\s+");
        int vertices = Integer.parseInt(input[0]);
        int edges = Integer.parseInt(input[1]);
        String[] inputEdges = Arrays.copyOfRange(input, 2, 2 + 2 * edges);

        for (int i = 0; i < vertices; i++) {
            result.addVertex(i);
        }

        for (int i = 0; i < edges; i++) {
            int u = Integer.parseInt(inputEdges[2 * i]);
            int v = Integer.parseInt(inputEdges[2 * i + 1]);
            result.addEdge(u, v);
        }
        return result;
    }

    public static SteinerInstance scanSteinerInstance(String instance, Supplier<TAGraph> graphSupplier) {
        TAGraph graph = scanGraph(instance, graphSupplier);

        String[] input = instance.split("\\s+");
        int edges = Integer.parseInt(input[1]);
        int terminals = Integer.parseInt(input[2 + 2 * edges]);
        String[] inputTerminals = Arrays.copyOfRange(input, 2 + 2 * edges + 1, 2 + 2 * edges + 1 + terminals);

        Set<Integer> terminalsSet = new HashSet<>();
        for (int i = 0; i < terminals; i++) {
            int v = Integer.parseInt(inputTerminals[i]);
            terminalsSet.add(v);
        }

        SteinerInstance result = new SteinerInstance(graph, terminalsSet, new HashMap<>());
        result.setSelected(new HashSet<>());

        return result;
    }

}
