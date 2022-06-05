package treewidth_approximation;

import org.javatuples.Pair;
import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.graph.TAVertex;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProvider;
import treewidth_approximation.logic.random_graph_provider.RandomGraphProviderImpl;
import treewidth_approximation.logic.separator_finder.SeparatorFinder;
import treewidth_approximation.logic.steiner.SteinerInstance;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionFinder;
import treewidth_approximation.view.PrefuseGraphShower;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) throws SeparatorFinder.NoSeparatorExistsException {
        RandomGraphProvider provider = new RandomGraphProviderImpl(new Random(0));

        int vertices = 100;
        double edgeChance = 1.75/vertices;

        TAGraph g = provider.getRandom(vertices, edgeChance).splitIntoConnectedComponents().get(0);
        g.normalizeIds();

        Set<Integer> terminals = provider.getRandomVertexSubset(g, 10).stream()
                .map(TAVertex::getId).collect(Collectors.toSet());
        SteinerInstance instance = new SteinerInstance(g, terminals, new HashMap<>());

        PrefuseGraphShower.showSteinerInstance(instance, "Steiner instance");

        Set<Pair<Integer, Integer>> selected = new HashSet<>();
        selected.add(new Pair<>(49, 1));
        selected.add(new Pair<>(35, 49));
        instance.setSelected(selected);

        PrefuseGraphShower.showSteinerInstance(instance, "Steiner instance with selected");


        PrefuseGraphShower.showGraphWithIds(g, terminals, "Ids with terminals");
        PrefuseGraphShower.showGraphWithIds(g, new HashSet<>(), "Ids without terminals");

        Set<Integer> separator = SeparatorFinder.findSeparatorIds(g, terminals, 10);
        PrefuseGraphShower.showGraphWithShapes(g, terminals, separator, "Separator");

        TreeDecompositionFinder.Result result = TreeDecompositionFinder.findDecomposition(g, 5);
        if (result.successful) {
            PrefuseGraphShower.showTreeDecomposition(result.decomposition, "Tree decomposition");
        }
    }
}
