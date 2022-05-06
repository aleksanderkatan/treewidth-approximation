package treewidth_approximation.separator_finder;

import treewidth_approximation.graph.TAGraph;
import treewidth_approximation.graph.TAVertex;

import java.util.Set;

public class SeparatorFinder {
    Set<TAVertex> findSeparator(TAGraph graph, Set<TAVertex> A, Set<TAVertex> B, int maxSize) {
        // finds a separator of A and B, that does not contain vertices from either of the sets
        // returns either the separator or null if no separator exists

//        // check if there are edges between sets
//        for (TAVertex v : A) {
//            for (TAVertex neighbour : v.getNeighbours()) {
//                if (B.contains(neighbour))
//                    return null;
//            }
//        }





        return null;
    }
}
