package treewidth_approximation.logic.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PartitionExecutor<T, S>{
    private final List<T> elements;
    List<List<T>> partitions;
    List<Integer> sizeLimits;
    Function<List<List<T>>, S> action;

    public PartitionExecutor(List<T> elements, int partitionsAmount, List<Integer> sizeLimits, Function<List<List<T>>, S> action) {
        this.elements = elements;
        partitions = new ArrayList<>();
        for (int i = 0; i< partitionsAmount; i++) {
            partitions.add(new ArrayList<>());
        }
        this.sizeLimits = sizeLimits;
        this.action = action;
    }
    
    private S partition(int depth) {
        if (depth == elements.size()) {
            return action.apply(partitions);
        }
        for (int i = 0; i< partitions.size(); i++) {
            List<T> partition = partitions.get(i);
            if (partition.size() >= sizeLimits.get(i)) continue;
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
