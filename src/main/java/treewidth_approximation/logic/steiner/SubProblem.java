package treewidth_approximation.logic.steiner;

import java.util.Set;

public class SubProblem {
    private final Set<Integer> X;
    private final Partition partition;

    public SubProblem(Set<Integer> X, Partition partition) {
        this.X = X;
        this.partition = partition;
    }

    public Set<Integer> getX() { return X; }
    public Partition getPartition() { return partition; }
}
