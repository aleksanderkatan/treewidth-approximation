package treewidth_approximation.graph;

import java.util.Set;

public interface Vertex {
    int getId();
    Set<Vertex> getNeighbours();
    void addNeighbour(Vertex neighbour);
    void removeNeighbour(Vertex neighbour);
}
