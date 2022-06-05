package treewidth_approximation.logic.jung;

public class JungEdge {
    private final boolean highlighted;

    public JungEdge(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public boolean isHighlighted() { return highlighted; }
}
