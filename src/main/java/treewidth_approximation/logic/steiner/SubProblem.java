package treewidth_approximation.logic.steiner;

import treewidth_approximation.logic.misc.Partition;
import treewidth_approximation.logic.misc.StringUtilities;

import java.util.Set;

public class SubProblem {
    private final Set<Integer> X;
    private final Partition<Integer> partition;

    public SubProblem(Set<Integer> X, Partition<Integer> partition) {
        this.X = X;
        this.partition = partition;
    }

    public Set<Integer> getX() { return X; }
    public Partition<Integer> getPartition() { return partition; }

    @Override
    public String toString() {
        return "X: " + StringUtilities.setToString(X) + ", partition: " + partition.toString();
    }
}
