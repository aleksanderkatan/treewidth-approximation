package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.steiner.PartialSolution;
import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.NiceDecompositionNode;
import treewidth_approximation.logic.tree_decomposition.DecompositionNode;

import java.util.*;

public abstract class NiceDecompositionNodeImpl implements NiceDecompositionNode {
    protected final Set<DecompositionNode> children;
    protected final Set<Integer> vertices;
    protected final PartialSolution solution;

    public NiceDecompositionNodeImpl(Set<Integer> vertices) {
        this.children = new HashSet<>();
        this.vertices = vertices;
        this.solution = new PartialSolution();
    }

    @Override
    public Set<Integer> getVertices() {
        return vertices;
    }

    @Override
    public Set<DecompositionNode> getChildren() {
        return children;
    }

    @Override
    public void addChild(DecompositionNode child) {
        children.add(child);
    }

    @Override
    public PartialSolution getSolutions() {
        return solution;
    }

    @Override
    public void compute() {

    }

    @Override
    public String getLabel() {
        return "NiceDecompositionNode - " + StringUtilities.setToString(vertices);
    }

    @Override
    public void removeChild(DecompositionNode child) {
        children.remove(child);
    }
}
