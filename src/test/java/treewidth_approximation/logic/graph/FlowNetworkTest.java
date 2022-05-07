package treewidth_approximation.logic.graph;

import org.junit.jupiter.api.Test;
import treewidth_approximation.logic.separator_finder.FlowNetwork;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FlowNetworkTest {
    @Test
    void testNotConnected() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(10);
        g.addVertex(20);
        g.addVertex(30);

        Set<Integer> A = new HashSet<>();
        A.add(10);
        Set<Integer> B = new HashSet<>();
        B.add(30);

        FlowNetwork network = new FlowNetwork(g, A, B);


        int result = network.increaseCurrentFlow(10);


        assertEquals(0, result);
    }

    @Test
    void testPath() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(30);
        g.addVertex(20);
        g.addVertex(10);
        g.addEdge(10, 20);
        g.addEdge(20, 30);

        Set<Integer> A = new HashSet<>();
        A.add(10);
        Set<Integer> B = new HashSet<>();
        B.add(30);

        FlowNetwork network = new FlowNetwork(g, A, B);


        int result = network.increaseCurrentFlow(10);


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

        Set<Integer> A = new HashSet<>();
        A.add(1);
        Set<Integer> B = new HashSet<>();
        B.add(4);

        FlowNetwork network = new FlowNetwork(g, A, B);


        int result = network.increaseCurrentFlow(10);


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

        Set<Integer> A = new HashSet<>();
        A.add(1);
        A.add(4);
        Set<Integer> B = new HashSet<>();
        B.add(3);
        B.add(6);

        FlowNetwork network = new FlowNetwork(g, A, B);


        int result = network.increaseCurrentFlow(10);


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

        Set<Integer> A = new HashSet<>();
        A.add(1);
        Set<Integer> B = new HashSet<>();
        B.add(4);

        FlowNetwork network = new FlowNetwork(g, A, B);


        int result = network.increaseCurrentFlow(10);


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

        Set<Integer> A = new HashSet<>();
        A.add(1);
        A.add(2);
        A.add(3);
        Set<Integer> B = new HashSet<>();
        B.add(6);
        B.add(7);
        B.add(8);

        FlowNetwork network = new FlowNetwork(g, A, B);


        int result = network.increaseCurrentFlow(10);


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

        Set<Integer> A = new HashSet<>();
        A.add(1);
        A.add(4);
        Set<Integer> B = new HashSet<>();
        B.add(3);
        B.add(6);

        FlowNetwork network = new FlowNetwork(g, A, B);


        int result = network.increaseCurrentFlow(1);


        assertEquals(1, result);
    }

    @Test
    void testSeparatorThrows() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge(1, 2);
        g.addEdge(2, 3);

        Set<Integer> A = new HashSet<>();
        A.add(1);
        Set<Integer> B = new HashSet<>();
        B.add(3);

        FlowNetwork network = new FlowNetwork(g, A, B);


        network.increaseCurrentFlow(1);
        assertThrows(FlowNetwork.FlowNotMaximalException.class, network::getSeparatorIds);
    }

    @Test
    void testSeparatorPath() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addEdge(1, 2);
        g.addEdge(2, 3);

        Set<Integer> A = new HashSet<>();
        A.add(1);
        Set<Integer> B = new HashSet<>();
        B.add(3);

        FlowNetwork network = new FlowNetwork(g, A, B);


        network.increaseCurrentFlow(2);
        List<Integer> ids = network.getSeparatorIds();


        assertEquals(1, ids.size());
        assertTrue(ids.contains(2));
    }

    @Test
    void testSeparatorMultipleVertices() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addVertex(5);
        g.addVertex(6);
        g.addVertex(7);
        g.addVertex(8);
        g.addVertex(9);
        g.addEdge(1, 4);
        g.addEdge(2, 5);
        g.addEdge(3, 5);
        g.addEdge(4, 5);
        g.addEdge(4, 6);
        g.addEdge(4, 7);
        g.addEdge(4, 8);
        g.addEdge(5, 8);
        g.addEdge(5, 9);

        Set<Integer> A = new HashSet<>();
        A.add(1);
        A.add(2);
        A.add(3);
        Set<Integer> B = new HashSet<>();
        B.add(7);
        B.add(8);
        B.add(9);

        FlowNetwork network = new FlowNetwork(g, A, B);


        network.increaseCurrentFlow(10);
        List<Integer> ids = network.getSeparatorIds();


        assertEquals(2, ids.size());
        assertTrue(ids.contains(4));
        assertTrue(ids.contains(5));
    }
}
