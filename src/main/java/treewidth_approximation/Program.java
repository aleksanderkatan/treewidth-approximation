package treewidth_approximation;

import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProvider;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProviderImpl;
import treewidth_approximation.logic.steiner.SteinerInstance;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.NiceTreeDecomposition;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.NiceTreeDecompositionGenerator;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionFinder;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionVerifier;
import treewidth_approximation.view.PrefuseGraphShower;

import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        RandomGraphProvider provider = new RandomGraphProviderImpl(new Random(0));

        int expectedTreeWidth = 4;
//        TAGraph g = provider.getGridSubgraph(15, expectedTreeWidth, 0.8);
//        TAGraph g = provider.getRandom(100, 0.01);
        TAGraph g = provider.getRandom(8, 0.8);
        g = g.splitIntoConnectedComponents(true).get(0);
        Set<Integer> terminals = provider.getRandomVertexSubset(g, 3).stream().map(TAVertex::getId).collect(Collectors.toSet());
        SteinerInstance steiner = new SteinerInstance(g, terminals, new HashMap<>());

        steiner.getSelected().add(new TAEdge(4, 3));
        PrefuseGraphShower.showSteinerInstance(steiner, "Graph of expected treewidth " + expectedTreeWidth);
//        PrefuseGraphShower.showGraphWithIds(steiner.getGraph(), new HashSet<>(), "graf");

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

        NiceTreeDecomposition niceDecomposition = NiceTreeDecompositionGenerator.generate(r.decomposition, steiner);
        TreeDecompositionVerifier.verify(niceDecomposition, g, 4 * (expectedTreeWidth + 1), true);
        PrefuseGraphShower.showTreeDecomposition(niceDecomposition, "Nice decomposition");
        niceDecomposition.getRoot().compute();
        System.out.println("Finished");
    }
}
