package treewidth_approximation;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProvider;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProviderImpl;
import treewidth_approximation.logic.steiner.SteinerInstance;
import treewidth_approximation.logic.steiner.SteinerSolver;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.NiceTreeDecomposition;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.NiceTreeDecompositionGenerator;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionFinder;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionVerifier;
import treewidth_approximation.view.PrefuseGraphShower;

import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        int expectedTreeWidth = 5;
//        int vertices = 200;
//        double edgeChance = 2.05/vertices;
//        int terminalsAmount = 15;

        RandomGraphProvider provider = new RandomGraphProviderImpl(new Random(67));

        TAGraph g = provider.getGridSubgraph(expectedTreeWidth, 20, 0.94);
        g = g.splitIntoConnectedComponents(true).get(0);
//        Set<Integer> terminals = provider.getRandomVertexSubset(g, terminalsAmount).stream().map(TAVertex::getId).collect(Collectors.toSet());

//        SteinerInstance steiner = new SteinerInstance(g, terminals, new HashMap<>());

//        PrefuseGraphShower.showSteinerInstance(steiner, "Steiner instance");
        TreeDecompositionFinder.Result r = TreeDecompositionFinder.findDecomposition(g, expectedTreeWidth);
        if (!r.successful) {
            System.out.println("Unsuccessful call.");
            return;
        }
        TreeDecompositionVerifier.verify(r.decomposition, g, expectedTreeWidth*4+3, true);
        PrefuseGraphShower.showGraphWithIds(g, Set.of(), "Graph");
        PrefuseGraphShower.showTreeDecomposition(r.decomposition, "Valid decomposition");

//        NiceTreeDecomposition niceDecomposition = NiceTreeDecompositionGenerator.generate(r.decomposition, steiner);
//        TreeDecompositionVerifier.verify(niceDecomposition, g, 4 * (expectedTreeWidth + 1), true);
//        PrefuseGraphShower.showTreeDecomposition(niceDecomposition, "Nice decomposition");

//        SteinerSolver solver = new SteinerSolver(steiner, niceDecomposition);
//        SteinerInstance solved = solver.solve();

//        PrefuseGraphShower.showSteinerInstance(solved, "Solved Steiner");
    }
}
