package treewidth_approximation.logic.steiner;

import treewidth_approximation.logic.misc.MapNormalizer;
import treewidth_approximation.logic.misc.Partition;
import treewidth_approximation.logic.misc.serialization.StringWriter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SubProblem {
    private final Set<Integer> X;
    private final Partition<Integer> partition;

    public SubProblem(Set<Integer> X, Partition<Integer> partition) {
        this.X = new HashSet<>(X);
        // !! very important!
        // order of blocks should not matter in a steiner subProblem!
        partition = new Partition<>(MapNormalizer.normalize(partition.getElements()));
        this.partition = partition;
    }

    public Set<Integer> getX() {return X;}

    public Partition<Integer> getPartition() {return partition;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        SubProblem other = (SubProblem) o;
        return X.equals(other.X) && partition.equals(other.partition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, partition);
    }

    @Override
    public String toString() {
        return "X: " + StringWriter.writeSet(X) + ", " + partition.toString();
    }
}
