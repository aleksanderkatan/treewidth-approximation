package treewidth_approximation.logic.graph;

import org.junit.jupiter.api.Test;
import treewidth_approximation.logic.separator_finder.FlowNetwork;
import treewidth_approximation.logic.separator_finder.SeparatorFinder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SeparatorFinderTest {
    @Test
    void testNotConnected() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(10);
        g.addVertex(20);
        g.addVertex(30);

        Set<Integer> W = new HashSet<>();
        W.add(10);
        W.add(20);

        Set<Integer> ids;
        try {
            ids = SeparatorFinder.findSeparatorIds(g, W, 0);
        } catch (SeparatorFinder.NoSeparatorExistsException e) {
            throw new RuntimeException();
        }

        assertEquals(0, ids.size());
    }

    @Test
    void testWNotConnected() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(10);
        g.addVertex(20);
        g.addVertex(30);
        g.addEdge(10, 20);
        g.addEdge(20, 30);

        Set<Integer> W = new HashSet<>();
        W.add(10);
        W.add(30);

        Set<Integer> ids;
        try {
            ids = SeparatorFinder.findSeparatorIds(g, W, 1);
        } catch (SeparatorFinder.NoSeparatorExistsException e) {
            throw new RuntimeException();
        }

        assertEquals(1, ids.size());
        assertTrue(ids.contains(20));
    }

    @Test
    void testPath() {
        TAGraph g = new TAGraphImpl();
        g.addVertex(10);
        g.addVertex(20);
        g.addVertex(30);
        g.addEdge(10, 20);
        g.addEdge(20, 30);

        Set<Integer> W = new HashSet<>();
        W.add(10);
        W.add(20);
        W.add(30);

        Set<Integer> ids;
        try {
            ids = SeparatorFinder.findSeparatorIds(g, W, 1);
        } catch (SeparatorFinder.NoSeparatorExistsException e) {
            throw new RuntimeException();
        }

        assertEquals(1, ids.size());
        assertTrue(ids.contains(20));
    }
}
