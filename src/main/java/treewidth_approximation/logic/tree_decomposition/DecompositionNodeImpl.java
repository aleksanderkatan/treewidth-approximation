package treewidth_approximation.logic.tree_decomposition;

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
    public Set<DecompositionNode> getChildren() {
        return children;
    }

    @Override
    public void addChild(DecompositionNode child) {
        children.add(child);
    }
}
