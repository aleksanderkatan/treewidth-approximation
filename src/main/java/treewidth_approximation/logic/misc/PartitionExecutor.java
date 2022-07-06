package treewidth_approximation.logic.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

// T - partitions of this are considered
// S - return type
public class PartitionExecutor<T, S>{
    private final List<T> elements;
    private final int partitionsAmount;
    private final Map<T, Integer> setsOfElements;
    private final Function<Partition<T>, S> action;

    public PartitionExecutor(List<T> elements, int partitionsAmount, Function<Partition<T>, S> action) {
        this.elements = elements;
        this.partitionsAmount = partitionsAmount;
        setsOfElements = new HashMap<>();
        for (T elem : elements) {
            setsOfElements.put(elem, -1);
        }
        this.action = action;
    }

    // TODO: each partition is now counted size! times
    private S split(int depth) {
        if (depth == elements.size()) {
            Partition<T> partition = new Partition<>(setsOfElements);
            if (!partition.isEverySetNotEmpty())
                return null;
            if (partition.getAmountOfSets() != partitionsAmount)
                return null;
            return action.apply(partition);
        }
        T elem = elements.get(depth);
        for (int i = 0; i< partitionsAmount; i++) {
            setsOfElements.put(elem, i);
            S result = split(depth + 1);
            if (result != null) return result;
        }
        return null;
    }

    public S run() {
        return split(0);
    }
}
