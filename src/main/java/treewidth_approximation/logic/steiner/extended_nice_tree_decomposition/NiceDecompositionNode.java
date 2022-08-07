package treewidth_approximation.logic.steiner.extended_nice_tree_decomposition;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.steiner.PartialSolution;
import treewidth_approximation.logic.steiner.SteinerInstance;
import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;
import treewidth_approximation.logic.tree_decomposition.DecompositionNode;

public interface NiceDecompositionNode extends DecompositionNode {
    void setInstance(SteinerInstance instance);

    void updateSubgraph(TAGraph childSubgraph);

    TAGraph getSubgraph();

    void compute();

    SubSolution computeSingular(SubProblem subProblem);

    PartialSolution getSolutions();
}
