package treewidth_approximation.logic.random_graph_provider;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAHashGraph;
import treewidth_approximation.logic.graph.TAVertex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomGraphProviderImpl implements RandomGraphProvider {
    private final Random random;

    public RandomGraphProviderImpl(Random random) {
        this.random = random;
    }

    @Override
    public TAGraph getRandom(int vertices, double edgeChance) {
        TAGraph graph = new TAHashGraph();
        for (int i = 0; i < vertices; i++) {graph.addVertex(i);}
        for (int i = 0; i < vertices; i++) {
            for (int j = i + 1; j < vertices; j++) {
                if (random.nextDouble() <= edgeChance) {
                    graph.addEdge(i, j);
                }
            }
        }
        return graph;
    }

    @Override
    public TAGraph getGridSubgraph(int x, int y, double edgeChance) {
        TAGraph graph = new TAHashGraph();
        for (int i = 0; i < x * y; i++) {graph.addVertex(i);}
        // horizontal
        for (int i = 0; i < x - 1; i++) {
            for (int j = 0; j < y; j++) {
                int a = i + j * x;
                int b = a + 1;
                if (random.nextDouble() <= edgeChance) {
                    graph.addEdge(a, b);
                }
            }
        }
        // vertical
        for (int j = 0; j < y - 1; j++) {
            for (int i = 0; i < x; i++) {
                int a = i + j * x;
                int b = a + x;
                if (random.nextDouble() <= edgeChance) {
                    graph.addEdge(a, b);
                }
            }
        }
        return graph;
    }

    @Override
    public Set<TAVertex> getRandomVertexSubset(TAGraph graph, int size) {
        Set<TAVertex> res = new HashSet<>();
        List<TAVertex> vertices = new ArrayList<>(graph.getVertices());

        for (int i = 0; i < size; i++) {
            int result = random.nextInt(vertices.size() - i);
            int otherPos = vertices.size() - i - 1;
            TAVertex temp = vertices.get(otherPos);
            vertices.set(otherPos, vertices.get(result));
            vertices.set(result, temp);

            res.add(vertices.get(otherPos));
        }
        return res;
    }
}
