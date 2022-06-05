package treewidth_approximation.logic.steiner;

import java.util.List;
import java.util.Set;

public class Partition {
    private final List<Set<Integer>> sets;

    public Partition(List<Set<Integer>> sets) {
        this.sets = sets;
    }

    public List<Set<Integer>> getSets() {
        return sets;
    }
}
