package treewidth_approximation.logic.graph;

import org.junit.jupiter.api.Test;
import treewidth_approximation.logic.misc.*;

import java.util.*;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;

public class MiscTest {
    @Test
    void testPermutationEquals() {
        Permutation p0 = new Permutation(List.of(0, 1, 2));
        Permutation p1 = new Permutation(List.of(0, 1, 2));
        Permutation p2 = new Permutation(List.of(1, 0, 2));

        assertEquals(p0, p1);
        assertNotEquals(p1, p2);
    }


    @Test
    void testGeneratePermutations() {
        Permutation p0 = new Permutation(List.of(0, 1, 2));
        Permutation p1 = new Permutation(List.of(0, 2, 1));
        Permutation p2 = new Permutation(List.of(1, 0, 2));
        Permutation p3 = new Permutation(List.of(1, 2, 0));
        Permutation p4 = new Permutation(List.of(2, 0, 1));
        Permutation p5 = new Permutation(List.of(2, 1, 0));

        Set<Permutation> permutations = new HashSet<>();

        PermutationExecutor<Boolean> executor = new PermutationExecutor<>(3, permutation -> {
            permutations.add(permutation);
            return null;
        });


        executor.run();


        assertTrue(permutations.contains(p0));
        assertTrue(permutations.contains(p1));
        assertTrue(permutations.contains(p2));
        assertTrue(permutations.contains(p3));
        assertTrue(permutations.contains(p4));
        assertTrue(permutations.contains(p5));
        assertEquals(6, permutations.size());
    }

    @Test
    void testPartitionEquals() {
        Partition<Integer> p0 = new Partition<>(Map.ofEntries(
                entry(1, 0),
                entry(2, 1),
                entry(3, 1)
        ));
        Partition<Integer> p1 = new Partition<>(Map.ofEntries(
                entry(1, 0),
                entry(2, 1),
                entry(3, 1)
        ));

        assertEquals(p0, p1);
    }

    @Test
    void testPartitionNotEquals() {
        Partition<Integer> p0 = new Partition<>(Map.ofEntries(
                entry(1, 0),
                entry(2, 1),
                entry(3, 1)
        ));
        Partition<Integer> p1 = new Partition<>(Map.ofEntries(
                entry(1, 0),
                entry(2, 1),
                entry(3, 2)
        ));

        assertNotEquals(p0, p1);
    }

    @Test
    void testPartitionDifferentTypes() {
        Partition<Integer> p0 = new Partition<>(Map.ofEntries(
                entry(1, 0),
                entry(2, 1),
                entry(3, 1)
        ));
        Partition<String> p1 = new Partition<>(Map.ofEntries(
                entry("1", 0),
                entry("2", 1),
                entry("3", 1)
        ));

        assertNotEquals(p0, p1);
    }

    @Test
    void testGenerateUnorderedPartitions() {
        List<Integer> l0 = List.of(0, 0, 0);
        List<Integer> l1 = List.of(0, 0, 1);
        List<Integer> l2 = List.of(0, 1, 0);
        List<Integer> l3 = List.of(0, 1, 1);
        List<Integer> l4 = List.of(0, 1, 2);
        // means first element is in 0th partition, second in 1st and third in 2nd

        Set<List<Integer>> partitions = new HashSet<>();

        PartitionExecutor<Integer, Boolean> partitionExecutor = new PartitionExecutor<>(List.of(1, 2, 3), 1, 3, false, (partition) -> {
            List<Integer> current = new ArrayList<>();
            current.add(partition.getSetIndexOfElement(1));
            current.add(partition.getSetIndexOfElement(2));
            current.add(partition.getSetIndexOfElement(3));
            partitions.add(current);
            return null;
        });


        partitionExecutor.run();


        assertTrue(partitions.contains(l0));
        assertTrue(partitions.contains(l1));
        assertTrue(partitions.contains(l2));
        assertTrue(partitions.contains(l3));
        assertTrue(partitions.contains(l4));
        assertEquals(5, partitions.size());
    }

