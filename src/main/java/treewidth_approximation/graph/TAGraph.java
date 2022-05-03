package treewidth_approximation.graph;

import java.util.Collection;

public interface TAGraph {
    Collection<TAVertex> getVertices();

    TAVertex getVertexById(int id);

    TAVertex addVertex(int id);
    void removeVertex(TAVertex vertex);

    void addEdge(TAVertex first, TAVertex second);
    void removeEdge(TAVertex first, TAVertex second);
}
