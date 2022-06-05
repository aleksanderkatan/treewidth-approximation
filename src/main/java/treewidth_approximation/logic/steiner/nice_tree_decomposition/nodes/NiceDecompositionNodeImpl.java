package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.NiceDecompositionNode;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.NiceTreeDecomposition;

import java.util.*;

public abstract class NiceDecompositionNodeImpl implements NiceDecompositionNode {
    protected final Set<NiceDecompositionNode> children;
    protected final Set<Integer> vertices;

    public NiceDecompositionNodeImpl(Set<Integer> vertices) {
        this.children = new HashSet<>();
        this.vertices = vertices;
    }

    @Override
    public Set<Integer> getVertices() {
        return vertices;
    }

    @Override
    public Set<NiceDecompositionNode> getChildren() {
        return children;
    }

    @Override
    public void addChild(NiceDecompositionNode child) {
        children.add(child);
    }

    @Override
    public Map<SubProblem, SubSolution> getSolutions() {
        return null;
    }
}
