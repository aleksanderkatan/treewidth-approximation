package treewidth_approximation.graph;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface Graph {
    Collection<Vertex> getVertices();

    Vertex getVertexById(int id);

    Vertex addVertex(int id);
    void removeVertex(Vertex vertex);

    void addEdge(Vertex first, Vertex second);
    void removeEdge(Vertex first, Vertex second);
}
