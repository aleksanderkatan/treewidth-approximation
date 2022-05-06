package treewidth_approximation.graph;

import org.junit.jupiter.api.Test;
import treewidth_approximation.separator_finder.FlowNetwork;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FlowNetworkTest {
    @Test
    void testNotConnected() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(10);
        g.addVertex(20);
        g.addVertex(30);

        Set<TAVertex> A = new HashSet<>();
        A.add(g.getVertexById(10));
        Set<TAVertex> B = new HashSet<>();
        B.add(g.getVertexById(30));

        FlowNetwork network = new FlowNetwork(g, A, B);


        int result = network.findMaxFlow(10);


        assertEquals(0, result);
    }

    @Test
    void testPath() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(10);
        g.addVertex(20);
        g.addVertex(30);
        g.addEdge(10, 20);
        g.addEdge(20, 30);

        Set<TAVertex> A = new HashSet<>();
        A.add(g.getVertexById(10));
        Set<TAVertex> B = new HashSet<>();
        B.add(g.getVertexById(30));

        FlowNetwork network = new FlowNetwork(g, A, B);


        int result = network.findMaxFlow(10);


        assertEquals(1, result);
    }

    @Test
    void testDoublePath() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addEdge(1, 2);
        g.addEdge(2, 4);
        g.addEdge(1, 3);
        g.addEdge(3, 4);

        Set<TAVertex> A = new HashSet<>();
        A.add(g.getVertexById(1));
        Set<TAVertex> B = new HashSet<>();
        B.add(g.getVertexById(4));

        FlowNetwork network = new FlowNetwork(g, A, B);


        int result = network.findMaxFlow(10);


        assertEquals(2, result);
    }

    @Test
    void testDoubleDisjointPaths() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addVertex(4);
        g.addVertex(5);
        g.addVertex(6);
        g.addEdge(4, 5);
        g.addEdge(5, 6);

        Set<TAVertex> A = new HashSet<>();
        A.add(g.getVertexById(1));
        A.add(g.getVertexById(4));
        Set<TAVertex> B = new HashSet<>();
        B.add(g.getVertexById(3));
        B.add(g.getVertexById(6));

        FlowNetwork network = new FlowNetwork(g, A, B);


        int result = network.findMaxFlow(10);


        assertEquals(2, result);
    }

    @Test
    void testRequireAlternation() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addVertex(5);
        g.addEdge(1, 5);
        g.addEdge(5, 3);
        g.addVertex(6);
        g.addEdge(2, 6);
        g.addEdge(6, 4);

        Set<TAVertex> A = new HashSet<>();
        A.add(g.getVertexById(1));
        Set<TAVertex> B = new HashSet<>();
        B.add(g.getVertexById(4));

        FlowNetwork network = new FlowNetwork(g, A, B);


        int result = network.findMaxFlow(10);


        assertEquals(2, result);
    }

    @Test
    void testMultipleVertices() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addVertex(5);
        g.addVertex(6);
        g.addVertex(7);
        g.addVertex(8);
        g.addEdge(1, 4);
        g.addEdge(2, 5);
        g.addEdge(3, 5);
        g.addEdge(4, 5);
        g.addEdge(4, 6);
        g.addEdge(4, 7);
        g.addEdge(5, 7);
        g.addEdge(5, 8);

        Set<TAVertex> A = new HashSet<>();
        A.add(g.getVertexById(1));
        A.add(g.getVertexById(2));
        A.add(g.getVertexById(3));
        Set<TAVertex> B = new HashSet<>();
        B.add(g.getVertexById(6));
        B.add(g.getVertexById(7));
        B.add(g.getVertexById(8));

        FlowNetwork network = new FlowNetwork(g, A, B);


        int result = network.findMaxFlow(10);


        assertEquals(2, result);
    }

    @Test
    void testExceedingLimit() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addVertex(4);
        g.addVertex(5);
        g.addVertex(6);
        g.addEdge(4, 5);
        g.addEdge(5, 6);

        Set<TAVertex> A = new HashSet<>();
        A.add(g.getVertexById(1));
        A.add(g.getVertexById(4));
        Set<TAVertex> B = new HashSet<>();
        B.add(g.getVertexById(3));
        B.add(g.getVertexById(6));

        FlowNetwork network = new FlowNetwork(g, A, B);


        int result = network.findMaxFlow(1);


        assertEquals(1, result);
    }
}
