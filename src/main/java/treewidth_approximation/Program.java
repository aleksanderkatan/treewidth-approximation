package treewidth_approximation;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProvider;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProviderImpl;
import treewidth_approximation.logic.separator_finder.SeparatorFinder;
import treewidth_approximation.view.GraphShower;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        RandomGraphProvider provider = new RandomGraphProviderImpl(new Random(0));

        int x = 4;
        int y = 10;
        int WSize = 15;
        int separatorSize = ((WSize+2)/3);

        TAGraph g = provider.getGridSubgraph(x, y, 0.6).splitIntoConnectedComponents().get(0);
        g.normalizeIds();
        Set<Integer> W = provider.getRandomVertexSubset(g, WSize).stream().map(TAVertex::getId).collect(Collectors.toSet());

        GraphShower.showGraphWithShapes(g, List.of(W), null, "Graph and W");
//        GraphShower.showGraphWithIds(g, List.of(W), "Ids");

        System.out.println("Attempting to find a separator of size " + separatorSize);
        Set<Integer> separator = null;
        try {
            separator = SeparatorFinder.findSeparatorIds(g, W, separatorSize);
        } catch (SeparatorFinder.NoSeparatorExistsException e) {
            System.out.println("No separator of order " + separatorSize);
        }
        if (separator != null) {
            System.out.println("Found separator of order " + separator.size());
            GraphShower.showGraphWithShapes(g, List.of(W), List.of(separator), "Separator");
        }
    }


}