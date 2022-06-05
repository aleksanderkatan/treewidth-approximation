package treewidth_approximation.logic.prefuse;

import prefuse.data.Edge;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GraphConverter {
    public static Graph convert(TAGraph graph, List<Set<Integer>> colors, List<Set<Integer>> shapes) {
        Table nodeTable = new Table();

//        nodeTable.addColumn(Graph.DEFAULT_NODE_KEY, int.class );
        nodeTable.addColumn("color", Integer.class);
        nodeTable.addColumn("shape", Integer.class);
        nodeTable.addColumn("label", String.class);

        Table edgeTable = new Table();

        edgeTable.addColumn(Graph.DEFAULT_SOURCE_KEY, int.class);
        edgeTable.addColumn(Graph.DEFAULT_TARGET_KEY, int.class);
        edgeTable.addColumn("color", Integer.class);
        edgeTable.addColumn("edge_label", int.class);

        Graph res = new Graph(nodeTable, edgeTable,false);

        Node[] nodes = new Node[graph.getVertices().size()];

        List<TAVertex> originalVertices = new ArrayList<>(graph.getVertices());
        for (TAVertex originalVertex : originalVertices) {
            Node node = res.addNode();
            nodes[originalVertex.getId()] = node;
            node.set("color", 0);
            node.set("shape", 0);
//            node.set(Graph.DEFAULT_NODE_KEY, originalVertex.getId());
            for (int j = 0; j < colors.size(); j++) {
                if (colors.get(j).contains(originalVertex.getId())) {
                    node.set("color", j+1);
                    break;
                }
            }
            for (int j = 0; j < shapes.size(); j++) {
                if (shapes.get(j).contains(originalVertex.getId())) {
                    node.set("shape", j+1);
                    break;
                }
            }
            node.set("label", Integer.toString(originalVertex.getId()));
        }

        for (TAVertex v : originalVertices) {
            for (TAVertex w : v.getNeighbours()) {
                int id1 = v.getId();
                int id2 = w.getId();
                if (id1 < id2) {
                    res.addEdge(id1, id2);
                    Edge e = res.getEdge(nodes[id1], nodes[id2]);
                    e.set("edge_label", 2);
                    e.set("color", (id1^id2)%2);
                    e.set(Graph.DEFAULT_SOURCE_KEY, id1);
                    e.set(Graph.DEFAULT_TARGET_KEY, id2);
                }
            }
        }
        return res;
    }
}
