package treewidth_approximation.logic.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TAGraphTest {
    @Test
    void testVertexAddition() {
        TAGraph g = new TAHashGraph();

        g.addVertex(11);
        g.addVertex(13);

        assertNotNull(g.getVertexById(11));
        assertNotNull(g.getVertexById(13));
        assertNull(g.getVertexById(12));
        assertEquals(2, g.getVertexAmount());
    }

    @Test
    void testEdgeAddition() {
        TAGraph g = new TAHashGraph();
        g.addVertex(2);
        g.addVertex(1);
        g.addVertex(3);

        g.addEdge(2, 1);
        g.addEdge(2, 3);

        assertTrue(g.hasEdge(1, 2));
        assertTrue(g.hasEdge(2, 3));
        assertFalse(g.hasEdge(1, 3));
        assertEquals(2, g.getEdgeAmount());
    }
}
