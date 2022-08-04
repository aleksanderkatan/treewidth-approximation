package treewidth_approximation.logic.graph;

import org.junit.jupiter.api.Test;
import treewidth_approximation.logic.graph.graph_serialization.GraphScanner;
import treewidth_approximation.logic.graph.graph_serialization.GraphWriter;

import static org.junit.jupiter.api.Assertions.*;

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
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);

        String graphString = GraphWriter.writeGraph(graph);

        assertEquals("3 0\n2\n3\n4\n", graphString);
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

        assertEquals("3 2\n2\n3\n4\n2 3\n3 4\n", graphString);
    }
}
