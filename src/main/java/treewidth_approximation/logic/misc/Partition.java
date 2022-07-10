package treewidth_approximation.logic.misc;

import java.util.*;

public class Partition<T> {
    private final List<Set<T>> sets;
    private final Map<T, Integer> elements;

//    normalize.
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

    public Partition<T> combine(Partition<T> other) {
        Map<T, Integer> newMap = new HashMap<>();

        int currentSet = 0;
        for (T elem : elements.keySet()) {
            if (newMap.containsKey(elem)) continue;

            Queue<T> q = new LinkedList<>();
            q.add(elem);
            while (!q.isEmpty()) {
                T current = q.remove();
                Set<T> combined = new HashSet<>(sets.get(elements.get(current)));
                combined.addAll(other.sets.get(other.elements.get(current)));
                for (T toVisit : combined) {
                    if (!newMap.containsKey(toVisit)) {
                        newMap.put(toVisit, currentSet);
                        q.add(toVisit);
                    }
                }
            }
            currentSet++;
        }

        return new Partition<>(newMap);
    }

    public Partition<T> copyRestrictingSet(int setIndex) {
        Map<T, Integer> newPartitionMap = new HashMap<>();
        for (var entry : elements.entrySet()) {
            int part = entry.getValue();
            if (part == setIndex) continue;
            if (part > setIndex) {
                part--;
            }
            newPartitionMap.put(entry.getKey(), part);
        }
        return new Partition<>(newPartitionMap);
    }

    public Partition<T> copyRestrictingElement(T elem) {
        int setIndex = elements.get(elem);
        if (sets.get(setIndex).size() == 1) {
            return copyRestrictingSet(setIndex);
        }
        Map<T, Integer> newMap = new HashMap<>(elements);
        newMap.remove(elem);
        return new Partition<>(newMap);
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
        if (index >= sets.size()) {
            return new HashSet<>();
        }
        return sets.get(index);
    }

    public int getSetIndexOfElement(T element) {
        return elements.get(element);
    }

    public List<Set<T>> getSets() { return sets; }

    public Map<T, Integer> getElements() { return elements; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partition<?> partition = (Partition<?>) o;
        return Objects.equals(sets, partition.sets) && Objects.equals(elements, partition.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sets, elements);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Partition: { ");
        for (Set<T> set : sets) {
            builder.append("{ ");
            for (T elem : set) {
                builder.append(elem).append(" ");
            }
            builder.append("} ");
        }
        builder.append("}");
        return new String(builder);
    }
}
