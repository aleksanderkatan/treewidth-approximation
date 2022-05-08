package treewidth_approximation;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProvider;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProviderImpl;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionFinder;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionVerifier;
import treewidth_approximation.view.GraphShower;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Program {
    public static void main(String[] args) {
        RandomGraphProvider provider = new RandomGraphProviderImpl(new Random(0));

//        int bagSize = 3+1;
        int bagSize = 2+1;
//        int bagSize = 1+1;
        int vertices = 200;

//        TAGraph g = provider.getRandom(vertices, 1.75/vertices).splitIntoConnectedComponents().get(0);
        TAGraph g = provider.getRandom(vertices, 1.75/vertices);
        g.normalizeIds();

        GraphShower.showGraphWithIds(g, new ArrayList<>(), "Graph");

        TreeDecompositionFinder finder = new TreeDecompositionFinder(g);
        TreeDecompositionFinder.Result result = finder.findDecomposition(bagSize - 1);

        if (result.successful) {
            GraphShower.showTreeDecomposition(result.decomposition, "Tree decomposition");
            TreeDecompositionVerifier.verify(result.decomposition, g, bagSize * 4, true);
        } else {
            System.out.println("Found inseparable set");
            GraphShower.showGraphWithIds(g, List.of(result.setWithoutSeparator), "Set without balanced " + bagSize + " separator");
            for (Integer v : result.setWithoutSeparator) {
                System.out.print(v.toString() + " ");
            }
            System.out.println();
        }
    }
}