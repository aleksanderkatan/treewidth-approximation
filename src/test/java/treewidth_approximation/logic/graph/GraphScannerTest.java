package treewidth_approximation.logic.graph;

import org.junit.jupiter.api.Test;
import treewidth_approximation.logic.graph.graph_serialization.GraphScanner;
import treewidth_approximation.logic.steiner.SteinerInstance;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GraphScannerTest {
    @Test
    public void testEmptyGraph() {
        String graphString = "0 0";

        TAGraph graph = GraphScanner.scanGraph(graphString, TAHashGraph::new);

        assertEquals(0, graph.getVertexAmount());
        assertEquals(0, graph.getEdgeAmount());
    }

    @Test
    public void testGraphOnlyVertices() {
        String graphString = "3 0\n";

        TAGraph graph = GraphScanner.scanGraph(graphString, TAHashGraph::new);

        assertEquals(3, graph.getVertexAmount());
        assertNotNull(graph.getVertexById(0));
        assertNotNull(graph.getVertexById(1));
        assertNotNull(graph.getVertexById(2));
        assertEquals(0, graph.getEdgeAmount());
    }

    @Test
    public void testGraphVerticesAndEdges() {
        String graphString = "3 2\n" +
                "1 2\n" +
                "1 0\n";

        TAGraph graph = GraphScanner.scanGraph(graphString, TAHashGraph::new);

        assertEquals(3, graph.getVertexAmount());
        assertNotNull(graph.getVertexById(0));
        assertNotNull(graph.getVertexById(1));
        assertNotNull(graph.getVertexById(2));
        assertEquals(2, graph.getEdgeAmount());
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(0, 1));
    }

    @Test
    public void testSteinerInstance() {
        String instanceString = "3 2\n" +
                "1 2\n" +
                "1 0\n" +
                "2\n" +
                "2\n" +
                "0";

        SteinerInstance instance = GraphScanner.scanSteinerInstance(instanceString, TAHashGraph::new);
        TAGraph graph = instance.getGraph();
        Set<Integer> terminals = instance.getTerminals();
        Set<TAEdge> selectedEdges = instance.getSelected();

        assertEquals(3, graph.getVertexAmount());
        assertNotNull(graph.getVertexById(0));
        assertNotNull(graph.getVertexById(1));
        assertNotNull(graph.getVertexById(2));
        assertEquals(2, graph.getEdgeAmount());
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(0, 1));
        assertEquals(Set.of(0, 2), terminals);
        assertEquals(Set.of(), selectedEdges);
    }
}
