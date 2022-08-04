package treewidth_approximation.logic.graph;

import org.junit.jupiter.api.Test;
import treewidth_approximation.logic.graph.graph_serialization.GraphScanner;

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
        String graphString = "3 0\n" +
                "2\n" +
                "3\n" +
                "4\n";

        TAGraph graph = GraphScanner.scanGraph(graphString, TAHashGraph::new);

        assertEquals(3, graph.getVertexAmount());
        assertNotNull(graph.getVertexById(2));
        assertNotNull(graph.getVertexById(3));
        assertNotNull(graph.getVertexById(4));
        assertEquals(0, graph.getEdgeAmount());
    }

    @Test
    public void testGraphVerticesAndEdges() {
        String graphString = "3 2\n" +
                "2\n" +
                "3\n" +
                "4\n" +
                "3 2\n" +
                "3 4\n";

        TAGraph graph = GraphScanner.scanGraph(graphString, TAHashGraph::new);

        assertEquals(3, graph.getVertexAmount());
        assertNotNull(graph.getVertexById(2));
        assertNotNull(graph.getVertexById(3));
        assertNotNull(graph.getVertexById(4));
        assertEquals(2, graph.getEdgeAmount());
        assertTrue(graph.hasEdge(2, 3));
        assertTrue(graph.hasEdge(3, 4));
    }
}
