package treewidth_approximation.logic.misc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class SubsetExecutor<T, S> {
    // T - partitions of this are considered
    // S - return type
    private final List<T> elements;
    private final Set<T> currentSet;
    private final Function<Set<T>, S> action;

    public SubsetExecutor(List<T> elements, Function<Set<T>, S> action) {
        this.elements = elements;
        this.currentSet = new HashSet<>();
        this.action = action;
    }

    private S split(int depth) {
        if (depth == elements.size()) {
            return action.apply(new HashSet<>(currentSet));
        }
        T elem = elements.get(depth);

        currentSet.add(elem);
        S result = split(depth + 1);
        if (result != null) return result;
        currentSet.remove(elem);
        return split(depth + 1);
    }

    public S run() {
        return split(0);
    }
}
