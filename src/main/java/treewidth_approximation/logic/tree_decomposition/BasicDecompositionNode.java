package treewidth_approximation.logic.tree_decomposition;

import treewidth_approximation.logic.misc.serialization.StringWriter;

import java.util.HashSet;
import java.util.Set;

public class BasicDecompositionNode implements DecompositionNode {
    private final Set<Integer> vertices;
    private final Set<DecompositionNode> children;

    public BasicDecompositionNode(Set<Integer> vertices) {
        this.vertices = vertices;
        this.children = new HashSet<>();
    }

    @Override
    public Set<Integer> getVertices() {
        return vertices;
    }

    @Override
    public String getLabel() {
        return StringWriter.writeSet(vertices);
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
    public void removeChild(DecompositionNode child) {
        children.remove(child);
    }
}
