package treewidth_approximation;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProvider;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProviderImpl;
import treewidth_approximation.logic.steiner.SteinerInstance;
import treewidth_approximation.view.JungGraphShower;
import treewidth_approximation.view.PrefuseGraphShower;

import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        RandomGraphProvider provider = new RandomGraphProviderImpl(new Random(0));

        int vertices = 100;
        double edgeChance = 1.75/vertices;

        TAGraph g = provider.getRandom(vertices, edgeChance).splitIntoConnectedComponents().get(0);
        g.normalizeIds();

        Set<Integer> terminals = provider.getRandomVertexSubset(g, 10).stream()
                .map(TAVertex::getId).collect(Collectors.toSet());
        SteinerInstance instance = new SteinerInstance(g, terminals, new HashMap<>());

        PrefuseGraphShower.showSteinerInstance(instance, new ArrayList<>(), "Steiner instance");
//        JungGraphShower.showGraphWithIds(g, "graph");
    }
}
