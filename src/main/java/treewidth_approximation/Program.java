package treewidth_approximation;

import org.javatuples.Pair;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProvider;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProviderImpl;
import treewidth_approximation.logic.separator_finder.SeparatorFinder;
import treewidth_approximation.logic.steiner.SteinerInstance;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.NiceTreeDecompositionGenerator;
import treewidth_approximation.logic.tree_decomposition.TreeDecomposition;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionFinder;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionVerifier;
import treewidth_approximation.view.PrefuseGraphShower;

import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        RandomGraphProvider provider = new RandomGraphProviderImpl(new Random(0));

        int expectedTreeWidth = 4;
        TAGraph g = provider.getGridSubgraph(250, expectedTreeWidth, 0.8);
        g = g.splitIntoConnectedComponents(true).get(0);

        PrefuseGraphShower.showGraphWithIds(g, new HashSet<>(), "Graph of expected treewidth " + expectedTreeWidth);
        TreeDecompositionFinder.Result r = TreeDecompositionFinder.findDecomposition(g, expectedTreeWidth);
        if (!r.successful) {
            PrefuseGraphShower.showGraphWithIds(g, r.setWithoutSeparator, "Set without balanced separator of order " + (expectedTreeWidth + 1));
            return;
        }

        if (TreeDecompositionVerifier.verify(r.decomposition, g, 4 * (expectedTreeWidth + 1), true)) {
//            PrefuseGraphShower.showTreeDecomposition(r.decomposition, "Correct decomposition with treewidth " + (4 * (expectedTreeWidth + 1) - 1));
        } else {
            PrefuseGraphShower.showTreeDecomposition(r.decomposition, "Incorrect tree decomposition");
            return;
        }

        TreeDecomposition niceDecomposition = NiceTreeDecompositionGenerator.generate(r.decomposition, null);
        TreeDecompositionVerifier.verify(niceDecomposition, g, 4 * (expectedTreeWidth + 1), true);
        PrefuseGraphShower.showTreeDecomposition(niceDecomposition, "Nice decomposition");
    }
}
