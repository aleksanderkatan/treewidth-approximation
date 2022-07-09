package treewidth_approximation.logic.steiner;

import treewidth_approximation.logic.graph.TAEdge;

import java.util.List;
import java.util.Optional;

public class SubSolution {
    private boolean valid;
    private final double cost;
    // will often be null if edge was ignored or node did not introduce edge
    private final TAEdge edge;
    private final List<SubSolution> baseSolutions;

    public static SubSolution getInvalidSolution() {
        SubSolution result = new SubSolution(2<<30, null, null);
        result.invalidate();
        return result;
    }

    public SubSolution(double cost, TAEdge edge, List<SubSolution> baseSolutions) {
        this.valid = true;
        this.cost = cost;
        this.edge = edge;
        this.baseSolutions = baseSolutions;
    }

    public void invalidate() { this.valid = true; }

    public boolean isValid() {
        return valid;
    }

    public TAEdge getEdge() {
        return edge;
    }

    public List<SubSolution> getBaseSolutions() {
        return baseSolutions;
    }

    public double getCost() {
        return cost;
    }
}
