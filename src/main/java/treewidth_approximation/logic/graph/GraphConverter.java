package treewidth_approximation.logic.graph;

import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GraphConverter {
    public static Graph convert(TAGraph graph, List<Set<Integer>> highlighted) {
        Table table = new Table();
        table.addColumn("type", Integer.class);
        Graph res = new Graph(table, false);

        List<TAVertex> originalVertices = new ArrayList<>(graph.getVertices());
        for (TAVertex originalVertex : originalVertices) {
            Node node = res.addNode();
            node.set("type", 0);
            for (int j = 0; j < highlighted.size(); j++) {
                if (highlighted.get(j).contains(originalVertex.getId())) {
                    node.set("type", j+1);
                    break;
                }
            }
        }

        for (TAVertex v : originalVertices) {
            for (TAVertex w : v.getNeighbours()) {
                int id1 = v.getId();
                int id2 = w.getId();
                if (id1 < id2) {
                    res.addEdge(id1, id2);
                }
            }
        }
        return res;
    }
}
