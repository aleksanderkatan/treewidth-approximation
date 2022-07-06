package treewidth_approximation.logic.misc;

import treewidth_approximation.logic.graph.TAEdge;

import java.util.*;

public class Partition<T> {
    List<Set<T>> sets;
    Map<T, Integer> elements;

    public Partition(Map<T, Integer> elements) {
        this.elements = elements;
        sets = new ArrayList<>();

        for (var entry : elements.entrySet()) {
            T element = entry.getKey();
            Integer setIndex = entry.getValue();

            while (sets.size() <= setIndex) {
                sets.add(new HashSet<>());
            }
            sets.get(setIndex).add(element);
        }
    }

    public int getAmountOfSets() {
        return sets.size();
    }

    public boolean isEverySetNotEmpty() {
        for (Set<T> set : sets) {
            if (set.size() == 0)
                return false;
        }
        return true;
    }

    public Set<T> getSet(int index) {
        return sets.get(index);
    }

    public int getSetOfElement(T element) {
        return elements.get(element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sets, elements);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof Partition))
            return false;

        Partition c = (Partition) obj;

        return (sets == c.sets) && (elements == c.elements);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Partition: { ");
        for (Set<T> set : sets) {
            s.append("{ ");
            for (T elem : set) {
                s.append(elem).append(" ");
            }
            s.append("} ");
        }
        s.append("}");
        return new String(s);
    }
}
