package treewidth_approximation.logic.misc;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class PermutationExecutor<S> {
    private final int elementsAmount;
    private final List<Integer> perm;
    private final Function<Permutation, S> action;

    public PermutationExecutor(int elementsAmount, Function<Permutation, S> action) {
        this.elementsAmount = elementsAmount;
        this.perm = new ArrayList<>();
        for (int i = 0; i< elementsAmount; i++) {
            perm.add(i);
        }
        this.action = action;
    }

    private S shuffle(int depth) {
        if (depth == elementsAmount) {
            Permutation permutation = new Permutation(new ArrayList<>(perm));
            return action.apply(permutation);
        }
        for (int i = depth; i< elementsAmount; i++) {
            int temp = perm.get(i);
            perm.set(i, perm.get(depth));
            perm.set(depth, temp);

            S result = shuffle(depth + 1);
            if (result != null) return result;

            temp = perm.get(i);
            perm.set(i, perm.get(depth));
            perm.set(depth, temp);
        }
        return null;
    }

    public S run() {
        return shuffle(0);
    }
}
