package treewidth_approximation.logic.steiner;

import treewidth_approximation.logic.graph.TAEdge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubSolution {
    private boolean valid;
    private final double cost;
    // will often be null if edge was ignored or node did not introduce edge
    private final TAEdge edge;
    private final List<SubSolution> baseSolutions;

    private static SubSolution INVALID_SOLUTION = null;

    public static SubSolution getInvalidSolution() {
        if (INVALID_SOLUTION == null) {
            INVALID_SOLUTION = new SubSolution(2137, null, List.of());
            INVALID_SOLUTION.invalidate();
        }
        return INVALID_SOLUTION;
    }

    public SubSolution(double cost, TAEdge edge, List<SubSolution> baseSolutions) {
        this.valid = true;
        this.cost = cost;
        this.edge = edge;
        this.baseSolutions = baseSolutions;
    }

    public void invalidate() {this.valid = false;}

    public boolean isValid() {
        return valid;
    }

    public Set<TAEdge> collectEdges() {
        Set<TAEdge> result = new HashSet<>();
        for (SubSolution baseSubSolution : baseSolutions) {
            result.addAll(baseSubSolution.collectEdges());
        }
        if (edge != null) {
            result.add(edge);
        }
        return result;
    }

    public double getCost() {
        return cost;
    }
}
