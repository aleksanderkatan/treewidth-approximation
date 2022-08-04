package treewidth_approximation.logic.graph.graph_serialization;

import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.steiner.SteinerInstance;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class GraphScanner {
    public static TAGraph scanGraph(String graph, Supplier<TAGraph> graphSupplier) {
        TAGraph result = graphSupplier.get();

        String[] input = graph.split("\\s+");
        int vertices = Integer.parseInt(input[0]);
        for (int i = 0; i< vertices; i++) {
            int v = Integer.parseInt(input[1+i]);
            result.addVertex(v);
        }

        int edges = Integer.parseInt(input[1+vertices]);
        String[] inputEdges = Arrays.copyOfRange(input, 2+vertices, 2+vertices+2*edges);
        for (int i = 0; i< edges; i++) {
            int u = Integer.parseInt(inputEdges[2*i]);
            int v = Integer.parseInt(inputEdges[2*i+1]);
            result.addEdge(u, v);
        }
        return result;
    }

    public static SteinerInstance scanSteinerInstance(String instance, Supplier<TAGraph> graphSupplier) {
        TAGraph graph = scanGraph(instance, graphSupplier);

        String[] input = instance.split("\\s+");
        int current = 0;
        int vertices = Integer.parseInt(input[0]);
        current = current + vertices + 1;
        int edges = Integer.parseInt(input[current]);
        current = current + 2*edges + 1;
        int terminals = Integer.parseInt(input[current]);
        String[] inputTerminals = Arrays.copyOfRange(input, current+1, current+1+terminals);
        current = current + terminals + 1;
        int selectedEdges = 0;
        String[] inputSelectedEdges = new String[0];
        if (input.length > current) {
            selectedEdges = Integer.parseInt(input[current]);
            inputSelectedEdges = Arrays.copyOfRange(input, current+1, current+1+2*selectedEdges);
        }

        Set<Integer> terminalsSet = new HashSet<>();
        for (int i = 0; i< terminals; i++) {
            int v = Integer.parseInt(inputTerminals[i]);
            terminalsSet.add(v);
        }

        Set<TAEdge> selectedEdgesSet = new HashSet<>();
        for (int i = 0; i< selectedEdges; i++) {
            int u = Integer.parseInt(inputSelectedEdges[2*i]);
            int v = Integer.parseInt(inputSelectedEdges[2*i+1]);
            selectedEdgesSet.add(new TAEdge(u, v));
        }

        SteinerInstance result = new SteinerInstance(graph, terminalsSet, new HashMap<>());
        result.setSelected(selectedEdgesSet);

        return result;
    }

}