    @Test
    void testGenerateOrderedPartitions() {
        List<Integer> l00 = List.of(0, 0, 0);

        List<Integer> l10 = List.of(0, 0, 1);
        List<Integer> l11 = List.of(1, 1, 0);

        List<Integer> l20 = List.of(0, 1, 0);
        List<Integer> l21 = List.of(1, 0, 1);

        List<Integer> l30 = List.of(0, 1, 1);
        List<Integer> l31 = List.of(1, 0, 0);

        List<Integer> l40 = List.of(0, 1, 2);
        List<Integer> l41 = List.of(0, 2, 1);
        List<Integer> l42 = List.of(1, 0, 2);
        List<Integer> l43 = List.of(1, 2, 0);
        List<Integer> l44 = List.of(2, 0, 1);
        List<Integer> l45 = List.of(2, 1, 0);

        Set<List<Integer>> partitions = new HashSet<>();

        PartitionExecutor<Integer, Boolean> partitionExecutor = new PartitionExecutor<>(List.of(1, 2, 3), 1, 3, true, (partition) -> {
            List<Integer> current = new ArrayList<>();
            current.add(partition.getSetIndexOfElement(1));
            current.add(partition.getSetIndexOfElement(2));
            current.add(partition.getSetIndexOfElement(3));
            partitions.add(current);
            return null;
        });


        partitionExecutor.run();


        assertTrue(partitions.contains(l00));
        assertTrue(partitions.contains(l10));
        assertTrue(partitions.contains(l11));
        assertTrue(partitions.contains(l20));
        assertTrue(partitions.contains(l21));
        assertTrue(partitions.contains(l30));
        assertTrue(partitions.contains(l31));
        assertTrue(partitions.contains(l40));
        assertTrue(partitions.contains(l41));
        assertTrue(partitions.contains(l42));
        assertTrue(partitions.contains(l43));
        assertTrue(partitions.contains(l44));
        assertTrue(partitions.contains(l45));
        assertEquals(13, partitions.size());
    }

    @Test
    void testGenerateOrderedPartitionsLimitedAmount() {
        List<Integer> l10 = List.of(0, 0, 1);
        List<Integer> l11 = List.of(1, 1, 0);

        List<Integer> l20 = List.of(0, 1, 0);
        List<Integer> l21 = List.of(1, 0, 1);

        List<Integer> l30 = List.of(0, 1, 1);
        List<Integer> l31 = List.of(1, 0, 0);

        Set<List<Integer>> partitions = new HashSet<>();

        PartitionExecutor<Integer, Boolean> partitionExecutor = new PartitionExecutor<>(List.of(1, 2, 3), 2, 2, true, (partition) -> {
            List<Integer> current = new ArrayList<>();
            current.add(partition.getSetIndexOfElement(1));
            current.add(partition.getSetIndexOfElement(2));
            current.add(partition.getSetIndexOfElement(3));
            partitions.add(current);
            return null;
        });


        partitionExecutor.run();


        assertTrue(partitions.contains(l10));
        assertTrue(partitions.contains(l11));
        assertTrue(partitions.contains(l20));
        assertTrue(partitions.contains(l21));
        assertTrue(partitions.contains(l30));
        assertTrue(partitions.contains(l31));
        assertEquals(6, partitions.size());
    }

    @Test
    void testPartitionCopyRestrictingSet() {
        Partition<Integer> p0 = new Partition<>(Map.ofEntries(
                entry(1, 0),
                entry(2, 1),
                entry(3, 1)
        ));
        Partition<Integer> p1 = new Partition<>(Map.ofEntries(
                entry(2, 0),
                entry(3, 0)
        ));


        p0 = p0.copyRestrictingSet(0);


        assertEquals(p0, p1);
    }

    @Test
    void testPartitionCopyRestrictingElementNoRemoveSet() {
        Partition<Integer> p0 = new Partition<>(Map.ofEntries(
                entry(1, 0),
                entry(2, 1),
                entry(3, 1)
        ));
        Partition<Integer> p1 = new Partition<>(Map.ofEntries(
                entry(1, 0),
                entry(3, 1)
        ));


        Partition<Integer> result = p0.copyRestrictingElement(2);


        assertEquals(p1, result);
    }

