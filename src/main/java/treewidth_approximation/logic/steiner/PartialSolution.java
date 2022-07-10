package treewidth_approximation.logic.steiner;

import treewidth_approximation.logic.misc.Partition;

import java.util.HashMap;
import java.util.Map;

// Box for a singular map
public class PartialSolution {
    private final Map<SubProblem, SubSolution> map;

    public PartialSolution() {
        map = new HashMap<>();
    }

    public void putSolution(SubProblem subProblem, SubSolution subSolution) {
//        if (subSolution.isValid())
            map.put(subProblem, subSolution);
    }

    public SubSolution getSolution(SubProblem subProblem) {
        SubSolution result = map.get(subProblem);
        if (result == null) {
            System.out.println("WARNING: a subProblem has no solution!!");
            System.out.println(subProblem);
            throw new RuntimeException();
        }
        return result;
    }

    public Map<SubProblem, SubSolution> getMap() { return map; }

}
