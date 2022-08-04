package treewidth_approximation.logic.graph;

import org.junit.jupiter.api.Test;
import treewidth_approximation.logic.graph.graph_serialization.GraphScanner;
import treewidth_approximation.logic.graph.graph_serialization.GraphWriter;
import treewidth_approximation.logic.steiner.SteinerInstance;

import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GraphWritterTest {
    @Test
    public void testEmptyGraph() {
        TAGraph graph = new TAHashGraph();

        String graphString = GraphWriter.writeGraph(graph);

        assertEquals("0\n0\n", graphString);
    }

    @Test
    public void testGraphOnlyVertices() {
        TAGraph graph = new TAHashGraph();
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);

        String graphString = GraphWriter.writeGraph(graph);

        assertEquals("3\n2\n3\n4\n0\n", graphString);
    }

    @Test
    public void testGraphVerticesAndEdges() {
        TAGraph graph = new TAHashGraph();
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addEdge(3, 2);
        graph.addEdge(3, 4);

        String graphString = GraphWriter.writeGraph(graph);

        assertEquals("3\n2\n3\n4\n2\n2 3\n3 4\n", graphString);
    }

    @Test
    public void testSteinerInstanceNoSelected() {
        TAGraph graph = new TAHashGraph();
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addEdge(3, 2);
        graph.addEdge(3, 4);
        Set<Integer> terminals = Set.of(2, 4);
        SteinerInstance instance = new SteinerInstance(graph, terminals, new HashMap<>());

        String instanceString = GraphWriter.writeSteinerInstance(instance);

        assertEquals("3\n2\n3\n4\n2\n2 3\n3 4\n2\n2\n4\n", instanceString);
    }

    @Test
    public void testSteinerInstanceWithSelected() {
        TAGraph graph = new TAHashGraph();
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);
        graph.addEdge(3, 2);
        graph.addEdge(3, 4);
        Set<Integer> terminals = Set.of(2, 4);
        SteinerInstance instance = new SteinerInstance(graph, terminals, new HashMap<>());
        instance.setSelected(Set.of(new TAEdge(2, 3)));

        String instanceString = GraphWriter.writeSteinerInstance(instance);

        assertEquals("3\n2\n3\n4\n2\n2 3\n3 4\n2\n2\n4\n1\n2 3\n", instanceString);
    }
}
