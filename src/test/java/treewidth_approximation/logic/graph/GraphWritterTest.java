package treewidth_approximation.logic.graph;

import org.junit.jupiter.api.Test;
import treewidth_approximation.logic.graph.graph_serialization.GraphWriter;
import treewidth_approximation.logic.steiner.SteinerInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GraphWritterTest {
    @Test
    public void testEmptyGraph() {
        TAGraph graph = new TAHashGraph();

        String graphString = GraphWriter.writeGraph(graph);

        assertEquals("0 0\n", graphString);
    }

    @Test
    public void testGraphOnlyVertices() {
        TAGraph graph = new TAHashGraph();
        graph.addVertex(1);
        graph.addVertex(0);
        graph.addVertex(2);

        String graphString = GraphWriter.writeGraph(graph);

        assertEquals("3 0\n", graphString);
    }

    @Test
    public void testGraphVerticesAndEdges() {
        TAGraph graph = new TAHashGraph();
        graph.addVertex(1);
        graph.addVertex(0);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.addEdge(1, 0);

        String graphString = GraphWriter.writeGraph(graph);

        assertEquals("3 2\n0 1\n1 2\n", graphString);
    }

    @Test
    public void testSteinerInstance() {
        TAGraph graph = new TAHashGraph();
        graph.addVertex(1);
        graph.addVertex(0);
        graph.addVertex(2);
        graph.addEdge(1, 2);
        graph.addEdge(1, 0);
        Set<Integer> terminals = Set.of(0, 2);
        SteinerInstance instance = new SteinerInstance(graph, terminals, new HashMap<>());

        String instanceString = GraphWriter.writeSteinerInstance(instance);

        assertEquals("3 2\n0 1\n1 2\n2\n0\n2\n", instanceString);
    }

    @Test
    public void testEdgeList() {
        List<TAEdge> edges = List.of(
                new TAEdge(1, 2),
                new TAEdge(0, 2)
        );

        String result = GraphWriter.writeEdgeList(edges);

        assertEquals("2\n1 2\n0 2\n", result);
    }
}
