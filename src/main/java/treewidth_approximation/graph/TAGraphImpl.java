package treewidth_approximation.graph;

import java.util.*;

public class TAGraphImpl implements TAGraph {
    // undirected graph without multi-edges or loops
    Map<Integer, TAVertex> vertices;

    public TAGraphImpl() {
        vertices = new HashMap<>();
    }

    @Override
    public Collection<TAVertex> getVertices() {
        return vertices.values();
    }

    @Override
    public TAVertex getVertexById(int id) {
        return vertices.get(id);
    }

    @Override
    public TAVertex addVertex(int id) {
        TAVertex newVertex = new TAVertexImpl(id);
        vertices.put(id, newVertex);
        return newVertex;
    }

    @Override
    public void removeVertex(TAVertex vertex) {
        vertices.remove(vertex.getId());
        Set<TAVertex> neighbours = new HashSet<>(vertex.getNeighbours());
        for (TAVertex neighbour : neighbours) removeEdge(vertex, neighbour);
    }

    @Override
    public void addEdge(TAVertex first, TAVertex second) {
        if (first == second) return;
        if (first.getNeighbours().contains(second)) return;
        first.addNeighbour(second);
        second.addNeighbour(first);
    }

    @Override
    public void removeEdge(TAVertex first, TAVertex second) {
        first.removeNeighbour(second);
        second.removeNeighbour(first);
    }
}
