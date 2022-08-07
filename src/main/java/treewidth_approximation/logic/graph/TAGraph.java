package treewidth_approximation.logic.graph;

import java.util.List;
import java.util.Set;

public interface TAGraph {
    Set<TAVertex> getVertices();

    Set<Integer> getVerticesIds();

    TAVertex getVertexById(int id);

    TAVertex addVertex(int id);

    int getVertexAmount();

    void addEdge(int firstId, int secondId);

    boolean hasEdge(int firstId, int secondId);

    int getEdgeAmount();

    TAGraph copyRestricting(Set<Integer> restricted);

    TAGraph subgraphInducedBy(Set<Integer> vertices);

    List<TAGraph> splitIntoConnectedComponents(boolean normalizeIds);

    TAGraph copyNormalized();
}
