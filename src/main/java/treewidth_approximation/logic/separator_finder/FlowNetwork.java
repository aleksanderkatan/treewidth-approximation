package treewidth_approximation.logic.separator_finder;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;

import java.util.*;

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

    public static class FlowNotMaximalException extends RuntimeException {}

    private final List<Vertex> vertices; // [0] - source, [1] - target
    private List<Integer> separatorIds;

    public FlowNetwork(TAGraph graph, Set<Integer> A, Set<Integer> B) {
        // assumes there are no edges between A and B
        vertices = new ArrayList<>();
        // inIndex of a vertex
        Map<Integer, Integer> idToIndex = new HashMap<>();

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

            if (A.contains(v.getId())) {
                Edge edgeSV = new Edge(0, indexIn, INFINITY);
                source.edges.add(edgeSV);
                in.edges.add(edgeSV);
                betweenCapacity = INFINITY;
            }
            if (B.contains(v.getId())) {
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

    private List<Edge> BFS() {
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
        return edgeThatGotToVertex;
    }

    private boolean increaseFlowByOne() {
        List<Edge> edgeThatGotToVertex = BFS();

        if (edgeThatGotToVertex.get(1) == null) { // max flow = min cut
            separatorIds = new ArrayList<>();
            for (int i = 2; i< vertices.size(); i+=2) {
                if (edgeThatGotToVertex.get(i) != null && edgeThatGotToVertex.get(i+1) == null) {
                    separatorIds.add(vertices.get(i).id);
                }
            }
            return false;
        }

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

    public int increaseCurrentFlow(int incrementLimit) {
        int result = 0;
        for (int i = 0; i< incrementLimit; i++) {
            boolean succeeded = increaseFlowByOne();
            if (!succeeded) {
                break;
            }
            result = i+1;
        }

        return result;
    }

    public List<Integer> getSeparatorIds() throws FlowNotMaximalException {
        if (separatorIds == null) throw new FlowNotMaximalException();
        return separatorIds;
    }
}
