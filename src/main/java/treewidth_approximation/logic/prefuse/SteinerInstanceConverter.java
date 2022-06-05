package treewidth_approximation.logic.prefuse;

import org.javatuples.Pair;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Table;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.steiner.SteinerInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SteinerInstanceConverter {
    public static Graph convert(SteinerInstance instance, List<Pair<Integer, Integer>> highlightedEdges) {
        Graph res = GraphConverter.convert(instance.getGraph(), new ArrayList<>(), new ArrayList<>());
//
//        G
//        List<TAVertex> originalVertices = new ArrayList<>(graph.getVertices());
//        for (TAVertex originalVertex : originalVertices) {
//            Node node = res.addNode();
//            node.set("color", 0);
//            node.set("shape", 0);
//            for (int j = 0; j < colors.size(); j++) {
//                if (colors.get(j).contains(originalVertex.getId())) {
//                    node.set("color", j+1);
//                    break;
//                }
//            }
//            for (int j = 0; j < shapes.size(); j++) {
//                if (shapes.get(j).contains(originalVertex.getId())) {
//                    node.set("shape", j+1);
//                    break;
//                }
//            }
//            node.set("label", Integer.toString(originalVertex.getId()));
//        }
//
//        for (TAVertex v : originalVertices) {
//            for (TAVertex w : v.getNeighbours()) {
//                int id1 = v.getId();
//                int id2 = w.getId();
//                if (id1 < id2) {
//                    res.addEdge(id1, id2);
//                }
//            }
//        }
        return res;
    }
}
