package treewidth_approximation.logic.graph;

import java.util.Collection;
import java.util.Set;

public interface TAGraph {
    Collection<TAVertex> getVertices();

    TAVertex getVertexById(int id);
    void normalizeIds();

    TAVertex addVertex(int id);
    void removeVertex(TAVertex vertex);
    void removeVertex(int vertexId);

    void addEdge(TAVertex first, TAVertex second);
    void addEdge(int firstId, int secondId);
    void removeEdge(TAVertex first, TAVertex second);
    void removeEdge(int firstId, int secondId);

    TAGraph copyRestricting(Set<Integer> restricted);
}
