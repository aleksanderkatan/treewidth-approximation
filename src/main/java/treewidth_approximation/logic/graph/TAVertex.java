package treewidth_approximation.logic.graph;

import java.util.Set;

public interface TAVertex {
    int getId();
    void setId(int id);
    Set<TAVertex> getNeighbours();
    void addNeighbour(TAVertex neighbour);
    void removeNeighbour(TAVertex neighbour);
}
