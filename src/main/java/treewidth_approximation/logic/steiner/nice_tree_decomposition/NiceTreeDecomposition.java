package treewidth_approximation.logic.steiner.nice_tree_decomposition;

import treewidth_approximation.logic.graph.TAEdge;
import treewidth_approximation.logic.misc.Partition;
import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;
import treewidth_approximation.logic.tree_decomposition.TreeDecomposition;

import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;

public class NiceTreeDecomposition implements TreeDecomposition {
    private final NiceDecompositionNode root;

    public NiceTreeDecomposition(NiceDecompositionNode root) {
        this.root = root;
    }

    public NiceDecompositionNode getRoot() {
        return root;
    }

    public Set<TAEdge> solve() {
        root.compute();
        int U = getRoot().getVertices().iterator().next();
        Partition<Integer> p = new Partition<>(Map.ofEntries(
                entry(U, 0)
        ));
        SubProblem subProblem = new SubProblem(Set.of(), p);
        SubSolution subSolution = getRoot().getSolutions().getSolution(subProblem);
        if (!subSolution.isValid()) {
            System.out.println("ERROR: SubSolution in root invalid!!");
        }
        return subSolution.collectEdges();
    }

    @Override
    public TreeDecomposition copy() {
        throw new RuntimeException("Method copy not yet implemented");
    }
}
