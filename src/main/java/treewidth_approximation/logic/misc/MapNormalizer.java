package treewidth_approximation.logic.misc;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    Generally partitions {{1}{2}} and {{2}{1}} are considered different
    However, for Steiner Tree purposes, those are the same
 */
public class MapNormalizer {
    public static Map<Integer, Integer> normalize(Map<Integer, Integer> map) {
        List<Integer> elements = new ArrayList<>(map.keySet());
        elements.sort(Integer::compare);

        Map<Integer, Integer> oldIndexToNewIndex = new HashMap<>();
        Map<Integer, Integer> result = new HashMap<>();
        int currentIndex = 0;
        for (int v : elements) {
            int oldIndex = map.get(v);
            if (!oldIndexToNewIndex.containsKey(oldIndex)) {
                oldIndexToNewIndex.put(oldIndex, currentIndex++);
            }
            result.put(v, oldIndexToNewIndex.get(oldIndex));
        }
        return result;
    }
}
