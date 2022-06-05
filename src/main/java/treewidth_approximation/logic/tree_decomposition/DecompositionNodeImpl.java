package treewidth_approximation.logic.tree_decomposition;

import treewidth_approximation.logic.misc.StringUtilities;

import java.util.HashSet;
import java.util.Set;

public class DecompositionNodeImpl implements DecompositionNode {
    private final Set<Integer> vertices;
    private final Set<DecompositionNode> children;

    public DecompositionNodeImpl(Set<Integer> vertices) {
        this.vertices = vertices;
        this.children = new HashSet<>();
    }

    @Override
    public Set<Integer> getVertices() {
        return vertices;
    }

    @Override
    public String getLabel() {
        return StringUtilities.getNodeLabel(this);
    }

    @Override
    public Set<DecompositionNode> getChildren() {
        return children;
    }

    @Override
    public void addChild(DecompositionNode child) {
        children.add(child);
    }
}
