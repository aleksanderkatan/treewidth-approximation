package treewidth_approximation.logic.steiner;

import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.NiceTreeDecomposition;

import java.util.Set;

public class SteinerSolver {
    private final SteinerInstance instance;
    private final NiceTreeDecomposition niceTreeDecomposition;

    // TODO: put calculating decomposition in solve()
    public SteinerSolver(SteinerInstance instance, NiceTreeDecomposition niceTreeDecomposition) {
        this.instance = instance;
        this.niceTreeDecomposition = niceTreeDecomposition;
    }

    public SteinerInstance solve() {
        Set<TAEdge> edges = niceTreeDecomposition.solve();
        instance.setSelected(edges);
        return instance;
    }
}
