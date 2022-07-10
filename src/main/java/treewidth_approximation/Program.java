package treewidth_approximation;

import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAGraphImpl;
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

import java.util.*;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        int expectedTreeWidth = 1;
        int vertices = 200;
        double edgeChance = 2.05/vertices;
        int terminalsAmount = 15;

        RandomGraphProvider provider = new RandomGraphProviderImpl(new Random(67));

        TAGraph g = provider.getRandom(vertices, edgeChance);
        g = g.splitIntoConnectedComponents(true).get(0);
        Set<Integer> terminals = provider.getRandomVertexSubset(g, terminalsAmount).stream().map(TAVertex::getId).collect(Collectors.toSet());

        SteinerInstance steiner = new SteinerInstance(g, terminals, new HashMap<>());

        PrefuseGraphShower.showSteinerInstance(steiner, "Steiner instance");
        TreeDecompositionFinder.Result r = TreeDecompositionFinder.findDecomposition(g, expectedTreeWidth);
        if (!r.successful) {
            return;
        }

        NiceTreeDecomposition niceDecomposition = NiceTreeDecompositionGenerator.generate(r.decomposition, steiner);
        TreeDecompositionVerifier.verify(niceDecomposition, g, 4 * (expectedTreeWidth + 1), true);
//        PrefuseGraphShower.showTreeDecomposition(niceDecomposition, "Nice decomposition");

        SteinerSolver solver = new SteinerSolver(steiner, niceDecomposition);
        SteinerInstance solved = solver.solve();

        PrefuseGraphShower.showSteinerInstance(solved, "Solved Steiner");
    }
}
