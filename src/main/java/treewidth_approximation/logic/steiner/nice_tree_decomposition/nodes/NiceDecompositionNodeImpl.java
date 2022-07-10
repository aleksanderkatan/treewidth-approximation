package treewidth_approximation.logic.steiner.nice_tree_decomposition.nodes;

import treewidth_approximation.logic.graph.TAGraph;
import treewidth_approximation.logic.misc.Partition;
import treewidth_approximation.logic.misc.PartitionExecutor;
import treewidth_approximation.logic.misc.StringUtilities;
import treewidth_approximation.logic.steiner.PartialSolution;
import treewidth_approximation.logic.steiner.SteinerInstance;
import treewidth_approximation.logic.steiner.SubProblem;
import treewidth_approximation.logic.steiner.SubSolution;
import treewidth_approximation.logic.steiner.nice_tree_decomposition.NiceDecompositionNode;
import treewidth_approximation.logic.tree_decomposition.DecompositionNode;

import java.util.*;

public abstract class NiceDecompositionNodeImpl implements NiceDecompositionNode {
    protected SteinerInstance instance;
    protected TAGraph inducedSubgraph;
    protected final Set<DecompositionNode> children;
    protected final Set<Integer> vertices;
    protected final PartialSolution solution;

    public NiceDecompositionNodeImpl(Set<Integer> vertices) {
        this.children = new HashSet<>();
        this.vertices = vertices;
        this.solution = new PartialSolution();
    }

    @Override
    public void setInstance(SteinerInstance instance) {
        this.instance = instance;
    }

    @Override
    public Set<Integer> getVertices() {
        return vertices;
    }

    @Override
    public TAGraph getSubgraph() {
        return inducedSubgraph;
    }

    @Override
    public Set<DecompositionNode> getChildren() {
        return children;
    }

    @Override
    public void addChild(DecompositionNode child) {
        children.add(child);
    }

    @Override
    public PartialSolution getSolutions() {
        return solution;
    }

    @Override
    public void compute() {
        // default implementation - for Forget, Introduce and Introduce Edge.
        // Leaf and Join further override this

        // firstly, call compute() for all children
        for (DecompositionNode child : getChildren()) {
            ((NiceDecompositionNode)child).compute();
        }

        System.out.println("Compute start for: " + getLabel());
        // now, for each SubProblem call singular calculate
        PartitionExecutor<Integer, Boolean> partitionExecutor = new PartitionExecutor<>(new ArrayList<>(vertices), 1, vertices.size(), false, partition -> {
            int amount = partition.getAmountOfSets();
            // either one of them is the set of vertices not chosen for a subtree, or all are chosen
            for (int i = 0; i<= amount; i++) {
                Set<Integer> X;
                if (i == amount) {
                    X = new HashSet<>();
                } else {
                    X = partition.getSet(i);
                }

                Partition<Integer> subProblemPartition = partition.copyRestrictingSet(i);

                SubProblem subProblem = new SubProblem(X, subProblemPartition);
                SubSolution subSolution = computeSingular(subProblem);
                // if subProblem does have a terminal in X, return invalid solution
//                System.out.println("Computing " + getLabel() + " " + subProblem);
                solution.putSolution(subProblem, subSolution);
            }

            return null;
        });

        partitionExecutor.run();
        // grand child solutions are no longer needed
        for (DecompositionNode child : children) {
            for (DecompositionNode grandChild : child.getChildren()) {
                ((NiceDecompositionNode)grandChild).getSolutions().dispose();
            }
        }

        System.out.println("Compute end for:   " + getLabel());
    }

    @Override
    public String getLabel() {
        return "NiceDecompositionNode - " + StringUtilities.setToString(vertices);
    }

    @Override
    public void removeChild(DecompositionNode child) {
        children.remove(child);
    }
}
