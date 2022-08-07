package treewidth_approximation.logic.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class TAHashGraph implements TAGraph {
    // undirected graph without multi-edges or loops
    Map<Integer, TAVertex> vertices;

    public TAHashGraph() {
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
    public TAGraph copyNormalized() {
        Map<TAVertex, Integer> map = new HashMap<>();

        int i = 0;
        for (TAVertex v : vertices.values()) {
            map.put(v, i);
            i++;
        }

        TAGraph result = new TAHashGraph();

        for (TAVertex v : vertices.values()) {
            result.addVertex(map.get(v));
        }
        for (TAVertex v : vertices.values()) {
            for (TAVertex n : v.getNeighbours()) {
                result.addEdge(map.get(v), map.get(n));
            }
        }
        return result;
    }

    @Override
    public TAVertex addVertex(int id) {
        TAVertex newVertex = new TAHashVertex(id);
        vertices.put(id, newVertex);
        return newVertex;
    }

    @Override
    public int getVertexAmount() {
        return getVertices().size();
    }

    @Override
    public void addEdge(int firstId, int secondId) {
        TAVertex first = getVertexById(firstId);
        TAVertex second = getVertexById(secondId);

        if (first == second) {return;}
        if (first == null || second == null) {return;}
        if (vertices.get(first.getId()) == null || vertices.get(second.getId()) == null) {return;}
        if (first.getNeighbours().contains(second)) {return;}
        first.addNeighbour(second);
        second.addNeighbour(first);
    }

    @Override
    public boolean hasEdge(int firstId, int secondId) {
        return vertices.get(firstId).getNeighboursIds().contains(secondId);
    }

    @Override
    public int getEdgeAmount() {
        // could be optimized
        int total = 0;
        for (TAVertex vertex : getVertices()) {
            total += vertex.getNeighbours().size();
        }
        return total / 2;
    }

    @Override
    public TAGraph copyRestricting(Set<Integer> restricted) {
        Set<Integer> retained = new HashSet<>(vertices.keySet());
        retained.removeAll(restricted);
        return subgraphInducedBy(retained);
    }

    @Override
    public TAGraph subgraphInducedBy(Set<Integer> vertices) {
        TAGraph result = new TAHashGraph();

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
            if (vertexComponents.get(v.getId()) != null) {continue;}

            Queue<Integer> q = new LinkedList<>();
            q.add(v.getId());

            while (!q.isEmpty()) {
                int vertex = q.remove();
                if (vertexComponents.containsKey(vertex)) {continue;}
                vertexComponents.put(vertex, index);
                for (TAVertex n : getVertexById(vertex).getNeighbours()) {
                    q.add(n.getId());
                }
            }
            index++;
        }

        List<TAGraph> components = new ArrayList<>();
        for (int i = 0; i < index; i++) {components.add(new TAHashGraph());}
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
        for (int i = 0; i <= vertices.size(); i++) {componentsBySize.add(new ArrayList<>());}
        for (TAGraph g : components) {
            if (normalizeIds) {
                g = g.copyNormalized();
            }
            componentsBySize.get(g.getVertices().size()).add(g);
        }

        List<TAGraph> orderedComponents = new ArrayList<>();
        for (int i = vertices.size(); i >= 0; i--) {
            orderedComponents.addAll(componentsBySize.get(i));
        }

        return orderedComponents;
    }
}
