package treewidth_approximation.logic.misc;

import java.util.List;

public class Permutation {
    private final List<Integer> elements;

    public Permutation(List<Integer> elements) {
        this.elements = elements;
    }

    public int get(int i) {return elements.get(i);}

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {return true;}

        if (!(obj instanceof Permutation)) {return false;}

        return elements.equals(((Permutation) obj).elements);
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }
}
