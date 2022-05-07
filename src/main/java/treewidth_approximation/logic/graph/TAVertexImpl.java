package treewidth_approximation.logic.graph;

import java.util.HashSet;
import java.util.Set;

public class TAVertexImpl implements TAVertex {
    private final Set<TAVertex> neighbours;
    private int id;

    public TAVertexImpl(int id) {
        this.id = id;
        neighbours = new HashSet<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Set<TAVertex> getNeighbours() {
        return neighbours;
    }

    @Override
    public void addNeighbour(TAVertex neighbour) {
        neighbours.add(neighbour);
    }

    @Override
    public void removeNeighbour(TAVertex neighbour) {
        neighbours.remove(neighbour);
    }
}
