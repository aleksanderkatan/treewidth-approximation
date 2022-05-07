package treewidth_approximation.logic.graph;

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
    public void normalizeIds() {
        Collection<TAVertex> temp = getVertices();
        vertices = new HashMap<>();

        int i = 0;
        for (TAVertex v : temp) {
            v.setId(i);
            vertices.put(i, v);
            i++;
        }
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
    public void removeVertex(int vertexId) {
        vertices.remove(vertexId);
    }

    @Override
    public void addEdge(TAVertex first, TAVertex second) {
        if (first == second) return;
        if (first.getNeighbours().contains(second)) return;
        first.addNeighbour(second);
        second.addNeighbour(first);
    }

    @Override
    public void addEdge(int firstId, int secondId) {
        addEdge(vertices.get(firstId), vertices.get(secondId));
    }

    @Override
    public void removeEdge(TAVertex first, TAVertex second) {
        first.removeNeighbour(second);
        second.removeNeighbour(first);
    }

    @Override
    public void removeEdge(int firstId, int secondId) {
        removeEdge(vertices.get(firstId), vertices.get(secondId));
    }

    @Override
    public TAGraph copyRestricting(Set<Integer> restricted) {
        TAGraph result = new TAGraphImpl();
        List<TAVertex> originalVertices = new ArrayList<>(getVertices());

        for (TAVertex v : originalVertices) {
            if (restricted.contains(v.getId())) continue;
            result.addVertex(v.getId());
        }

        for (TAVertex v : originalVertices) {
            if (restricted.contains(v.getId())) continue;
            for (TAVertex n : v.getNeighbours()) {
                if (restricted.contains(n.getId())) continue;
                result.addEdge(v.getId(), n.getId());
            }
        }

        return result;
    }
}
