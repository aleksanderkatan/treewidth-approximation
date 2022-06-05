package treewidth_approximation.logic.steiner;

public class SubSolution {
    private final double cost;
    private final boolean wasEdgeTaken;
    private final SubSolution parentSolution;

    public SubSolution(double cost, boolean wasEdgeTaken, SubSolution parentSolution) {
        this.cost = cost;
        this.wasEdgeTaken = wasEdgeTaken;
        this.parentSolution = parentSolution;
    }

    public double getCost() {
        return cost;
    }

    public SubSolution getParentSolution() {
        return parentSolution;
    }

    public boolean wasEdgeTaken() {
        return wasEdgeTaken;
    }
}
