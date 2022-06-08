package treewidth_approximation.logic.graph;

import java.util.*;
import java.util.stream.Collectors;

public class TAGraphImpl implements TAGraph {
    // undirected graph without multi-edges or loops
    Map<Integer, TAVertex> vertices;

    public TAGraphImpl() {
        vertices = new HashMap<>();
    }

    @Override
    public Set<TAVertex> getVertices() {
        return new HashSet<>(vertices.values());
    }

    @Override
    public Set<Integer> getVerticesIds() {
        return getVertices().stream().map(TAVertex::getId).collect(Collectors.toSet());
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
        if (first == null || second == null) return;
        if (vertices.get(first.getId()) == null || vertices.get(second.getId()) == null) return;
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

    @Override
    public TAGraph subgraphInducedBy(Set<Integer> vertices) {
        TAGraph result = new TAGraphImpl();

        for (Integer v : vertices) {
            result.addVertex(v);
        }
        for (Integer v : vertices) {
            for (Integer n : getVertexById(v).getNeighboursIds()) {
                result.addEdge(v, n);
            }
        }
        return result;
    }

    @Override
    public List<TAGraph> splitIntoConnectedComponents(boolean normalizeIds) {
        Map<Integer, Integer> vertexComponents = new HashMap<>(); // vertex -> component

        int index = 0;
        for (TAVertex v : getVertices()) {
            if (vertexComponents.get(v.getId()) != null) continue;

            Queue<Integer> q = new LinkedList<>();
            q.add(v.getId());

            while (! q.isEmpty()) {
                int vertex = q.remove();
                if (vertexComponents.containsKey(vertex)) continue;
                vertexComponents.put(vertex, index);
                for (TAVertex n : getVertexById(vertex).getNeighbours()) {
                    q.add(n.getId());
                }
            }
            index++;
        }

        List<TAGraph> components = new ArrayList<>();
        for (int i = 0; i< index; i++) components.add(new TAGraphImpl());
        for (var k : vertexComponents.entrySet()) {
            components.get(k.getValue()).addVertex(k.getKey());
        }
        for (var k : vertexComponents.entrySet()) {
            TAGraph g = components.get(k.getValue());
            TAVertex v = getVertexById(k.getKey());
            for (TAVertex n : v.getNeighbours()) {
                g.addEdge(v.getId(), n.getId());
            }
        }

        // make sure everything stays linear
        List<List<TAGraph>> componentsBySize = new ArrayList<>();
        for (int i = 0; i<= vertices.size(); i++) componentsBySize.add(new ArrayList<>());
        for (TAGraph g : components) {
            if (normalizeIds) {
                g.normalizeIds();
            }
            componentsBySize.get(g.getVertices().size()).add(g);
        }

        List<TAGraph> orderedComponents = new ArrayList<>();
        for (int i = vertices.size(); i>=0; i--) {
            orderedComponents.addAll(componentsBySize.get(i));
        }

        return orderedComponents;
    }
}
