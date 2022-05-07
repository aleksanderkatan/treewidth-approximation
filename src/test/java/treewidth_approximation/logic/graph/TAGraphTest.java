package treewidth_approximation.logic.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TAGraphTest {
    @Test
    void testVertexAddition() {
        TAGraph g = new TAGraphImpl();

        g.addVertex(11);
        g.addVertex(13);

        assertNotNull(g.getVertexById(11));
        assertNotNull(g.getVertexById(13));
        assertNull(g.getVertexById(12));
    }

    @Test
    void testVertexRemoval() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(11);
        TAVertex removed = g.addVertex(13);

        g.removeVertex(removed);

        assertNotNull(g.getVertexById(11));
        assertNull(g.getVertexById(13));
    }
}
