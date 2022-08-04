package treewidth_approximation.logic.graph.graph_serialization;

import treewidth_approximation.logic.graph.TAGraph;

import java.util.function.Supplier;

public class GraphScanner {
    public static TAGraph scanGraph(String graph, Supplier<TAGraph> graphSupplier) {
        TAGraph result = graphSupplier.get();

        String[] input = graph.split("\\s+");
        int vertices = Integer.parseInt(input[0]);
        int edges = Integer.parseInt(input[1]);
        for (int i = 0; i< vertices; i++) {
            int v = Integer.parseInt(input[2+i]);
            result.addVertex(v);
        }
        for (int i = 0; i< edges; i++) {
            int u = Integer.parseInt(input[2+vertices+2*i]);
            int v = Integer.parseInt(input[2+vertices+2*i+1]);
            result.addEdge(u, v);
        }
        return result;
    }

}
