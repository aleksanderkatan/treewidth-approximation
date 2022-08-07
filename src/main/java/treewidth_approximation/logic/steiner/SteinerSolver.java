package treewidth_approximation.logic.steiner;

import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.steiner.extended_nice_tree_decomposition.NiceTreeDecomposition;
import treewidth_approximation.logic.steiner.extended_nice_tree_decomposition.NiceTreeDecompositionGenerator;
import treewidth_approximation.logic.tree_decomposition.TreeDecomposition;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionFinder;
import treewidth_approximation.logic.tree_decomposition.TreeDecompositionVerifier;

import java.util.Set;

public class SteinerSolver {
    public static void solve(SteinerInstance instance, NiceTreeDecomposition niceTreeDecomposition) {
        Set<TAEdge> edges = niceTreeDecomposition.solve();
        instance.setSelected(edges);
    }

    public static void solve(SteinerInstance instance, int treeWidthLimit) {
        for (int i = 1; i <= treeWidthLimit; i++) {
            // attempt to find tree-decomposition of width 4*i+3
            TreeDecompositionFinder.Result r = TreeDecompositionFinder
                    .findDecomposition(instance.getGraph(), i);

            if (!r.successful) {
                // tree-width greater than i
                // r.setWithoutSeparator - set without balanced separator of order i+1
                continue;
            }
            TreeDecomposition decomposition = r.decomposition;
            // verify, that resulting decomposition is correct
            assert TreeDecompositionVerifier.verify(decomposition, instance.getGraph(), i * 4 + 3, false);

            // generate niceDecomposition from decomposition and solve
            NiceTreeDecomposition niceDecomposition = NiceTreeDecompositionGenerator.generate(r.decomposition, instance);
            SteinerSolver.solve(instance, niceDecomposition);

            break;
        }
    }
}
