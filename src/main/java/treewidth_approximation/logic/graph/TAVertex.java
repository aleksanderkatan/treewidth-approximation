package treewidth_approximation.logic.graph;

import java.util.Set;

public interface TAVertex {
    int getId();
    void setId(int id);
    Set<TAVertex> getNeighbours();
    Set<Integer> getNeighboursIds();
    void addNeighbour(TAVertex neighbour);
    void removeNeighbour(TAVertex neighbour);
}
