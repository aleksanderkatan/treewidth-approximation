package treewidth_approximation.graph;

import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;

public class GraphConverter {
    public static Graph convert(TAGraph graph) {
        Table table = new Table();
        table.addColumn("type", Integer.class);
        Graph res = new Graph(table, false);

        for (int i = 0; i< graph.getVertices().size(); i++) {
            Node node = res.addNode();
            node.set("type", i);
        }

        for (TAVertex v : graph.getVertices()) {
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
