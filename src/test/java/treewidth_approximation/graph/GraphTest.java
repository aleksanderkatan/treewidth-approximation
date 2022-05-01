package treewidth_approximation.graph;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {
    @Test
    void testVertexAddition() {
        Graph g = new GraphImpl();

        g.addVertex(11);
        g.addVertex(13);

        assertNotNull(g.getVertexById(11));
        assertNotNull(g.getVertexById(13));
        assertNull(g.getVertexById(12));
    }

    @Test
    void testVertexRemoval() {
        Graph g = new GraphImpl();
        g.addVertex(11);
        Vertex removed = g.addVertex(13);

        g.removeVertex(removed);

        assertNotNull(g.getVertexById(11));
        assertNull(g.getVertexById(13));
    }
}
