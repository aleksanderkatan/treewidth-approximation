package treewidth_approximation.logic.jung;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphConverter {
    public static Graph<JungVertex, JungEdge> convert(TAGraph graph) {
        Graph<JungVertex, JungEdge> result = new UndirectedSparseGraph<>();
        Map<Integer, JungVertex> nodes = new HashMap<>();

        for (TAVertex vertex : graph.getVertices()) {
            JungVertex newVertex = new JungVertex(vertex.getId());
            nodes.put(vertex.getId(), newVertex);
            result.addVertex(newVertex);
        }
        for (TAVertex vertex : graph.getVertices()) {
            for (TAVertex neighbour : vertex.getNeighbours()) {
                int id1 = vertex.getId();
                int id2 = neighbour.getId();
                if (id1 > id2) continue;
                result.addEdge(new JungEdge(false), nodes.get(id1), nodes.get(id2));
            }
        }

        return result;
    }
}
