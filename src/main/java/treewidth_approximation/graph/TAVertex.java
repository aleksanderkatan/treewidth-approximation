package treewidth_approximation.graph;

import java.util.Set;

public interface TAVertex {
    int getId();
    Set<TAVertex> getNeighbours();
    void addNeighbour(TAVertex neighbour);
    void removeNeighbour(TAVertex neighbour);
}
