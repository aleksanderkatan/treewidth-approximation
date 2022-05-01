package treewidth_approximation.graph;

import java.util.HashSet;
import java.util.Set;

public class VertexImpl implements Vertex {
    private final Set<Vertex> neighbours;
    private final int id;

    public VertexImpl(int id) {
        this.id = id;
        neighbours = new HashSet<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Set<Vertex> getNeighbours() {
        return neighbours;
    }

    @Override
    public void addNeighbour(Vertex neighbour) {
        neighbours.add(neighbour);
    }

    @Override
    public void removeNeighbour(Vertex neighbour) {
        neighbours.remove(neighbour);
    }
}
