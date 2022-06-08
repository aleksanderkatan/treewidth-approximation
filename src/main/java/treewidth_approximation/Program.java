package treewidth_approximation;

import org.javatuples.Pair;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProvider;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProviderImpl;
import treewidth_approximation.logic.separator_finder.SeparatorFinder;
import treewidth_approximation.logic.steiner.SteinerInstance;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionFinder;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionVerifier;
import treewidth_approximation.view.PrefuseGraphShower;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        RandomGraphProvider provider = new RandomGraphProviderImpl(new Random(0));

        int gridSize = 4;
        TAGraph g = provider.getGridSubgraph(40, gridSize, 0.8);
        g = g.splitIntoConnectedComponents(true).get(0);

        TreeDecompositionFinder.Result r = TreeDecompositionFinder.findDecomposition(g, gridSize);

        if (!r.successful) {
            PrefuseGraphShower.showGraphWithShapes(g, r.setWithoutSeparator, new HashSet<>(), "Inseparable set");
            return;
        }
        PrefuseGraphShower.showGraphWithIds(g, new HashSet<>(), "Graph");
        r.decomposition.collapse();
        if (TreeDecompositionVerifier.verify(r.decomposition, g, 4*(gridSize+1), true)) {
            PrefuseGraphShower.showTreeDecomposition(r.decomposition, "Correct tree decomposition");
        } else {
            PrefuseGraphShower.showTreeDecomposition(r.decomposition, "Incorrect tree decomposition");
        }
    }
}
