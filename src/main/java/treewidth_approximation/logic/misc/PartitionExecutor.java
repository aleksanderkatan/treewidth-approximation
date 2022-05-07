package treewidth_approximation.logic.misc;

import java.util.*;
import java.util.function.Function;

public class PartitionExecutor<T, S>{
    private final List<T> elements;
    List<List<T>> partitions;
    Function<List<List<T>>, S> action;

    public PartitionExecutor(List<T> elements, int partitionsAmount, Function<List<List<T>>, S> action) {
        this.elements = elements;
        partitions = new ArrayList<>();
        for (int i = 0; i< partitionsAmount; i++) {
            partitions.add(new ArrayList<>());
        }
        this.action = action;
    }
    
    private S partition(int depth) {
        if (depth == elements.size()) {
            return action.apply(partitions);
        }
        for (List<T> partition : partitions) {
            partition.add(elements.get(depth));
            S result = partition(depth + 1);
            if (result != null) return result;
            partition.remove(partition.size() - 1);
        }
        return null;
    }

    public S run() {
        return partition(0);
    }
}
