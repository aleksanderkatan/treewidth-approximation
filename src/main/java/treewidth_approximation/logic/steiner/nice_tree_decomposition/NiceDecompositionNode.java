package treewidth_approximation.logic.steiner.nice_tree_decomposition;

import treewidth_approximation.logic.steiner.PartialSolution;
import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;
import treewidth_approximation.logic.tree_decomposition.DecompositionNode;

import java.util.Map;
import java.util.Set;

public interface NiceDecompositionNode extends DecompositionNode {
    void compute();
    void computeSingular(SubProblem subProblem);
    PartialSolution getSolutions();
}
