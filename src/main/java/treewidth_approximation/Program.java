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
        RandomGraphProvider provider = new RandomGraphProviderImpl(new Random(0));

        int expectedTreeWidth = 4;
//        TAGraph g = provider.getGridSubgraph(15, expectedTreeWidth, 0.8);
//        TAGraph g = provider.getRandom(100, 0.01);
        // 9 - ok, 10 - too much

        TAGraph g = new TAGraphImpl();
        g.addVertex(0);
        g.addVertex(1);
        g.addVertex(2);
        g.addVertex(3);
        g.addVertex(4);
        g.addEdge(1, 2);
        g.addEdge(0, 2);
        g.addEdge(2, 4);
        g.addEdge(4, 3);
        Set<Integer> terminals = Set.of(2, 4, 3);


//        TAGraph g = provider.getRandom(6, 2.0/6);

//        TAGraph g = provider.getRandom(3, 1);
//        g = g.splitIntoConnectedComponents(true).get(0);
//        Set<Integer> terminals = provider.getRandomVertexSubset(g, 2).stream().map(TAVertex::getId).collect(Collectors.toSet());

        SteinerInstance steiner = new SteinerInstance(g, terminals, new HashMap<>());

        PrefuseGraphShower.showSteinerInstance(steiner, "Steiner instance");
//        PrefuseGraphShower.showGraphWithIds(steiner.getGraph(), new HashSet<>(), "graf");

        TreeDecompositionFinder.Result r = TreeDecompositionFinder.findDecomposition(g, expectedTreeWidth);
        if (!r.successful) {
            PrefuseGraphShower.showGraphWithIds(g, r.setWithoutSeparator, "Set without balanced separator of order " + (expectedTreeWidth + 1));
            return;
        }

        NiceTreeDecomposition niceDecomposition = NiceTreeDecompositionGenerator.generate(r.decomposition, steiner);
        TreeDecompositionVerifier.verify(niceDecomposition, g, 4 * (expectedTreeWidth + 1), true);
        PrefuseGraphShower.showTreeDecomposition(niceDecomposition, "Nice decomposition");

        SteinerSolver solver = new SteinerSolver(steiner, niceDecomposition);
        SteinerInstance solved = solver.solve();

        PrefuseGraphShower.showSteinerInstance(solved, "Solved Steiner");
    }
}
