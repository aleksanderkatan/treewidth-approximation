package treewidth_approximation.logic.random_graph_provider;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;

import java.util.Set;

public interface RandomGraphProvider {
    TAGraph getRandom(int vertices, double edgeChance);
    TAGraph getGridSubgraph(int x, int y, double edgeChance);
    Set<TAVertex> getRandomVertexSubset(TAGraph graph, int size);
}
