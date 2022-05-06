package treewidth_approximation.separator_finder;

import treewidth_approximation.graph.TAGraph;
import treewidth_approximation.graph.TAVertex;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class FlowNetwork {
    private static final Integer INFINITY = 1<<30;
    private static class Edge {
        Integer source;
        Integer target;
        Integer capacity;
        Integer flow;

        Edge(Integer source, Integer target, Integer capacity) {
            this.source = source;
            this.target = target;
            this.capacity = capacity;
            this.flow = 0;
        }
    }

    private static class Vertex {
        List<Edge> edges;
        Integer id;

        Vertex(Integer id) {
            edges = new ArrayList<>();
            this.id = id;
        }
    }

    private List<Vertex> vertices; // [0] - source, [1] - target
    private Map<Integer, Integer> idToIndex; // inIndex of a vertex

    public FlowNetwork(TAGraph graph, Set<TAVertex> A, Set<TAVertex> B) {
        vertices = new ArrayList<>();
        idToIndex = new HashMap<>();

        Vertex source = new Vertex(-1);
        Vertex target = new Vertex(-2);

        vertices.add(source);
        vertices.add(target);

        // add vertices, source-in, in-out, out-target edges
        List<TAVertex> originalVertices = new ArrayList<>(graph.getVertices());
        for (int i = 0; i< originalVertices.size(); i++) {
            TAVertex v = originalVertices.get(i);
            Vertex in = new Vertex(v.getId());
            int indexIn = 2+2*i;
            idToIndex.put(v.getId(), indexIn);
            Vertex out = new Vertex(v.getId());
            int indexOut = 2+2*i+1;

            int betweenCapacity = 1;

            if (A.contains(v)) {
                Edge edgeSV = new Edge(0, indexIn, INFINITY);
                source.edges.add(edgeSV);
                in.edges.add(edgeSV);
                betweenCapacity = INFINITY;
            }
            if (B.contains(v)) {
                Edge edgeVT = new Edge(indexOut, 1, INFINITY);
                out.edges.add(edgeVT);
                target.edges.add(edgeVT);
                betweenCapacity = INFINITY;
            }

            Edge edge = new Edge(indexIn, indexOut, betweenCapacity);
            in.edges.add(edge);
            out.edges.add(edge);

            vertices.add(in);
            vertices.add(out);
        }

        // add remaining edges
        for (TAVertex v : originalVertices) {
            for (TAVertex n : v.getNeighbours()) {
                if (n.getId() < v.getId()) continue;

                int inIndexV = idToIndex.get(v.getId());
                int outIndexV = inIndexV+1;
                int inIndexN = idToIndex.get(n.getId());
                int outIndexN = inIndexN+1;

                Vertex inV = vertices.get(inIndexV);
                Vertex outV = vertices.get(outIndexV);
                Vertex inN = vertices.get(inIndexN);
                Vertex outN = vertices.get(outIndexN);

                Edge e1 = new Edge(outIndexV, inIndexN, INFINITY);
                outV.edges.add(e1);
                inN.edges.add(e1);

                Edge e2 = new Edge(outIndexN, inIndexV, INFINITY);
                outN.edges.add(e2);
                inV.edges.add(e2);
            }
        }
    }

    private boolean increaseFlow() {
        List<Edge> edgeThatGotToVertex = new ArrayList<>();
        for (int i = 0; i< vertices.size(); i++) edgeThatGotToVertex.add(null);

        Queue<Integer> q = new LinkedList<>();
        q.add(0);

        while (!q.isEmpty()) {
            int current = q.remove();

            for (Edge e : vertices.get(current).edges) {
                if (e.source == current && e.flow < e.capacity && edgeThatGotToVertex.get(e.target) == null) {
                    edgeThatGotToVertex.set(e.target, e);
                    q.add(e.target);
                }
                if (e.target == current && e.flow > 0 && edgeThatGotToVertex.get(e.source) == null) {
                    edgeThatGotToVertex.set(e.source, e);
                    q.add(e.source);
                }
            }
        }

        if (edgeThatGotToVertex.get(1) == null)
            return false;

        int current = 1;
        while (current != 0) {
            Edge e = edgeThatGotToVertex.get(current);
            if (e.target == current) {
                e.flow += 1;
                current = e.source;
            } else {
                e.flow -= 1;
                current = e.target;
            }
        }
        return true;
    }

    public int findMaxFlow(int limit) {
        for (int i = 0; i< limit; i++) {
            boolean succeeded = increaseFlow();
            if (!succeeded) return i;
        }
        return limit;
    }
}
