package treewidth_approximation.logic.steiner;

import java.util.HashMap;
import java.util.Map;

// Box for a singular map
public class PartialSolution {
    private Map<SubProblem, SubSolution> map;

    public PartialSolution() {
        map = new HashMap<>();
    }

    public void dispose() {
        map = new HashMap<>();
    }

    public void putSolution(SubProblem subProblem, SubSolution subSolution) {
        if (subSolution.isValid())
            map.put(subProblem, subSolution);
    }

    public SubSolution getSolution(SubProblem subProblem) {
        SubSolution result = map.get(subProblem);
        if (result == null) {
            return SubSolution.getInvalidSolution();
        }
        return result;
    }

    public Map<SubProblem, SubSolution> getMap() { return map; }

}