    @Test
    void testPartitionCopyRestrictingElementRemoveSet() {
        Partition<Integer> p0 = new Partition<>(Map.ofEntries(
                entry(1, 0),
                entry(2, 1),
                entry(3, 1)
        ));


        Partition<Integer> result = p0.copyRestrictingElement(1);


        assertEquals(Set.of(2, 3), result.getSet(result.getSetIndexOfElement(2)));
    }

    @Test
    void testPartitionCombineSame() {
        Partition<Integer> p0 = new Partition<>(Map.ofEntries(
                entry(1, 0),
                entry(2, 1),
                entry(3, 1)
        ));


        Partition<Integer> result = p0.combine(p0);


        assertEquals(Set.of(1), result.getSet(result.getSetIndexOfElement(1)));
        assertEquals(Set.of(2, 3), result.getSet(result.getSetIndexOfElement(2)));
    }

    @Test
    void testPartitionCombineSimple() {
        Partition<Integer> p0 = new Partition<>(Map.ofEntries(
                entry(1, 0),
                entry(2, 1),
                entry(3, 1)
        ));
        Partition<Integer> p1 = new Partition<>(Map.ofEntries(
                entry(1, 0),
                entry(2, 0),
                entry(3, 1)
        ));


        Partition<Integer> result = p0.combine(p1);


        assertEquals(Set.of(1, 2, 3), result.getSet(result.getSetIndexOfElement(1)));
    }

    @Test
    void testPartitionCombineComplex() {
        Partition<Integer> p0 = new Partition<>(Map.ofEntries(
                entry(1, 0),
                entry(2, 0),
                entry(3, 0),
                entry(4, 1),
                entry(5, 1),
                entry(6, 2)
        ));
        Partition<Integer> p1 = new Partition<>(Map.ofEntries(
                entry(1, 3),
                entry(2, 3),
                entry(3, 2),
                entry(4, 1),
                entry(5, 0),
                entry(6, 0)
        ));


        Partition<Integer> result = p0.combine(p1);


        assertEquals(Set.of(1, 2, 3), result.getSet(result.getSetIndexOfElement(1)));
        assertEquals(Set.of(4, 5, 6), result.getSet(result.getSetIndexOfElement(4)));
    }

    @Test
    void testPartitionCombineLoop() {
        Partition<Integer> p0 = new Partition<>(Map.ofEntries(
                entry(1, 0),
                entry(2, 0),
                entry(3, 1),
                entry(4, 1),
                entry(5, 2),
                entry(6, 2)
        ));
        Partition<Integer> p1 = new Partition<>(Map.ofEntries(
                entry(1, 0),
                entry(2, 1),
                entry(3, 1),
                entry(4, 2),
                entry(5, 2),
                entry(6, 0)
        ));


        Partition<Integer> result = p0.combine(p1);


        assertEquals(Set.of(1, 2, 3, 4, 5, 6), result.getSet(result.getSetIndexOfElement(1)));
    }

    @Test
    void testSubsetGenerator() {
        List<Integer> elements = List.of(1, 2, 3);
        Set<Set<Integer>> subsets = new HashSet<>();

        SubsetExecutor<Integer, Boolean> subsetExecutor = new SubsetExecutor<>(new ArrayList<>(elements), subset -> {
            subsets.add(subset);
            return null;
        });


        subsetExecutor.run();


        assertTrue(subsets.contains(Set.of()));
        assertTrue(subsets.contains(Set.of(1)));
        assertTrue(subsets.contains(Set.of(2)));
        assertTrue(subsets.contains(Set.of(3)));
        assertTrue(subsets.contains(Set.of(1, 2)));
        assertTrue(subsets.contains(Set.of(1, 3)));
        assertTrue(subsets.contains(Set.of(2, 3)));
        assertTrue(subsets.contains(Set.of(1, 2, 3)));
        assertEquals(8, subsets.size());
    }
}
