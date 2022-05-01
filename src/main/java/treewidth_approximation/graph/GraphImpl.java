package treewidth_approximation.graph;

import java.util.*;

public class GraphImpl implements Graph {
    // undirected graph without multi-edges or loops
    Map<Integer, Vertex> vertices;

    public GraphImpl() {
        vertices = new HashMap<>();
    }

    @Override
    public Collection<Vertex> getVertices() {
        return vertices.values();
    }

    @Override
    public Vertex getVertexById(int id) {
        return vertices.get(id);
    }

    @Override
    public Vertex addVertex(int id) {
        Vertex newVertex = new VertexImpl(id);
        vertices.put(id, newVertex);
        return newVertex;
    }

    @Override
    public void removeVertex(Vertex vertex) {
        vertices.remove(vertex.getId());
        Set<Vertex> neighbours = new HashSet<>(vertex.getNeighbours());
        for (Vertex neighbour : neighbours) removeEdge(vertex, neighbour);
    }

    @Override
    public void addEdge(Vertex first, Vertex second) {
        first.addNeighbour(second);
        second.addNeighbour(first);
    }

    @Override
    public void removeEdge(Vertex first, Vertex second) {
        first.removeNeighbour(second);
        second.removeNeighbour(first);
    }
}
