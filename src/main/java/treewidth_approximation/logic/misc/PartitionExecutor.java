package treewidth_approximation.logic.misc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.lang.Math.min;


// T - partitions of this are considered
// S - return type
public class PartitionExecutor<T, S>{
    private final List<T> elements;
    private final int minPartitionsAmount;
    private final int maxPartitionsAmount;
    private final boolean setOrderMatters;
    private final Map<T, Integer> setsOfElements;
    private final Function<Partition<T>, S> action;

    public PartitionExecutor(List<T> elements, int minPartitionsAmount, int maxPartitionsAmount, boolean setOrderMatters, Function<Partition<T>, S> action) {
        this.elements = elements;
        this.minPartitionsAmount = minPartitionsAmount;
        this.maxPartitionsAmount = maxPartitionsAmount;
        this.setOrderMatters = setOrderMatters;
        setsOfElements = new HashMap<>();
        for (T elem : elements) {
            setsOfElements.put(elem, -1);
        }
        this.action = action;
    }

    private S split(int depth, int currentAmountOfSets) {
        if (currentAmountOfSets > maxPartitionsAmount) return null;

        if (depth == elements.size()) {
            Partition<T> partition = new Partition<>(setsOfElements);
            if (!(minPartitionsAmount <= currentAmountOfSets && currentAmountOfSets <= maxPartitionsAmount))
                return null;

            if (setOrderMatters) {
                // we have a partition, let's now order it in every possible way
                PermutationExecutor<S> permutationExecutor = new PermutationExecutor<>(currentAmountOfSets, (permutation) -> {
                    Map<T, Integer> orderedPartitionMap = new HashMap<>();
                    for (int i = 0; i< currentAmountOfSets; i++) {
                        for (T elem : partition.getSet(i)) {
                            orderedPartitionMap.put(elem, permutation.get(i));
                        }
                    }
                    Partition<T> orderedPartition = new Partition<>(orderedPartitionMap);

                    return action.apply(orderedPartition);
                });
                return permutationExecutor.run();
            } else {
                return action.apply(partition);
            }
        }
        T elem = elements.get(depth);
        for (int i = 0; i < min(currentAmountOfSets + 1, maxPartitionsAmount); i++) {
            setsOfElements.put(elem, i);
            S result = split(depth + 1, (i == currentAmountOfSets) ? currentAmountOfSets + 1 : currentAmountOfSets);
            if (result != null) return result;
        }
        return null;
    }

    public S run() {
        return split(0, 0);
    }
}
