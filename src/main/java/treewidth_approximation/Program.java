package treewidth_approximation;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProvider;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProviderImpl;
import treewidth_approximation.logic.separator_finder.SeparatorFinder;
import treewidth_approximation.logic.tree_decomposition.TreeDecomposition;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionFinder;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionVerifier;
import treewidth_approximation.view.GraphShower;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        RandomGraphProvider provider = new RandomGraphProviderImpl(new Random(0));

        int x = 3;
        int y = 10;
        int bagSize = x+1;

        TAGraph g = provider.getGridSubgraph(x, y, 0.9).splitIntoConnectedComponents().get(0);
        g.normalizeIds();

        GraphShower.showGraphWithIds(g, new ArrayList<>(), "Graph");


        TreeDecompositionFinder finder = new TreeDecompositionFinder(g);
        TreeDecompositionFinder.Result result = finder.findDecomposition(bagSize);

        if (result.successful) {
            GraphShower.showTreeDecomposition(result.decomposition, "Tree decomposition");
            TreeDecompositionVerifier.verify(result.decomposition, g, bagSize*4, true);
        } else {
            System.out.println("Found unseparable set");
            for (Integer v : result.setWithoutSeparator) {
                System.out.print(v.toString() + " ");
            }
            System.out.println();
        }
    }


}