package treewidth_approximation.graph;

import java.util.Collection;

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
}
